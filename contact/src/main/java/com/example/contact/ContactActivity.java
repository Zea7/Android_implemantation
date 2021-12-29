package com.example.contact;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    RecyclerView userList;
    LinearLayoutManager linearLayoutManager;
    UserListAdapter adapter;
    ArrayList<Person> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        userList = findViewById(R.id.contactList);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adapter = new UserListAdapter(items);
        userList.setLayoutManager(linearLayoutManager);
        userList.setAdapter(adapter);
    }
}
