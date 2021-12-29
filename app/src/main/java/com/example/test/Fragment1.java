package com.example.test;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gallery.IVAdapter;
import com.example.gallery.IVDecoration;
import com.example.gallery.IVitem;

import java.util.ArrayList;
import java.util.List;
import com.example.contact.*;

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
    private RecyclerView recyclerView;
    private UserListAdapter adapter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_gallery, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.icon_gallery);

        List<Person> contactList = new ArrayList<Person>();
        Person person = new Person("성민정", "010-4399-2755");
        contactList.add(person);

        person = new Person("복승주",  "010-6679-3956");
        contactList.add(person);

        person = new Person("탁유성",  "010-7810-3987");
        contactList.add(person);

        person = new Person("예효기",  "010-6936-3816");
        contactList.add(person);

        person = new Person("박병욱",  "010-4010-8889");
        contactList.add(person);

        person = new Person("홍주연",  "010-4043-2603");
        contactList.add(person);

        person = new Person("강원재",  "010-5152-5826");
        contactList.add(person);

        person = new Person("송지현",  "010-2345-8474");
        contactList.add(person);

        person = new Person("안원준",  "010-2618-3674");
        contactList.add(person);
        adapter = new UserListAdapter(contactList);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }
}