package com.hyunseok.android.sharemusicplaylist;

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
import com.hyunseok.android.sharemusicplaylist.domain.Needs;

import java.util.List;

/**
 * Created by kang on 2017-04-06.
 */

public class RecyclerViewAdpt extends RecyclerView.Adapter<RecyclerViewAdpt.Holder>{
    Context context;
    List<Needs> datas;
    public RecyclerViewAdpt(Context context, List<Needs> datas){
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
    }

    @Override
    public int getItemCount() {return datas.size();}

    public class Holder extends RecyclerView.ViewHolder {
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
                        Toast.makeText(context, "Click Play Button", Toast.LENGTH_SHORT).show();
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
