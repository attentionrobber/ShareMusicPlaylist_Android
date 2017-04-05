package com.hyunseok.android.sharemusicplaylist.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.SerializableType;
import com.j256.ormlite.table.DatabaseTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-04-04.
 */

@DatabaseTable(tableName = "playlist")
public class Playlist {

    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    String title;
    @DatabaseField
    boolean isShare;
    @DatabaseField(persisterClass = SerializableCollectionsType.class)
    List<String> tracks;
    @DatabaseField
    String imgUri;
    @DatabaseField
    Date curDate;

    public Playlist() {

    }

    // create 시 사용되는 생성자
    public Playlist(String title, List<String> tracks, String imgUri, Date curDate) {
        this.title = title;
        this.isShare = false;
        this.tracks = tracks;
        this.imgUri = imgUri;
        this.curDate = curDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsShare() {
        return isShare;
    }

    public String getImgUri() {
        return imgUri;
    }

    public Date getCurDate() {
        return curDate;
    }

    public List<String> getTracks() {
        return tracks;
    }

    public void setTracks(List<String> tracks) {
        this.tracks = tracks;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShare(boolean share) {
        isShare = share;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }
}

/**
 * List 를 쓰기 위한 클래스
 */
class SerializableCollectionsType extends SerializableType {
    private static SerializableCollectionsType singleton;
    public SerializableCollectionsType() {
        super(SqlType.SERIALIZABLE, new Class<?>[0]);
    }

    public static SerializableCollectionsType getSingleton() {
        if (singleton == null) {
            singleton = new SerializableCollectionsType();
        }
        return singleton;
    }

    @Override
    public boolean isValidForField(Field field) {
        return Collection.class.isAssignableFrom(field.getType());
    }
}