package com.example.gallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragment extends Fragment {

    private IVitem item;
    private ArrayList<IVitem> images;
    private int position;
    private int pos;


    public ImageFragment() {
        // Required empty public constructor
    }

    public ImageFragment(IVitem item){
        this.item = item;
    }


    public static ImageFragment newInstance(ArrayList<IVitem> images, int position) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putSerializable("images",images);
        args.putInt("position",position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            images = (ArrayList) getArguments().getParcelableArrayList("images");
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_image, container, false);
        PhotoView photo = rootView.findViewById(R.id.fragment_image);

        Glide.with(getContext()).load(images.get(position).getUri()).into(photo);
        return rootView;
    }
}