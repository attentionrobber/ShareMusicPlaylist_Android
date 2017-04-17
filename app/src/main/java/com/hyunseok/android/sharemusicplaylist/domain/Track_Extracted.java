package com.hyunseok.android.sharemusicplaylist.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Track 클래스에서 뽑아낸(추출된) Track List
 * Player 에서 재생할 때 사용한다.
 * Created by HKS on 2017-04-14.
 */

public class Track_Extracted {
    public static List<Track> tracks = new ArrayList<>();
    public static int position = -1;
}
