package com.hyunseok.android.sharemusicplaylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.domain.Track_Extracted;
import com.hyunseok.android.sharemusicplaylist.player.PlayerService;

public class TrackInfoActivity extends AppCompatActivity {
    Track track;
    TextView txtTitle, txtAlbum, txtArtist;
    RecyclerView rcyAlbumTrack;
    ImageView imgAlbum;
    ImageButton btnPlay, btnMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_info);
        track = (Track)getIntent().getSerializableExtra("TrackInfo");
        initWidget();
        txtTitle.setText(track.getTitle());
        txtAlbum.setText(track.getAlbum());
        txtArtist.setText(track.getArtist());
        Glide.with(this).load(track.getImage()).into(imgAlbum);
    }

    private void initWidget(){
        txtTitle = (TextView)findViewById(R.id.txtTitleInfo);
        txtAlbum = (TextView)findViewById(R.id.txtAlbumInfo);
        txtArtist = (TextView)findViewById(R.id.txtArtistInfo);
        rcyAlbumTrack = (RecyclerView)findViewById(R.id.rcyAlbumTracks);
        imgAlbum = (ImageView)findViewById(R.id.imgAlbumInfo);
        btnPlay = (ImageButton)findViewById(R.id.btnPlayInfo);
        btnMore = (ImageButton)findViewById(R.id.btnMoreInfo);

        btnPlay.setOnClickListener(listener);
        btnMore.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnPlayInfo :
                    //TODO Play Button 한번 더 눌렀을 때 동작. 새로 추가하기 or 현재 Playlist 에 추가하기 둘중 생각해보기
                    Track_Extracted.tracks.add(track); // 해당 position 의 Track 하나를 추출한다.
                    PlayerService.position = Track_Extracted.tracks.size()-1;
                    // TODO 같은 음악일 경우 add 안되도록
                    //Player.play(context);
                    intent = new Intent(TrackInfoActivity.this, PlayerService.class);
                    intent.setAction(PlayerService.ACTION_INIT);
                    startService(intent);
                    MainActivity.changeTab("PLAYER"); // PLAYER Tab 으로 변경하고 view 도 refresh 해준다.
                    break;
                case R.id.btnMoreInfo :
                    intent = new Intent(TrackInfoActivity.this, PopUpActivity.class);
                    intent.putExtra("TrackInfo",track);
                    startActivity(intent);
                    break;
            }
        }
    };

}
