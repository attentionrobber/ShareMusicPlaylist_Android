package com.hyunseok.android.sharemusicplaylist.domain;

/**
 * Track 클래스에서 뽑아낸(추출된) Track 하나
 * Player 에서 재생할 때 사용
 * Created by HKS on 2017-04-14.
 */

public class Track_Extracted {

    public static String artist;
    public static String title;
    public static String album;
    public static String image;
    public static int duration;
    public static String preview;
    public static String albumList;

    public static void putTrack(Track track) {
        artist = track.getArtist();
        title = track.getTitle();
        album = track.getAlbum();
        image = track.getImage();
        duration = track.getDuration();
        preview = track.getPreview();
        albumList = track.getAlbumList();
    }
//    public Track_Extracted(Track track) {
//        artist = track.getArtist();
//        title = track.getTitle();
//        album = track.getAlbum();
//        image = track.getImage();
//        duration = track.getDuration();
//        preview = track.getPreview();
//        albumList = track.getAlbumList();
//    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getImage() {
        return image;
    }

    public int getDuration() {
        return duration;
    }

    public String getPreview() {
        return preview;
    }

    public String getAlbumList() {
        return albumList;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public void setAlbumList(String albumList) {
        this.albumList = albumList;
    }
}
