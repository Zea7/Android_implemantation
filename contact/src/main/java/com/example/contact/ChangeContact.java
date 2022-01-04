package com.example.contact;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ChangeContact extends AppCompatActivity {
    private static final int IMAGE_PICK_GALLERY_CODE = 100;
    private EditText getName;
    private EditText getNumber;
    private ImageView icon;
    private Button saveButton;
    private Button cancelButton;
    private String name;
    private String number;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_contact);

        getName = findViewById(R.id.change_name);
        getNumber= findViewById(R.id.change_number);
        icon = findViewById(R.id.change_image);
        saveButton = findViewById(R.id.save_change);
        cancelButton = findViewById(R.id.cancel_change);
        getInfo();

        icon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openGalleryIntent();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeContact(getContentResolver());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changeContact(ContentResolver helper) {
        String setName = getName.getText().toString().trim();
        String setNumber = getNumber.getText().toString().trim();


        if(setNumber.equals("")) setNumber = number;
        if(setName.equals("")) setName = name;

        ArrayList<ContentProviderOperation> cpo = new ArrayList<>();

        String wherePHone = ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"
                + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "=?";
        String[] phoneargs = new String[]{String.valueOf(getContactID(helper, number)), String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)};
        String whereName = ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"
                + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "'";
        String[] nameargs = new String[]{String.valueOf(getContactID(helper, number))};
        String wherePhoto = ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"
                + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'";
        String[] photoargs = new String[]{String.valueOf(getContactID(helper, number))};

        // add name
        cpo.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(whereName, nameargs)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, setName).build());

        // add number
        cpo.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(wherePHone, phoneargs)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, setNumber).build());

        byte[] imageBytes = imageUriToBytes();
        if(imageBytes != null){
            cpo.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(wherePhoto, photoargs)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, imageBytes)
                    .build());
        }
        else{

        }

        try {
            ContentProviderResult[] results = helper.applyBatch(ContactsContract.AUTHORITY, cpo);
            Toast.makeText(this, "수정이 완료되었습니다",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactActivity2.class);
            intent.putExtra("Name2", setName);
            intent.putExtra("PhoneNumber2", setNumber);
            intent.putExtra("Icon", image);
            ContactActivity2 a = (ContactActivity2) ContactActivity2.ContactShowActivity;
            a.finish();
            this.startActivity(intent);
            finish();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
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

    private byte[] imageUriToBytes() {
        Bitmap bitmap;
        ByteArrayOutputStream baos = null;
        try{
            //noinspection deprecation
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(image));

            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            return baos.toByteArray();
        } catch (Exception e){
            return null;
        }
    }



    private void openGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //noinspection deprecation
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                image = data.getData().toString();

                Glide.with(this).load(image).into(icon);
            }
        }

    }

    private void getInfo(){
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");
        image = intent.getStringExtra("image");
        getName.setHint(name);
        getNumber.setHint(number);
        Glide.with(this).load(image).apply(new RequestOptions().error(R.drawable.person)).into(icon);
    }
}