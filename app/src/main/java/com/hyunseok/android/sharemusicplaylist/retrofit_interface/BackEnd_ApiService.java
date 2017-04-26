package com.hyunseok.android.sharemusicplaylist.retrofit_interface;

import com.hyunseok.android.sharemusicplaylist.domain.TrackData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kang on 2017-04-05.
 */

public interface BackEnd_ApiService {
    //http://api.deezer.com/search?q=track:"강남스타일"
    @GET("api/search/list/")
    Call<TrackData> getData(@Query("q") String value);
    @GET("api/search/playlist/")
    Call<TrackData> getList(@Query("q") String value);
}
