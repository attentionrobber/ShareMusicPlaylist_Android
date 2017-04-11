package com.hyunseok.android.sharemusicplaylist.domain;

/**
 * Created by kang on 2017-04-06.
 */

public class Track {
    private String artist;
    private String title;
    private String album;
    private String image;
    private int duration;
    private String preview;
    private String albumList;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getAlbumList() {
        return albumList;
    }

    public void setAlbumList(String albumList) {
        this.albumList = albumList;
    }
}
