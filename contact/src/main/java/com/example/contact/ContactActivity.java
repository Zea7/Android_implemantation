package com.example.contact;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private final List<Person> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        bindList();
    }
    private void bindList() {
        // List item create
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

        // Recycler view
        RecyclerView recyclerView = findViewById(R.id.contactList);

        // Adapter 추가
        UserListAdapter adapter = new UserListAdapter(contactList);
        recyclerView.setAdapter(adapter);

        // Layout manager 추가
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

}
