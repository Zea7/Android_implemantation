package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ContactUtils {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh_layout;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;
    private Context content;
    private ImageButton plus;

    public ContactUtils(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, Context content) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        this.content = content;
    }

    public View getContractView(){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_contact, container, false);

        plus = rootView.findViewById(R.id.add_contacts);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(content, AddContact.class);
                content.startActivity(intent);

            }
        });

        refresh_layout = (SwipeRefreshLayout) rootView.findViewById(com.example.contact.R.id.swipe_contact);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(recyclerView, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                        refresh_layout.setRefreshing(false);

                        ContactsRvAdapter newadapter = new ContactsRvAdapter(recyclerView.getContext(), getContacts());
                        recyclerView.swapAdapter(newadapter, true);
                    }
                }, 500);
            }
        });
        recyclerView = rootView.findViewById(R.id.contact);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(content);
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        ContactsRvAdapter adapter = new ContactsRvAdapter(recyclerView.getContext(), getContacts());
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    private List<ModelContacts> getContacts(){
        List <ModelContacts> list = new ArrayList<>();
        Cursor cursor = content.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            list.add(new ModelContacts(
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))));
        }
        return list;
    }
}
