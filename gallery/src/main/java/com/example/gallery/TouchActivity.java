package com.example.gallery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.StatusBarManager;
import android.app.UiModeManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

public class TouchActivity extends AppCompatActivity {
    private String imageName;
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

            setImage(imagePath);
            imageName = imagePath.split("/")[imagePath.split("/").length - 1];
        }
    }

    private void setImage(String imagePath) {

        PhotoView image = findViewById(R.id.large_iamge);
        Glide.with(this).asBitmap().load(imagePath).into(image);
    }


}