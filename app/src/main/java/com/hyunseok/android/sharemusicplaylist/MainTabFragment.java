package com.hyunseok.android.sharemusicplaylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.player.Player;
import com.hyunseok.android.sharemusicplaylist.adapter.HorizontalAdapter;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter_sample;
import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter;
import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter_Sample;
import com.hyunseok.android.sharemusicplaylist.data.DBHelper;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity 의 TabLayout 3가지의 Fragment
 * Search Tab, Player Tab, Playlist Tab
 */

public class MainTabFragment extends Fragment {

    private static final String ARG_TAB_TYPE = "TAB-TYPE";
    public static final String TYPE_PLAYER = "PLAYER";
    public static final String TYPE_PLAYLIST = "PLAYLIST";
    public static final String TYPE_SEARCH = "SEARCH";
    private String mTabType = "";
    private int layout;

    private List<Track> tracks = new ArrayList<>(); // Music Data

    // Playlist Tab
    RecyclerView rv_myPlaylist, rv_followPlaylist;
    PlaylistRecyclerViewAdapter_Sample followPlaylistAdapter;
    PlaylistRecyclerViewAdapter myPlaylistAdapter;
    List<Playlist> myPlaylist = new ArrayList<>();
    List<Track> playlistDatas;

    FloatingActionButton btn_newMyList, btn_newFollowList;

    public MainTabFragment() {
        // Required empty public constructor
    }

    public static MainTabFragment newInstance(String flag) {
        MainTabFragment fragment = new MainTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TAB_TYPE, flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabType = getArguments().getString(ARG_TAB_TYPE);

            if (mTabType != null) {
                switch (mTabType) {
                    case TYPE_SEARCH:
                        layout = R.layout.fragment_main_searchtab;
                        break;
                    case TYPE_PLAYER:
                        layout = R.layout.fragment_main_playertab;
                        break;
                    case TYPE_PLAYLIST:
                        layout = R.layout.fragment_main_playlisttab;
                        break;
                    default: break;
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout, container, false);
        switch(layout) {
            case R.layout.fragment_main_searchtab:
                Log.i("MainTabFragment", "onCreate Search=================================");
                init_searchTab(view);
                break;
            case R.layout.fragment_main_playertab:
                Log.i("MainTabFragment", "onCreate Player=================================");
                init_playerTab(view);
                break;
            case R.layout.fragment_main_playlisttab:
                Log.i("MainTabFragment", "onCreate Playlist=================================");
                init_playlistTab(view);
                break;
            default: break;
        }

        return view;
    }

    public void init_searchTab(View view) {
        SearchFunction searchFunction = new SearchFunction(view, getContext());
        searchFunction.init_searchTab();
    }

    public void init_playerTab(View view) {
        Player player = Player.getInstance();
        player.execute(view, getActivity(), getContext());

//        Player player = new Player(view, getContext());
//        player.execute();
    }

    public void init_playlistTab(View view) {
        rv_myPlaylist = (RecyclerView) view.findViewById(R.id.rv_myPlaylist);
        rv_followPlaylist = (RecyclerView) view.findViewById(R.id.rv_followPlaylist);

        btn_newMyList = (FloatingActionButton) view.findViewById(R.id.btn_newMyList);
        btn_newFollowList = (FloatingActionButton) view.findViewById(R.id.btn_newFollowList);
        btn_newMyList.setOnClickListener(clickListener);
        btn_newFollowList.setOnClickListener(clickListener);

        try {
            loadPlaylist();
        } catch (Exception e) {
            e.printStackTrace();
        }

        playlistDatas = new ArrayList<>();

        myPlaylistAdapter = new PlaylistRecyclerViewAdapter(getContext(), myPlaylist, "my");
        followPlaylistAdapter = new PlaylistRecyclerViewAdapter_Sample(getContext(), playlistDatas, "follow");

        rv_myPlaylist.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_followPlaylist.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_myPlaylist.setAdapter(myPlaylistAdapter);
        rv_followPlaylist.setAdapter(followPlaylistAdapter);
    }

    private void loadPlaylist() throws SQLException {
        DBHelper dbHelper = OpenHelperManager.getHelper(getContext(), DBHelper.class);
        Dao<Playlist, Integer> playlistDao = dbHelper.getPlaylistDao();
        myPlaylist = playlistDao.queryForAll();
    }

    View.OnClickListener clickListener = v -> {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.imgbtn_search:
                intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_newMyList:
                intent = new Intent(getContext(), PlaylistNewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_newFollowList:
                // TODO Follow Playlist 만들기 버튼 눌렀을 때 기능 추가
                Toast.makeText(getContext(), "New Follow List", Toast.LENGTH_SHORT).show();
                break;
            default: break;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
//        switch(layout) {
//            case R.layout.fragment_main_searchtab:
//                break;
//            case R.layout.fragment_main_playertab:
//                Log.i("MainTabFragment", "onResume Player=================================");
//                init_playerTab(getView());
//                break;
//            case R.layout.fragment_main_playlisttab:
//                Log.i("MainTabFragment", "onResume Playlist=================================");
//                init_playlistTab(getView());
//                break;
//            default: break;
//        }
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
