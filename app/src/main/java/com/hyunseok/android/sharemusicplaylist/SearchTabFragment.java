package com.hyunseok.android.sharemusicplaylist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyunseok.android.sharemusicplaylist.adapter.SearchRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchTabFragment extends Fragment {

    private static final String ARG_TAB_TYPE = "TAB-TYPE";
    public static final String TYPE_PLAYLIST = "PLAYLIST";
    public static final String TYPE_TRACK = "TRACK";
    public static final String TYPE_TAG = "TAG";
    public static final String TYPE_ALBUM = "ALBUM";
    private String mTabType = "";

    private List<String> datas = new ArrayList<>();

    public SearchTabFragment() {
        // Required empty public constructor
    }

    public static SearchTabFragment newInstance(String flag) {
        SearchTabFragment fragment = new SearchTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TAB_TYPE, flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        datas.clear();

        if (getArguments() != null) {
            mTabType = getArguments().getString(ARG_TAB_TYPE);
            //Log.i("Fragment", "000"+mTabType);
            // TODO 각 TAB 별로 알맞은 소스추가
            // TODO 각 TAB 별로 알맞은 layout 으로 변경이 필요할 시 변경.(추후에 생각)
            switch (mTabType) {
                case TYPE_PLAYLIST:
                    Log.i("Fragment", "111"+mTabType);
                    //Toast.makeText(getContext(), "Playlist"+TYPE_PLAYLIST, Toast.LENGTH_SHORT).show();
                    datas.add("rv_playlist1");
                    datas.add("rv_playlist2");
                    datas.add("rv_playlist3");
                    datas.add("rv_playlist4");
                    datas.add("rv_playlist5");
                    datas.add("rv_playlist6");
                    datas.add("rv_playlist7");
                    datas.add("rv_playlist8");
                    datas.add("rv_playlist9");
                    datas.add("rv_playlist10");
                    break;
                case TYPE_TRACK:
                    Log.i("Fragment", "222"+mTabType);
                    //Toast.makeText(getContext(), "Tracks"+TYPE_TRACK, Toast.LENGTH_SHORT).show();
                    datas.add("rv_track1");
                    datas.add("rv_track2");
                    datas.add("rv_track3");
                    datas.add("rv_track4");
                    datas.add("rv_track5");
                    datas.add("rv_track6");
                    datas.add("rv_track7");
                    datas.add("rv_track8");
                    datas.add("rv_track9");
                    datas.add("rv_track10");
                    break;
                case TYPE_TAG:
                    Log.i("Fragment", "333"+mTabType);
                    //Toast.makeText(getContext(), "TAG"+TYPE_TAG, Toast.LENGTH_SHORT).show();
                    datas.add("rv_TAG1");
                    datas.add("rv_TAG2");
                    datas.add("rv_TAG3");
                    datas.add("rv_TAG4");
                    datas.add("rv_TAG5");
                    datas.add("rv_TAG6");
                    datas.add("rv_TAG7");
                    datas.add("rv_TAG8");
                    datas.add("rv_TAG9");
                    datas.add("rv_TAG10");
                    break;
                case TYPE_ALBUM:
                    Log.i("Fragment", "444"+mTabType);
                    //Toast.makeText(getContext(), "Albums"+TYPE_ALBUM, Toast.LENGTH_SHORT).show();
                    datas.add("rv_album1");
                    datas.add("rv_album2");
                    datas.add("rv_album3");
                    datas.add("rv_album4");
                    datas.add("rv_album5");
                    datas.add("rv_album6");
                    datas.add("rv_album7");
                    datas.add("rv_album8");
                    datas.add("rv_album9");
                    datas.add("rv_album10");
                    break;
                default: break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_tab, container, false);

        // TODO Playlsit 인지 Track 인지 TAG 인지 Album 인지에 따라서 맞는 클릭 이벤트 추가

        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new SearchRecyclerViewAdapter(getContext(), datas, mTabType));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
