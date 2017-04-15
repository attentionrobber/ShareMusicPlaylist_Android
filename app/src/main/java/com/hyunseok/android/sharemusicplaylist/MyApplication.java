package com.hyunseok.android.sharemusicplaylist;

import android.app.Application;
import android.content.res.Configuration;

import com.hyunseok.android.sharemusicplaylist.domain.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * 모든 Activity 에서 사용할 ...
 * Created by KHS on 2017-04-13.
 */

public class MyApplication extends Application {

    private List<Track> tracks;
    

    public MyApplication() {
        tracks = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
