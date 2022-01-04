package com.example.contact;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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

    public static Cursor getContactCursor(ContentResolver contactHelper, String startsWith){
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = null;
        try{
            if(startsWith != null && !startsWith.equals("")){
                cursor = contactHelper.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " line \""
                + startsWith + "%\"", null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC");
            }
            else{
                cursor = contactHelper.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            }
            cursor.moveToFirst();
        } catch (Exception e){
            e.printStackTrace();
        }
        return cursor;
    }

    public static void deleteContact(ContentResolver contactHelper, String number){
        ArrayList<ContentProviderOperation> cpo = new ArrayList<>();
        String[] args = new String[]{String.valueOf(getContactID(contactHelper, number))};
        cpo.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=?", args).build());
        try{
            contactHelper.applyBatch(ContactsContract.AUTHORITY, cpo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static long getContactID(ContentResolver contactHelper, String number) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String[] projection = {ContactsContract.PhoneLookup._ID};
        Cursor cursor = null;

        try{
            cursor = contactHelper.query(contactUri, projection, null, null, null);
            if(cursor.moveToFirst()){
                int personID = cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID);
                return cursor.getLong(personID);
            }
            return -1;
        } catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if(cursor!=null){
                cursor.close();
                cursor=null;
            }
        }
        return -1;
    }
}
