package com.hyunseok.android.sharemusicplaylist.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.domain.Track;

import java.util.List;

/**
 * Used in : Player
 * Created by KHS on 2017-04-03.
 */

public class PlayerAdapter extends PagerAdapter {

    private List<Track> datas;
    private Context context;

    private LayoutInflater inflater;

    public PlayerAdapter(List<Track> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);

        View view = inflater.inflate(R.layout.player_card_item, null); // parent가 없는 inflater를 사용해야하므로

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_artist = (TextView) view.findViewById(R.id.tv_artist);

        // 데이터 가져오기
        Track track = datas.get(position);

        tv_title.setText(track.getTitle());
        tv_artist.setText(track.getArtist());

        Glide.with(context).load(R.mipmap.default_album_image).placeholder(R.mipmap.default_album_image).into(imageView); // placeholder()는 디폴트 이미지를 지정해줄 수 있다.

        // 생성한 뷰를 컨테이너에 담아준다. 컨테이너 = 뷰페이저를 생성한 최외곽 레이아웃 개념
        container.addView(view);

        return view;
    }

    // 화면에 보이지 않는 요소(View)들을 메모리에서 지워줌.
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

    // instantiateItem에서 리턴된 Object가 View인지 아닌지 확인.
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
