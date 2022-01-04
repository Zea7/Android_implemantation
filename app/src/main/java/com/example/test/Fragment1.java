package com.example.test;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gallery.IVAdapter;
import com.example.gallery.IVDecoration;
import com.example.gallery.IVitem;

import java.util.ArrayList;
import java.util.List;
import com.example.contact.*;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh_layout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(int n) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putInt("number", n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ContactUtils utils = new ContactUtils(inflater, container, savedInstanceState, getContext());
        try {
            return utils.getContractView();
        } catch (Exception e){
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
            view = (LinearLayout) rootView.findViewById(R.id.temp_layer);
            refresh_layout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_contact);
            refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(view, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                            refresh_layout.setRefreshing(false);
                            Fragment1 fragment = Fragment1.newInstance(3);

                            FragmentManager fm = getParentFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.fragment_first, fragment).commitAllowingStateLoss();
                        }
                    }, 500);
                }
            });
            return rootView;
        }
    }

}