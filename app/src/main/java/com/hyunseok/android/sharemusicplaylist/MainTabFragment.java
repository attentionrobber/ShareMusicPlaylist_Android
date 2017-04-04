package com.hyunseok.android.sharemusicplaylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.hyunseok.android.sharemusicplaylist.adapter.HorizontalAdapter;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter;
import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter;
import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter_Sample;
import com.hyunseok.android.sharemusicplaylist.data.DBHelper;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;
import com.hyunseok.android.sharemusicplaylist.util.Logger;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainTabFragment extends Fragment {

    private static final String ARG_TAB_TYPE = "TAB-TYPE";
    public static final String TYPE_PLAYER = "PLAYER";
    public static final String TYPE_PLAYLIST = "PLAYLIST";
    public static final String TYPE_SEARCH = "SEARCH";
    private String mTabType = "";
    private int layout;

    // Search Tab
    RecyclerView recyclerView_horizon;
    HorizontalAdapter horizontalAdapter;

    List<String> horizontalList;

    ImageButton btn_search;

    // Player Tab
    ViewPager viewPager_player;
    PlayerAdapter playerAdapter;

    List<String> cardDatas;

    // Playlist Tab
    RecyclerView rv_myPlaylist, rv_followPlaylist;
    PlaylistRecyclerViewAdapter_Sample followPlaylistAdapter;
    PlaylistRecyclerViewAdapter myPlaylistAdapter;
    List<Playlist> myPlaylist = new ArrayList<>();

    List<String> playlistDatas;

    FloatingActionButton btn_newList;


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
        Logger.print("MainTabFragment", "=================onCreate===================");
        if (getArguments() != null) {
            mTabType = getArguments().getString(ARG_TAB_TYPE);

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
                default:
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.print("MainTabFragment", "=================onCreateView===================");
        View view = inflater.inflate(layout, container, false);
        switch(layout) {
            case R.layout.fragment_main_searchtab:
                init_searchTab(view);
                break;
            case R.layout.fragment_main_playertab:
                init_playerTab(view);
                break;
            case R.layout.fragment_main_playlisttab:
                init_playlistTab(view);
                break;
            default: break;
        }

        return view;
    }

    private void init_searchTab(View view) {
        recyclerView_horizon = (RecyclerView) view.findViewById(R.id.recyclerView_horizon);
        btn_search = (ImageButton) view.findViewById(R.id.imgbtn_search);
        btn_search.setOnClickListener(clickListener);

        horizontalList = new ArrayList<>();
        horizontalList.add("horizontal 1");
        horizontalList.add("horizontal 2");
        horizontalList.add("horizontal 3");
        horizontalList.add("horizontal 4");
        horizontalList.add("horizontal 5");
        horizontalList.add("horizontal 6");
        horizontalList.add("horizontal 7");
        horizontalList.add("horizontal 8");
        horizontalList.add("horizontal 9");
        horizontalList.add("horizontal 10");

        horizontalAdapter = new HorizontalAdapter(getContext(), horizontalList);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_horizon.setLayoutManager(horizontalLayoutManager);
        recyclerView_horizon.setAdapter(horizontalAdapter);
    }

    private void init_playerTab(View view) {
        viewPager_player = (ViewPager) view.findViewById(R.id.viewPager_player);

        cardDatas = new ArrayList<>();
        cardDatas.add("card1");
        cardDatas.add("card2");
        cardDatas.add("card3");
        cardDatas.add("card4");
        cardDatas.add("card5");
        cardDatas.add("card6");
        cardDatas.add("card7");
        cardDatas.add("card8");
        cardDatas.add("card9");
        cardDatas.add("card10");

        playerAdapter = new PlayerAdapter(cardDatas, getContext());
        viewPager_player.setAdapter(playerAdapter);

    }

    private void init_playlistTab(View view) {
        rv_myPlaylist = (RecyclerView) view.findViewById(R.id.rv_myPlaylist);
        rv_followPlaylist = (RecyclerView) view.findViewById(R.id.rv_followPlaylist);

        btn_newList = (FloatingActionButton) view.findViewById(R.id.btn_newList);
        btn_newList.setOnClickListener(clickListener);

        try {
            loadPlaylist();
        } catch (Exception e) {
            e.printStackTrace();
        }

        playlistDatas = new ArrayList<>();
        playlistDatas.add("playlist1");
        playlistDatas.add("playlist2");
        playlistDatas.add("playlist3");
        playlistDatas.add("playlist4");
        playlistDatas.add("playlist5");
        playlistDatas.add("playlist6");
        playlistDatas.add("playlist7");
        playlistDatas.add("playlist8");
        playlistDatas.add("playlist9");
        playlistDatas.add("playlist10");

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

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.imgbtn_search:
                    intent = new Intent(getContext(), SearchActivity_.class);
                    startActivity(intent);
                    break;
                case R.id.btn_newList:
                    intent = new Intent(getContext(), PlaylistNewActivity_.class);
                    startActivity(intent);
                    break;
                default: break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        switch(layout) {
            case R.layout.fragment_main_searchtab:
                break;
            case R.layout.fragment_main_playertab:
                break;
            case R.layout.fragment_main_playlisttab:
                init_playlistTab(getView());
                break;
            default: break;
        }
        //Logger.print("MainTabFragment", "=================onResume===================");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Logger.print("MainTabFragment", "=================onAttach===================");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Logger.print("MainTabFragment", "=================onDetach===================");
    }
}
