package com.hyunseok.android.sharemusicplaylist.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.RecyclerViewAdpt;
import com.hyunseok.android.sharemusicplaylist.domain.API_URL;

import com.hyunseok.android.sharemusicplaylist.domain.Results;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.domain.Track_Data;
import com.hyunseok.android.sharemusicplaylist.retrofit_interface.BackEndApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kang on 2017-04-05.
 */

public class RetrofitDeezerUtil {

    private Retrofit retrofit;
    private BackEndApiService service;
    private List<Track> track_Datas, search_Datas;

    private Context context;
    private RecyclerView recyclerView_track,recyclerView_album;
    private TextView txtAlbum,txtTrack,txtTrackMore;
    private View view;
    public RetrofitDeezerUtil(Context context, View view){
        this.context = context;
        this.view = view;
        this.recyclerView_track = (RecyclerView)view.findViewById(R.id.rcy_Search_Track);
        this.recyclerView_album = (RecyclerView)view.findViewById(R.id.rcy_Search_Album);
        this.txtTrack = (TextView)view.findViewById(R.id.txtTrack);
        this.txtAlbum= (TextView)view.findViewById(R.id.txtAlbum);
        this.txtTrackMore = (TextView)view.findViewById(R.id.txtTrackMore);
        this.txtTrackMore.setOnClickListener(listener);

    }
    public void setRetrofit() {
        //레트로 핏을 생성하고
        //http://api.deezer.com/search?q=track:"강남스타일"

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL.backend_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //사용할 인터페이스를 설정한다
        service = retrofit.create(BackEndApiService.class);
        //데이터를 가져온다

    }

    public void searchTrack(String value){
        Call<Track_Data> result = service.getData(value);
        result.enqueue(new Callback<Track_Data>() {
            @Override
            public void onResponse(Call<Track_Data> call, Response<Track_Data> response) {
                if(response.isSuccessful()) {
                    track_Datas = new ArrayList<>();
                    Track_Data trackData = response.body();
                    //json = response.body().toString();
                    for(Results result: trackData.getResults()){
                        Track tracks= new Track();
                        setting_Needs(result,tracks);
                        track_Datas.add(tracks);
                    }
                    setting_recyclerView();
                    txtTrack.setText("곡 검색("+ trackData.getCount()+")");
                }else{
                    Logger.print("MainRetrofit",response.message());   //정상적이지 않을경우 message에 오류 내용이 담겨온다.
                }
            }

            @Override
            public void onFailure(Call<Track_Data> call, Throwable t) {
                //통신 자체가 잘못됐을 경우 이쪽으로 넘어온다.
                Logger.print("MainRetrofit","Failure===========================================");   //정상적이지 않을경우 message에 오류 내용이 담겨온다.
            }
        });


    }
    public void setting_recyclerView(){
        search_Datas = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            search_Datas.add(track_Datas.get(i));
        }
        RecyclerViewAdpt adapter = new RecyclerViewAdpt(context, search_Datas);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView_track.setLayoutManager(manager);
        recyclerView_track.setAdapter(adapter);
    }
    public Track setting_Needs(Results result, Track track){
        track.setArtist(result.getArtist());
        track.setTitle(result.getTitle());
        track.setAlbum(result.getAlbum_title());
        track.setImage(result.getAlbum_picture_big());
        track.setDuration(Integer.parseInt(result.getDuration()));
        track.setPreview(result.getPreview());
        track.setAlbum_id(result.getAlbum_id());
        return track;
    }

    public void searchList(String value){

        Logger.print("SearchFucntion", "search Track Title ==========================" +value);
        Call<Track_Data> result = service.getData(value);
        result.enqueue(new Callback<Track_Data>() {
            @Override
            public void onResponse(Call<Track_Data> call, Response<Track_Data> response) {
                if(response.isSuccessful()) {
                    track_Datas = new ArrayList<>();
                    Track_Data trackData = response.body();
                    //json = response.body().toString();
                    for(Results result: trackData.getResults()){
                        Track tracks= new Track();
                        setting_Needs(result,tracks);
                        track_Datas.add(tracks);
                    }
                    Logger.print("SearchFunction","first search Title============"+ track_Datas.get(0).getTitle());
                    setting_recyclerView();
                    txtTrack.setText("곡 검색("+ trackData.getCount()+")");
                }else{
                    Logger.print("MainRetrofit",response.message());   //정상적이지 않을경우 message에 오류 내용이 담겨온다.
                }
            }

            @Override
            public void onFailure(Call<Track_Data> call, Throwable t) {
                //통신 자체가 잘못됐을 경우 이쪽으로 넘어온다.
                Logger.print("MainRetrofit","Failure===========================================");   //정상적이지 않을경우 message에 오류 내용이 담겨온다.
            }
        });
    }
    View.OnClickListener listener = v -> {
        //TODO searchActivity로 이동
        //Intent intent = new Intent(context, SearchActivity_.class);
        Toast.makeText(context, "Move to Search Result Activity", Toast.LENGTH_SHORT).show();
    };

}
