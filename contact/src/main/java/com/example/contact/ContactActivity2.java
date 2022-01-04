package com.example.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ContactActivity2 extends AppCompatActivity {
    ImageView iv;
    TextView tv1, tv2;
    private String number;
    private String name;
    private String imagePath;
    public static Activity ContactShowActivity;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        else if(item.getItemId()==R.id.delete_contact) {
            Toast.makeText(ContactActivity2.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
            deleteContact();
            finish();
        }
        else if(item.getItemId()==R.id.change_contact){
            ContactUtils.changeContact(name, number, imagePath, this);
        }


        return super.onOptionsItemSelected(item);
    }

    private void deleteContact() {
        ContactUtils.deleteContact(getContentResolver(), number);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);

        ContactShowActivity = ContactActivity2.this;

        getSupportActionBar().setTitle("연락처");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        number = getIntent().getStringExtra("PhoneNumber2");
        name = getIntent().getStringExtra("Name2");
        imagePath = getIntent().getStringExtra("Icon");
    }
}