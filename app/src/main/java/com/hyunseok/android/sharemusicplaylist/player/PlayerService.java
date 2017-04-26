package com.hyunseok.android.sharemusicplaylist.player;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.domain.Track_Extracted;
import com.hyunseok.android.sharemusicplaylist.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity 에서
 * 음악 재생 Class
 *
 * Used in : Player,
 * Created by KHS on 2017-04-11.
 */
public class PlayerService extends Service implements ControlInterface {

    private static final int NOTIFICATION_ID = 1;

    //
    public static final String ACTION_INIT = "action_init";
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";
    //public static final String ACTION_REWIND = "action_rewind";
    //public static final String ACTION_FAST_FORWARD = "action_fast_forward";

    // 1. 미디어플레이어 사용 API 세팅
    public static MediaPlayer mMediaPlayer = null;
    public static int position = 0;

    List<Track> tracks = new ArrayList<>();

    private Controller controller;

    public PlayerService(){
        controller = Controller.getInstance();
        controller.addObserver(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Player 에서 play(
        if(intent != null) {
            //if(intent.getExtras() != null) {
                //listType = intent.getExtras().getString(ListFragment.ARG_LIST_TYPE);
                //position = intent.getExtras().getInt("position");
                if(mMediaPlayer == null) {
                    initPlayer();
                    initController();
                }
            //}
        }

        Log.i("PS11", "onStartCommand");
        handleAction(intent);

        return super.onStartCommand(intent, flags, startId);
    }

    // 1. Media Player 기본값 설정
    private void initPlayer() {

        tracks = Track_Extracted.tracks;

        if(mMediaPlayer != null)
            mMediaPlayer.release();

        // Local Uri : "content://media/external/audio/media/967"
        Log.i("PS11initMedia", "position : "+position);
        Uri music_uri = Uri.parse(tracks.get(position).getPreview());

        mMediaPlayer = MediaPlayer.create(this, music_uri);
        mMediaPlayer.setLooping(false); // 반복 여부

        mMediaPlayer.setOnCompletionListener(mp -> { // 미디어 플레이어에 완료 체크 리스너를 등록한다. 음악이 끝까지 재생됐을 경우임.
            // TODO next();
            //buildNotification(generateAction(android.R.drawable.ic_media_play, "Play", ACTION_PLAY), ACTION_PLAY);
        });
    }
    private void initController() {
//        seekBar.setMax(player.getDuration()); // seekBar의 최대 길이 설정
//        seekBar.setProgress(0);
    }

    // 2. 명령어 실행.
    // Intent Action 에 넘어온 명령어를 분기시키는 함수
    private void handleAction(Intent intent) {
        if(intent == null || intent.getAction() == null)
            return;

        String action = intent.getAction();
        if(action.equalsIgnoreCase(ACTION_INIT)) { // 플레이어 초기화 후 재생
            initPlayer();
            controller.play();
        } else if(action.equalsIgnoreCase(ACTION_PLAY)) {
            controller.play();
        } else if(action.equalsIgnoreCase(ACTION_PAUSE)) {
            controller.pause();
        } else if(action.equalsIgnoreCase(ACTION_PREVIOUS)) {
            controller.prev();
        } else if(action.equalsIgnoreCase(ACTION_NEXT)) {
            controller.next();
        } else if(action.equalsIgnoreCase(ACTION_STOP)) {
            playerStop();
        }
        //else if( action.equalsIgnoreCase( ACTION_FAST_FORWARD ) ) {

        //}
        //else if( action.equalsIgnoreCase( ACTION_REWIND ) ) {

        //}
    }

    // Activity 에서의 클릭 버튼 생성
    private NotificationCompat.Action generateAction(int icon, String title, String intentAction) {
        Intent intent = new Intent(getApplicationContext(), PlayerService.class);
        intent.setAction(intentAction);
        // PendingIntent : 실행 대상이 되는 인텐트를 지연시키는 역할
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);

        return new NotificationCompat.Action.Builder(icon, title, pendingIntent).build();
    }

    // Notification Bar 를 생성하는 함수
    private void buildNotification(NotificationCompat.Action action, String action_flag) {
        // 노티바 모양
        //Notification.MediaStyle style = new Notification.MediaStyle();

        // 노티바 전체를 클릭했을 때 실행되는 메인 intent
//        Intent intentMain = new Intent( getApplicationContext(), PlayerActivity.class );
//        intentMain.putExtra( ListFragment.ARG_POSITION, position );
//        PendingIntent mainIntent = PendingIntent.getActivity(getApplicationContext(), 1, intentMain, 0);

        // STOP intent
        Intent intentStop = new Intent(getApplicationContext(), PlayerService.class);
        intentStop.setAction(ACTION_STOP);
        PendingIntent stopIntent = PendingIntent.getService(getApplicationContext(), 1, intentStop, 0);

        // 노티바 생성
        //Notification.Builder builder = new Notification.Builder( this );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this); // Compat은 버전처리를 자체적으로 해준다.

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(tracks.get(position).getTitle())
                .setContentText(tracks.get(position).getArtist());

        // Pause일 경우만 노티 삭제 가능
        //if(ACTION_PAUSE.equals(action_flag)) {
            builder.setDeleteIntent(stopIntent); // 노티바가 delete 될 때 stopIntent 가 실행.
            builder.setOngoing(false);
        //}

        //builder.setStyle(style);

        builder.addAction(generateAction(android.R.drawable.ic_media_previous, "", ACTION_PREVIOUS));
        builder.addAction(action);
        builder.addAction(generateAction(android.R.drawable.ic_media_next, "", ACTION_NEXT));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build()); // 노티바를 화면에 보여준다. 첫번째 인자는 notification 닫을 때 id값이 들어간다.
    }

    private void playerStart() {
        buildNotification(generateAction(android.R.drawable.ic_media_pause, "", ACTION_PAUSE), ACTION_PAUSE);
        mMediaPlayer.start();
    }

    private void playerPause() {
        buildNotification(generateAction(android.R.drawable.ic_media_play, "", ACTION_PLAY), ACTION_PLAY);
        mMediaPlayer.pause();
    }

    private void playerStop() {
        if(mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel( NOTIFICATION_ID ); // 인자는 notification 닫을 때 id값이 들어간다.
        Intent intent = new Intent(getApplicationContext(), PlayerService.class);
        stopService(intent);
    }

    private void playerPrev() {
        if(position > 0) { // 10곡 있으면 size:10 idx, position은 0~9
            position--;
            initPlayer();
            playerStart();
        }
    }

    private void playerNext() {
        if(position < Track_Extracted.tracks.size()-1) {
            position++;
            initPlayer();
            playerStart();
        }
    }

    @Override
    public void startPlayer() {
        playerStart();
    }

    @Override
    public void pausePlayer() {
        playerPause();
    }

    @Override
    public void prevPlayer() {
        playerPrev();
    }

    @Override
    public void nextPlayer() {
        playerNext();
    }

    @Override
    public void onDestroy() {
        controller.remove(this);
        super.onDestroy();
    }
}
