package com.hyunseok.android.sharemusicplaylist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.PlaylistDetailActivity;
import com.hyunseok.android.sharemusicplaylist.PlaylistDetailActivity_;
import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.SearchTabFragment;

import java.util.List;

/**
 * Search Tab 에서 검색 하면 나오는
 * Playlist, Tracks, TAG, Album 에서의
 * RecyclerView Adapter
 *
 * Created by Administrator on 2017-03-29.
 */

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.Holder>{

    private int item_layout_id;

    private Context context;
    private List<String> datas;
    private String flag;

    public SearchRecyclerViewAdapter(Context context, List<String> datas, String flag) {
        this.context = context;
        this.datas = datas;
        this.flag = flag;

        // TODO layout 바꾸기. -> playlist tab, tracks tab, TAG tab, albums tab 따로. 아래 ViewHolder 에서도 바꿔줘야한다.
        switch (flag) { // MainActivity에서 Adapter를 호출할 때 View를 바꿔줄 수 있다.
            case SearchTabFragment.TYPE_PLAYLIST:
                item_layout_id = R.layout.fragment_search_tab_item;
                break;
            case SearchTabFragment.TYPE_TRACK:
                item_layout_id = R.layout.fragment_search_tab_item;
                break;
            case SearchTabFragment.TYPE_TAG:
                item_layout_id = R.layout.fragment_search_tab_item;
                break;
            case SearchTabFragment.TYPE_ALBUM:
                item_layout_id = R.layout.fragment_search_tab_item;
                break;
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_search_tab_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        //Common common = (Common) datas.get(position);

        holder.position = position; // 현재 위치 받아오기
        holder.tv_title_tabitem.setText(datas.get(position));
        holder.tv_artist_tabitem.setText(datas.get(position));
//        Glide.with(context).load(common.getImageUri())
//                .placeholder(R.mipmap.default_album_image).into(holder.imageView_tabitem);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public int position;

        public int id;
        public String title;

        RelativeLayout itemLayout;
        ImageView imageView_tabitem;
        TextView tv_title_tabitem, tv_artist_tabitem;

        public Holder(View view) {
            super(view);

            itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            imageView_tabitem = (ImageView) view.findViewById(R.id.imageView_tabitem);
            tv_title_tabitem = (TextView) view.findViewById(R.id.tv_title_tabitem);
            tv_artist_tabitem = (TextView) view.findViewById(R.id.tv_artist_tabitem);

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO my playlist 로 추가

                    switch (flag) { // MainActivity에서 Adapter를 호출할 때 View를 바꿔줄 수 있다.
                        case SearchTabFragment.TYPE_PLAYLIST:
                            action_playlist();
                            break;
                        case SearchTabFragment.TYPE_TRACK:
                            action_track();
                            break;
                        case SearchTabFragment.TYPE_TAG:
                            action_TAG();
                            break;
                        case SearchTabFragment.TYPE_ALBUM:
                            action_album();
                            break;
                    }
                }
            });
        }
    }

    private void action_playlist() {
        Intent intent = new Intent(context, PlaylistDetailActivity_.class);
        context.startActivity(intent);
    }
    private void action_track() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Select Actions");
        final CharSequence items[] = {"Play", "Add to Playlist"};
        alertDialog.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    // TODO Play 되게
                    Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    // TODO Add to Playlist 되게
                    Toast.makeText(context, "Add to Playlist", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
        alertDialog.show();
    }
    private void action_TAG() {

    }
    private void action_album() {

    }
}