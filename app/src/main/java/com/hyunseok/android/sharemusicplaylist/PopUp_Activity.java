package com.hyunseok.android.sharemusicplaylist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.data.DBHelper;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.util.Logger;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PopUp_Activity extends Activity {
    TextView txtTrackTitle,txtTrackArtist;
    Button btnShowTrackInfo,btnAdd_ExistList,btnAdd_NewList;
    Track track;
    String selectedListTitle;
    DBHelper dbHelper;
    Dao<Playlist, Integer> playlistDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_);
        initWidget();
        track = (Track)getIntent().getSerializableExtra("TrackInfo");
        txtTrackTitle.setText(track.getTitle());
        txtTrackArtist.setText(track.getArtist());

    }
    private void initWidget(){
        txtTrackTitle = (TextView)findViewById(R.id.txtTrackTitle);
        txtTrackArtist = (TextView)findViewById(R.id.txtTrackArtist);
        btnShowTrackInfo = (Button)findViewById(R.id.btnShowTrackInfo);
        btnAdd_ExistList= (Button)findViewById(R.id.btnAddExistPlaylist);
        btnAdd_NewList = (Button)findViewById(R.id.btnAddNewPlaylist);
        btnShowTrackInfo.setOnClickListener(listener);
        btnAdd_NewList.setOnClickListener(listener);
        btnAdd_ExistList.setOnClickListener(listener);
    }

    View.OnClickListener listener = v -> {
        switch (v.getId()){
            case R.id.btnAddExistPlaylist :
                Toast.makeText(PopUp_Activity.this, "기존에 존재하는 리스트에 곡 추가", Toast.LENGTH_SHORT).show();
                try {
                    List<Playlist> playlists;
                    List<String> listTitles = new ArrayList<>();
                    dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);  //추상 팩토리 형태로 구현
                    playlistDao = dbHelper.getPlaylistDao();
                    playlists = playlistDao.queryForAll();
                    if(playlists.size() != 0 && playlists != null) {
                        for (Playlist playlist : playlists) {
                            Logger.print("popupactivity", "list title" + playlist.getId() + " : " + playlist.getTitle());
                            listTitles.add(playlist.getTitle());
                        }
                        setDialog(listTitles);
                    }else{
                        Toast.makeText(this, "No exist your List. Please Make your Playlist", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PopUp_Activity.this, PlaylistNewActivity_.class);
                        intent.putExtra("TrackInfo", track);
                        startActivity(intent);
                    }

                } catch (SQLException e) {e.printStackTrace();}
                break;
            case R.id.btnAddNewPlaylist :
                Toast.makeText(PopUp_Activity.this, "새로운 액티비티 생성후 곡 추가", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PopUp_Activity.this, PlaylistNewActivity_.class);
                intent.putExtra("TrackInfo", track);
                startActivity(intent);
                break;
            case R.id.btnShowTrackInfo :
                Toast.makeText(PopUp_Activity.this, "곡에 대한 정보와, 해당 곡의 앨범에 포함된 리스트", Toast.LENGTH_SHORT).show();
                Intent intentTI = new Intent(PopUp_Activity.this, TrackInfoActivity.class);
                startActivity(intentTI);
                break;
        }
    };
    public void setDialog(List<String> listTitles){
        Logger.print("PopupActivity","setDialog-----");
        CharSequence[] items = listTitles.toArray(new CharSequence[listTitles.size()]);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Select List...");
        alertDialogBuilder.setItems(items,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            updateList(id);
                            dialog.dismiss();
                        } catch (SQLException e) {e.printStackTrace();}
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void updateList(int id) throws SQLException {
        //TODO : selectListTitle에 맞는 title 레코드를 찾아서 track arraylist에 .add(track)을 해준다.
        Toast.makeText(this, "update List!", Toast.LENGTH_SHORT).show();
        Playlist playlists;
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);  //추상 팩토리 형태로 구현
        playlistDao = dbHelper.getPlaylistDao();
        playlists = playlistDao.queryForId(id);
        playlists.getTracks().add(track);

    }


}
