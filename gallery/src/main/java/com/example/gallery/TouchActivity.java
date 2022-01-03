package com.example.gallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RecoverableSecurityException;
import android.app.StatusBarManager;
import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TouchActivity extends AppCompatActivity {
    private String imageName;
    private Uri uri;
    private Context content = this;
    private static final int DELETE_REQUEST_CODE = 13;
    private int pos = 0;

    private enum StatusBarColorType{
        DARK (R.color.invi_navy);

        private int backgroundColorId;

        StatusBarColorType(int backgroundColorId){
            this.backgroundColorId = backgroundColorId;
        }

        protected int getBackgroundColorId(){
            return backgroundColorId;
        }
    }
    private final String TAG = "TouchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_touch);

        Log.d(TAG, "onCreate: started.");

        ActionBar ab = getSupportActionBar();
        ab.hide();

        getWindow().setStatusBarColor(ContextCompat.getColor(this, StatusBarColorType.DARK.getBackgroundColorId()));

        ImageView imageView = findViewById(R.id.back_image);

        getIncomingIntent();

        Button button = findViewById(R.id.back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.pop_up, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_menu){
                            Toast.makeText(TouchActivity.this, imageName, Toast.LENGTH_SHORT).show();
                        }
                        else if(menuItem.getItemId() == R.id.delete){
                            alert(uri);
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("image_path")){
            String imagePath = getIntent().getStringExtra("image_path");
            uri = getIntent().getParcelableExtra("image_uri");

            setImage(imagePath);
            imageName = imagePath.split("/")[imagePath.split("/").length - 1];
        }
    }

    private void setImage(String imagePath) {

        PhotoView image = findViewById(R.id.large_iamge);
        Glide.with(this).asBitmap().load(imagePath).into(image);
    }

    void requestDeletePermission(List<Uri> uriList) {
        PendingIntent pendingIntent = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            pendingIntent = MediaStore.createDeleteRequest(content.getContentResolver(), uriList);
        }

        try {
            Activity activity = (Activity) content;
            assert pendingIntent != null;
            activity.startIntentSenderForResult(pendingIntent.getIntentSender(), 10, null, 0, 0,
                    0, null);

        } catch (Exception e) {
            Toast.makeText(content, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == -1)
                finish();
        }
    }

    void alert(Uri uri) {
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(content);

        builder.setCancelable(true);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        try {
            deleteAPI28(uri, content);
            alertDialog.dismiss();
        }catch (Exception e){

            try {
                deleteAPI30(uri);
            } catch (IntentSender.SendIntentException e1) {
                e1.printStackTrace();
            }
            alertDialog.dismiss();


        }
    }

    private void deleteAPI30(Uri imageUri) throws IntentSender.SendIntentException {
        ContentResolver contentResolver = content.getContentResolver();
        // API 30

        List<Uri> uriList = new ArrayList<>();
        Collections.addAll(uriList, imageUri);
        requestDeletePermission(uriList);

    }

    public static int deleteAPI28(Uri uri, Context context) {
        ContentResolver resolver = context.getContentResolver();
        return resolver.delete(uri, null, null);
    }


}