package com.hyunseok.android.sharemusicplaylist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter_Sample;
import com.hyunseok.android.sharemusicplaylist.data.DBHelper;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  PlaylistDetail Activity 에 비교하여 더 있어야 될것들
 *  대표이미지 설정 버튼, 음악 추가 버튼, TAG 입력부분(?)
 */

// TODO 확인(Confirm)버튼, 취소(Cancle)버튼 만들기(항목 다 작성 후 저장 or  나가기)

@EActivity(R.layout.activity_playlist_new)
public class PlaylistNewActivity extends AppCompatActivity {

    private final int REQ_CAMERA = 101; // 카메라 요청 코드
    private final int REQ_GALLERY = 102; // 갤러리 요청 코드

    @ViewById
    ImageView imageView_playlistNew;
    @ViewById
    EditText et_playlistTitle;
    @ViewById
    Button btn_OK, btn_cancle, btn_addTrack;
    @ViewById
    RecyclerView recyclerView;
    PlaylistRecyclerViewAdapter_Sample adapter;
    private List<String> tracks = new ArrayList<>();; // Activity 하단의 RecyclerView 에 들어가는 Tracks

    // Image
    private Uri imageUri;
    private String strUri;

    @AfterViews
    protected void init() {

        adapter = new PlaylistRecyclerViewAdapter_Sample(this, tracks, "");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private Playlist makePlaylist() {
        Playlist playlist = new Playlist();

        playlist.setTitle(et_playlistTitle.getText().toString());
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
    }

    @Click({R.id.btn_OK, R.id.btn_cancle, R.id.btn_addTrack})
    public void buttonEvent(View v) {
        int cnt = 0; // TODO cnt 삭제 -> real Data로 바꾸기
        switch (v.getId()) {
            case R.id.btn_addTrack:
                cnt++;
                tracks.add("Music" + cnt);
                init(); // Layout(RecylcerView) Refresh
                break;
            case R.id.btn_OK:
                Playlist playlist = makePlaylist();
                try {
                    saveToDB(playlist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, ""+playlist.getTitle(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.btn_cancle:
                PlaylistNewActivity.super.onBackPressed();
                break;
            default: break;
        }
    }

    @Click({R.id.imageView_playlistNew})
    public void setImage() {
        AlertDialog.Builder alert_Imgbtn = new AlertDialog.Builder(PlaylistNewActivity.this);
        alert_Imgbtn.setTitle("Input Image");
        final CharSequence[] items_Imgbtn = {"Camera", "Gallery"};
        alert_Imgbtn.setItems(items_Imgbtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0 : // Camera
                        inputImageBy("CAMERA");
                        break;
                    case 1 : // Gallery
                        inputImageBy("GALLERY");
                        break;
                }
            }
        });
        alert_Imgbtn.show(); // 팝업창을 띄운다.
    }

    private void inputImageBy(String option) {
        Intent intent = null;
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
