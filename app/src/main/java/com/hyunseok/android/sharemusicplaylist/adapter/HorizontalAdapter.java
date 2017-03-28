package com.hyunseok.android.sharemusicplaylist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyunseok.android.sharemusicplaylist.R;

import java.util.List;

/**
 * Created by Administrator on 2017-03-28.
 */
public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.Holder> {

    private Context context;
    private List<String> horizontalList;

    public HorizontalAdapter(Context context, List<String> horizontalList) {
        this.context = context;
        this.horizontalList = horizontalList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false); // context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) 와 같다.
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.tv_recycler.setText(horizontalList.get(position));
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView imageView_recycler;
        TextView tv_recycler;

        public Holder(View itemView) {
            super(itemView);
            imageView_recycler = (ImageView) itemView.findViewById(R.id.imageView_recycler);
            tv_recycler = (TextView) itemView.findViewById(R.id.tv_recycler);

            tv_recycler.setOnClickListener(clickListener);
        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }
}
