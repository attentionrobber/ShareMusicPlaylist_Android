package com.hyunseok.android.sharemusicplaylist.domain;

import java.util.List;

/**
 * Created by kang on 2017-04-05.
 */

public class TrackData {
    private List<Data> data;
    private int total;
    public List<Data> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
