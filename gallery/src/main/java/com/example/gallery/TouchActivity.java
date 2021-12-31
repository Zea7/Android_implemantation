package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TouchActivity extends AppCompatActivity {
    private final String TAG = "TouchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("image_path") && getIntent().hasExtra("image_name")){
            String imagePath = getIntent().getStringExtra("image_path");
            String imageName = getIntent().getStringExtra("image_name");

            setImage(imagePath, imageName);
        }
    }

    private void setImage(String imagePath, String imageName) {
        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);

        ImageView image = findViewById(R.id.large_iamge);
        Glide.with(this).asBitmap().load(imagePath).into(image);
    }


}