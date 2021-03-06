package com.hyunseok.android.sharemusicplaylist;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter_Sample;
import com.hyunseok.android.sharemusicplaylist.data.DBHelper;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.util.Logger;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Playlist Tab 에서 새 플레이리스트를 만들 때 뜨는 액티비티
 *
 * PlaylistDetail Activity 에 비교하여 더 있어야 될것들
 * 대표이미지 설정 버튼, 음악 추가 버튼, TAG 입력부분(?)
 */
public class PlaylistNewActivity extends AppCompatActivity {

    private final int REQ_CAMERA = 101; // 카메라 요청 코드
    private final int REQ_GALLERY = 102; // 갤러리 요청 코드

    ImageView imageView_playlistNew;
    EditText et_playlistTitle;
    Button btn_OK, btn_cancle, btn_addTrack;
    ToggleButton toggleButton;

    RecyclerView recyclerView;
    PlaylistRecyclerViewAdapter_Sample adapter;
    private List<Track> tracks = new ArrayList<>(); // Activity 하단의 RecyclerView 에 들어가는 Tracks

    // Image
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_new);

        setWidget();
        init();
    }

    private void setWidget() {
        imageView_playlistNew = findViewById(R.id.imageView_playlistNew);
        et_playlistTitle = findViewById(R.id.et_playlistTitle);
        btn_OK = findViewById(R.id.btn_OK);
        btn_cancle = findViewById(R.id.btn_cancle);
        btn_addTrack = findViewById(R.id.btn_addTrack);
        toggleButton = findViewById(R.id.toggleButton);

        imageView_playlistNew.setOnClickListener(v -> setImage());
        btn_OK.setOnClickListener(this::buttonEvent);
        btn_cancle.setOnClickListener(this::buttonEvent);
        btn_addTrack.setOnClickListener(this::buttonEvent);
    }

    protected void init() {
        adapter = new PlaylistRecyclerViewAdapter_Sample(this, tracks, "");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Playlist makePlaylist() {
        Playlist playlist = new Playlist();
        String strUri;

        playlist.setTitle(et_playlistTitle.getText().toString());
        //playlist.setNickName(); // TODO
        playlist.setShare(toggleButton.isChecked());
        playlist.setTracks(tracks);
        if(imageUri != null) {
            strUri = imageUri.toString();
        } else {
            strUri = "";
        }
        playlist.setImgUri(strUri);
        playlist.setCurDate(new Date(System.currentTimeMillis()));

        return playlist;
    }

    private void saveToDB(Playlist playlist) throws SQLException {
        DBHelper dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        Dao<Playlist, Integer> playlistDao = dbHelper.getPlaylistDao();
        playlistDao.create(playlist);
        List<Playlist> playlistTest = playlistDao.queryForAll();
        Logger.print("PlaylistNewActivity","Save To DB===========================" + playlistTest.size());
    }

    public void buttonEvent(View v) {
        int cnt = 0; // TODO cnt 삭제 -> real Data로 바꾸기
        switch (v.getId()) {
            case R.id.btn_addTrack:
                cnt++;
                Track track = new Track();
                track.setTitle("Music" + cnt);
                track.setArtist("Artist" + cnt);
                tracks.add(track);
                init(); // Layout(RecylcerView) Refresh
                break;
            case R.id.btn_OK:
                Playlist playlist = makePlaylist();
                try {
                    Logger.print("PlaylistNewActivity","btn_OK click====================================");
                    saveToDB(playlist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
                MainActivity.changeTab("PLAYLIST"); // Playlist Tab Refresh;
                break;
            case R.id.btn_cancle:
                PlaylistNewActivity.super.onBackPressed();
                break;
            default: break;
        }
    }

    public void setImage() {
        AlertDialog.Builder alert_Imgbtn = new AlertDialog.Builder(PlaylistNewActivity.this);
        alert_Imgbtn.setTitle("Input Image");
        final CharSequence[] items_Imgbtn = {"Camera", "Gallery"};
        alert_Imgbtn.setItems(items_Imgbtn, (dialog, which) -> {
            switch (which) {
                case 0 : // Camera
                    inputImageBy("CAMERA");
                    break;
                case 1 : // Gallery
                    inputImageBy("GALLERY");
                    break;
            }
        });
        alert_Imgbtn.show(); // 팝업창을 띄운다.
    }

    private void inputImageBy(String option) {
        Intent intent;
        if ("CAMERA".equals(option)) { // 카메라 촬영 후 미디어 컨텐트 Uri를 생성해서 외부저장소에 저장한다.
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "ShareMusic/jpg"); // 촬영한 사진을 저장한다.
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            startActivityForResult(intent, REQ_CAMERA);
        } else if ("GALLERY".equals(option)) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*"); // 외부저장소에 있는 이미지만 가져오기위한 필터링.
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_GALLERY); // createChooser로 타이틀을 붙여줄 수 있다.
        }
    }

    //startActivityForResult() 후에 실행되는 콜백 함수
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CAMERA:
                // 마시멜로버전 이상인 경우에만 getData()에 null이 넘어올것임.
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        if (data != null && data.getData() != null) {
                            imageUri = data.getData();
                        }
                    }
                    if (imageUri != null) {
                        Glide.with(this).load(imageUri).into(imageView_playlistNew);
                    } else {

                    }
                } else { // reulstCode가 uri가 남아있는데 삭제처리해야함.

                }
                break;
            case REQ_GALLERY:
                //if(data != null && data.getData() != null) {
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    Glide.with(this).load(imageUri).into(imageView_playlistNew);
                } else {

                }
                break;
        }
    }
}
