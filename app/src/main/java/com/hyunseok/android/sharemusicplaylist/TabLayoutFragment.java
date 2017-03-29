package com.hyunseok.android.sharemusicplaylist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TabLayoutFragment extends Fragment {

    private static final String ARG_TAB_TYPE = "TAB-TYPE";
    public static final String TYPE_PLAYLIST = "PLAYLIST";
    public static final String TYPE_TRACK = "TRACK";
    public static final String TYPE_TAG = "TAG";
    public static final String TYPE_ALBUM = "ALBUM";
    private String mTabType = "";

    public TabLayoutFragment() {
        // Required empty public constructor
    }

    public static TabLayoutFragment newInstance(String flag) {
        TabLayoutFragment fragment = new TabLayoutFragment();
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
            Log.i("Fragment", "000"+mTabType);

            // TODO 각 TAB 별로 알맞는 소스추가
//            if(TYPE_PLAYLIST.equals(mTabType)) {
//                Log.i("Fragment", "111"+mTabType);
//                Toast.makeText(getContext(), "Playlist"+TYPE_PLAYLIST, Toast.LENGTH_SHORT).show();
//            } else if(TYPE_TRACK.equals(mTabType)) {
//                Log.i("Fragment", "222"+mTabType);
//                Toast.makeText(getContext(), "Tracks"+TYPE_TRACK, Toast.LENGTH_SHORT).show();
//            } else if(TYPE_TAG.equals(mTabType)) {
//                Log.i("Fragment", "333"+mTabType);
//                Toast.makeText(getContext(), "TAG"+TYPE_TAG, Toast.LENGTH_SHORT).show();
//            } else if(TYPE_ALBUM.equals(mTabType)) {
//                Log.i("Fragment", "444"+mTabType);
//                Toast.makeText(getContext(), "Albums"+TYPE_ALBUM, Toast.LENGTH_SHORT).show();
//            }

            switch (mTabType) {
                case TYPE_PLAYLIST:
                    Log.i("Fragment", "111"+mTabType);
                    Toast.makeText(getContext(), "Playlist"+TYPE_PLAYLIST, Toast.LENGTH_SHORT).show();
                    break;
                case TYPE_TRACK:
                    Log.i("Fragment", "222"+mTabType);
                    Toast.makeText(getContext(), "Tracks"+TYPE_TRACK, Toast.LENGTH_SHORT).show();
                    break;
                case TYPE_TAG:
                    Log.i("Fragment", "333"+mTabType);
                    Toast.makeText(getContext(), "TAG"+TYPE_TAG, Toast.LENGTH_SHORT).show();
                    break;
                case TYPE_ALBUM:
                    Log.i("Fragment", "444"+mTabType);
                    Toast.makeText(getContext(), "Albums"+TYPE_ALBUM, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_layout, container, false);
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
