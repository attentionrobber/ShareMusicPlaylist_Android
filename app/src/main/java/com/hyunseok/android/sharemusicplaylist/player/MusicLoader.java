package com.hyunseok.android.sharemusicplaylist.player;

import android.net.Uri;
import android.util.Log;

import com.hyunseok.android.sharemusicplaylist.domain.API_URL;
import com.hyunseok.android.sharemusicplaylist.domain.Data;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.domain.TrackData;
import com.hyunseok.android.sharemusicplaylist.retrofit_interface.DeezerApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Used in :
 * Created by KHS on 2017-04-12.
 */

public class MusicLoader {

    private Retrofit retrofit;
    private DeezerApiService service;
    public List<Track> tracks = new ArrayList<>();

//    public void loadMusic(List<Track> tracks) {
//        this.tracks = tracks;
//    }

//    public MusicLoader(List<Track> tracks) {
//        this.tracks = tracks;
//    }

    public List<Track> getTracks() {
        Log.i("MusicLoader", ""+tracks);
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    //    public void loadMusic() {
//
//        setRetrofit();
//        loadTrack();
//    }
//
//    private void setRetrofit() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(API_URL.deezer_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        service = retrofit.create(DeezerApiService.class); //사용할 인터페이스를 설정한다
//    }
//
//    private void loadTrack(){
//        String value = "";
//        value = "track:\""+value+"\"";
//        Call<TrackData> result = service.getData(value);
//        tracks = new ArrayList<>();
//
//        result.enqueue(new Callback<TrackData>() {
//            @Override
//            public void onResponse(Call<TrackData> call, Response<TrackData> response) {
//                if(response.isSuccessful()) {
//                    TrackData trackData = response.body();
//                    for(Data data : trackData.getData()){
//                        Track needs = new Track();
//
//                        setting_Needs(data,needs);
//                        tracks.add(needs);
//                    }
//                }else{
//                    Log.d("MainRetrofit",response.message());   // 정상적이지 않을경우 message 에 오류 내용이 담겨온다.
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TrackData> call, Throwable t) {
//                //통신 자체가 잘못됐을 경우 이쪽으로 넘어온다.
//                Log.d("MainRetrofit","Failure==========================================="); //정상적이지 않을경우 message 에 오류 내용이 담겨온다.
//            }
//        });
//
//    }
//
//    private Track setting_Needs(Data data, Track needs){
//        needs.setArtist(data.getArtist().getName());
//        needs.setTitle(data.getTitle());
//        needs.setAlbum(data.getAlbum().getTitle());
//        needs.setImage(data.getAlbum().getCover());
//        needs.setDuration(data.getDuration());
//        needs.setPreview(data.getPreview());
//        needs.setAlbumList(data.getAlbum().getTracklist());
//        return needs;
//    }
}
