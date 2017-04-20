package com.hyunseok.android.sharemusicplaylist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.domain.Track;

import java.util.List;

/**
 * Used in : Player
 * Created by KHS 2017-04-20.
 */

public class Player_PlaylistAdapter extends BaseAdapter{

    private List<Track> tracks;
    private LayoutInflater inflater;

    public Player_PlaylistAdapter(List<Track> tracks, Context context) {
        this.tracks = tracks;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public Object getItem(int position) {
        return tracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.player_playlist_item, null);

            holder = new Holder();

            holder.tv_title_playlist = (TextView) convertView.findViewById(R.id.tv_title_playlist);
            holder.tv_artist_playlist = (TextView) convertView.findViewById(R.id.tv_artist_playlist);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // 사용할 데이터 instance를 ArrayList에서 꺼낸다.
        Track track = tracks.get(position);

        holder.tv_title_playlist.setText(track.getTitle());
        holder.tv_artist_playlist.setText(track.getArtist());

        return convertView;
    }

    private class Holder {
        // 각 행에서 사용되는 위젯을 재사용하기 위한 Holder클래스
        // view만 재사용되는게 아니라 Holder를 통해서 위젯들도 재사용이 가능하다.
        public TextView tv_title_playlist, tv_artist_playlist;
    }
}
