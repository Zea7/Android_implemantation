package com.example.test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gallery.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private IVAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(int n) {
        Fragment2 fragment = new Fragment2();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_gallery, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.icon_gallery);

        List<IVitem> items = new ArrayList<IVitem>();
        for(int i=1; i<=26; i++){
            items.add(new IVitem(getResources().getIdentifier("_" + i, "drawable", getActivity().getPackageName())));
        }

        adapter = new IVAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new IVDecoration(getContext()));

        return rootView;
    }
}