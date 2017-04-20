package com.hyunseok.android.sharemusicplaylist;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.domain.Track_Extracted;
import com.hyunseok.android.sharemusicplaylist.player.Player;
import com.hyunseok.android.sharemusicplaylist.player.PlayerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kang on 2017-04-06.
 */

public class RecyclerViewAdpt extends RecyclerView.Adapter<RecyclerViewAdpt.Holder>{
    Context context;
    private ArrayList<Track> datas;
    public RecyclerViewAdpt(Context context, ArrayList<Track> datas){
        this.context = context;
        this.datas = datas;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Glide.with(context).load(datas.get(position).getImage()).into(holder.cover_image);
        holder.txtTitle.setText(datas.get(position).getTitle());
        holder.txtArtist.setText(datas.get(position).getArtist());
        holder.txtAlbum.setText(datas.get(position).getAlbum());
        holder.position = position;
    }

    @Override
    public int getItemCount() {return datas.size();}

    public class Holder extends RecyclerView.ViewHolder {
        private int position;

        ImageView cover_image;
        ImageButton btnPlay,btnMore;
        TextView txtTitle, txtArtist, txtAlbum;
        CardView cardView;

        public Holder(View view) {
            super(view);
            cover_image = (ImageView)view.findViewById(R.id.imageView);
            btnPlay = (ImageButton)view.findViewById(R.id.btnPlay);
            btnMore = (ImageButton)view.findViewById(R.id.btnMore);
            txtTitle = (TextView)view.findViewById(R.id.txtTitle);
            txtArtist= (TextView)view.findViewById(R.id.txtArtist);
            txtAlbum= (TextView)view.findViewById(R.id.txtAlbum);
            cardView = (CardView)view.findViewById(R.id.cardView);

            btnPlay.setOnClickListener(listener);
            btnMore.setOnClickListener(listener);
            cardView.setOnClickListener(listener);
        }
        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnPlay:
                        // TODO Play Button 한번 더 눌렀을 때 동작. 새로 추가하기 or 현재 Playlist 에 추가하기 둘중 생각해보기
                        Track_Extracted.tracks.add(datas.get(position)); // 해당 position 의 Track 하나를 추출한다.
                        PlayerService.position = Track_Extracted.tracks.size()-1;
                        // TODO 같은 음악일 경우 add 안되도록
                        Player.play(context);
                        MainActivity.changeTab("PLAYER"); // PLAYER Tab 으로 변경하고 view 도 refresh 해준다.
                        break;
                    case R.id.btnMore:
                        Toast.makeText(context, "Click More Button", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cardView:
                        Toast.makeText(context, "Click Card view", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }
}
