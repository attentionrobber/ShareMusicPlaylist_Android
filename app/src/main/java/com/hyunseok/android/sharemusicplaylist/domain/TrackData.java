package com.hyunseok.android.sharemusicplaylist.domain;

import java.util.List;

/**
 * Created by kang on 2017-04-05.
 */

public class TrackData {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
//    @Override
//    public String toString()
//    {
//        return "ClassPojo [data = "+data+"]";
//    }
}
