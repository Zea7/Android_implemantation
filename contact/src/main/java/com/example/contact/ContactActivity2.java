package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ContactActivity2 extends AppCompatActivity {
    ImageView iv;
    TextView tv1, tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);

        iv=(ImageView) findViewById(R.id.imageView2);
        tv1=(TextView) findViewById(R.id.name2);
        tv2=(TextView) findViewById(R.id.phone2);

        UiModeManager ui = (UiModeManager) this.getSystemService(Context.UI_MODE_SERVICE);
        if(getIntent().getStringExtra("Icon") == null) {
            if (ui.getNightMode() == UiModeManager.MODE_NIGHT_YES)
                Glide.with(this).load(R.drawable.person_white).into(iv);
            else
                Glide.with(this).load(R.drawable.person).into(iv);
        }
        else
            Glide.with(this).asBitmap().load(getIntent().getStringExtra("Icon")).into(iv);
        tv1.setText(getIntent().getStringExtra("Name2"));
        tv2.setText(getIntent().getStringExtra("PhoneNumber2"));
    }
}