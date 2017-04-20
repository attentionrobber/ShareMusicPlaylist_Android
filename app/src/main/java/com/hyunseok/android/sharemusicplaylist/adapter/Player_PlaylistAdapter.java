package com.hyunseok.android.sharemusicplaylist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.player.Player;
import com.hyunseok.android.sharemusicplaylist.player.PlayerService;

import java.util.List;

/**
 * Used in : Player
 * Created by KHS 2017-04-20.
 */

public class Player_PlaylistAdapter extends RecyclerView.Adapter<Player_PlaylistAdapter.Holder> {

    private Context context;
    private List<Track> tracks;

    public Player_PlaylistAdapter(List<Track> tracks, Context context) {
        this.tracks = tracks;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.player_playlist_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final Track track = tracks.get(position);

        holder.position = position;
        holder.tv_title_playlist.setText(track.getTitle());
        holder.tv_artist_playlist.setText(track.getArtist());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        int position;
        LinearLayout linearLayout;
        TextView tv_title_playlist, tv_artist_playlist;

        Holder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            tv_title_playlist = (TextView) view.findViewById(R.id.tv_title_playlist);
            tv_artist_playlist = (TextView) view.findViewById(R.id.tv_artist_playlist);
            linearLayout.setOnClickListener(v -> {
                // TODO 선택부분 재생
                PlayerService.position = position;
                Player.play(context);
            });
        }
    }
}
