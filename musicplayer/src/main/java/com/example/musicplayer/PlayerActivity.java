package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    Button btnPlay, btnNext, btnPrev, btnFf, btnFr;
    TextView txtSongName,txtSongArtist, txtSongStart, txtSongStop;
    SeekBar seekMusic;
    BarVisualizer visualizer;
    ImageView imageView;

    String sName, sArtist, imagePath;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    String mySongs;
    ArrayList<Music> musicArrayList;
    Thread updateSeekBar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(visualizer != null){
            visualizer.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnPrev = findViewById(R.id.prevbtn);
        btnNext = findViewById(R.id.nextbtn);
        btnPlay = findViewById(R.id.playbtn);
        btnFf = findViewById(R.id.ffbtn);
        btnFr = findViewById(R.id.frbtn);
        txtSongName = findViewById(R.id.songName2);
        txtSongArtist = findViewById(R.id.songArtist2);
        txtSongStart = findViewById(R.id.txtSongStart);
        txtSongStop = findViewById(R.id.txtSongStop);
        seekMusic = findViewById(R.id.seekBar);
        visualizer = findViewById(R.id.blast);
        imageView = findViewById(R.id.playerImageView);

        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = i.getStringExtra("songs");
        musicArrayList = (ArrayList) bundle.getParcelableArrayList("songList");
        position = i.getIntExtra("position",0);
        txtSongName.setSelected(true);
        txtSongArtist.setSelected(true);
        Uri uri = Uri.parse(mySongs);
        sName = i.getStringExtra("songName");
        sArtist = i.getStringExtra("songArtist");
        imagePath = i.getStringExtra("songImage");
        Uri imagePathUri = Uri.parse(imagePath);

        txtSongName.setText(sName);
        txtSongArtist.setText(sArtist);
        UiModeManager ui = (UiModeManager) this.getSystemService(Context.UI_MODE_SERVICE);
        if(ui.getNightMode() == UiModeManager.MODE_NIGHT_YES)
            Glide.with(this).asBitmap().load(imagePath).apply(new RequestOptions().error(R.drawable.music_note_white)).into(imageView);
        else
            Glide.with(this).asBitmap().load(imagePath).apply(new RequestOptions().error(R.drawable.music_note_black)).into(imageView);


        txtSongName.setText(getIntent().getStringExtra("songName"));
        txtSongArtist.setText(getIntent().getStringExtra("songArtist"));

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while(currentPosition<totalDuration){
                    try{
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekMusic.setProgress(currentPosition);
                    }catch (InterruptedException | IllegalStateException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        seekMusic.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        //noinspection deprecation
        seekMusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.bright_black), PorterDuff.Mode.MULTIPLY);
        seekMusic.getThumb().setColorFilter(getResources().getColor(R.color.bright_black), PorterDuff.Mode.SRC_IN);

        seekMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        txtSongStop.setText(endTime);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txtSongStart.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);

        btnPlay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    btnPlay.setBackgroundResource(R.drawable.ic_play_arrow);
                    mediaPlayer.pause();
                }
                else{
                    btnPlay.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        // next listener
        mediaPlayer.setOnCompletionListener((new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btnNext.performClick();
            }
        }));

        int audioSessionId = mediaPlayer.getAudioSessionId();
        if(audioSessionId != -1){
            visualizer.setAudioSessionId(audioSessionId);
        }

        btnNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%musicArrayList.size());
                Uri u = Uri.parse(musicArrayList.get(position).getUriStr());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                sName = musicArrayList.get(position).getName();
                sArtist = musicArrayList.get(position).getArtist();

                txtSongName.setText(sName);
                txtSongArtist.setText(sArtist);
                String endTime = createTime(mediaPlayer.getDuration());
                txtSongStop.setText(endTime);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause);
                startAnimation(imageView);
                int audioSessionId = mediaPlayer.getAudioSessionId();
                System.out.println(musicArrayList.get(position).getImagePath());
                if(audioSessionId != -1){
                    visualizer.setAudioSessionId(audioSessionId);
                }
                UiModeManager ui = (UiModeManager) getBaseContext().getSystemService(Context.UI_MODE_SERVICE);
                if(ui.getNightMode() == UiModeManager.MODE_NIGHT_YES)
                    Glide.with(getBaseContext()).asBitmap().load(musicArrayList.get(position).getImagePath()).apply(new RequestOptions().error(R.drawable.music_note_white)).into(imageView);
                else
                    Glide.with(getBaseContext()).asBitmap().load(musicArrayList.get(position).getImagePath()).apply(new RequestOptions().error(R.drawable.music_note_black)).into(imageView);


            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1<0)?(musicArrayList.size()-1):(position-1));

                Uri u = Uri.parse(musicArrayList.get(position).getUriStr());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sName = musicArrayList.get(position).getName();
                sArtist = musicArrayList.get(position).getArtist();
                txtSongName.setText(sName);
                txtSongArtist.setText(sArtist);
                String endTime = createTime(mediaPlayer.getDuration());
                txtSongStop.setText(endTime);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause);
                startAnimation(imageView);
                int audioSessionId = mediaPlayer.getAudioSessionId();
                if(audioSessionId != -1){
                    visualizer.setAudioSessionId(audioSessionId);
                }
                UiModeManager ui = (UiModeManager) getBaseContext().getSystemService(Context.UI_MODE_SERVICE);
                if(ui.getNightMode() == UiModeManager.MODE_NIGHT_YES)
                    Glide.with(getBaseContext()).asBitmap().load(musicArrayList.get(position).getImagePath()).apply(new RequestOptions().error(R.drawable.music_note_white)).into(imageView);
                else
                    Glide.with(getBaseContext()).asBitmap().load(musicArrayList.get(position).getImagePath()).apply(new RequestOptions().error(R.drawable.music_note_black)).into(imageView);
            }
        });

        btnFf.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+9000);
                }
            }
        });

        btnFr.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-11000);
                }
            }
        });
    }

    public void startAnimation(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f,360f);
        animator.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);

        animatorSet.start();
    }

    public String createTime(int duration){
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time+=min+":";
        if(sec<10){
            time+="0";
        }
        time+=sec;

        return time;
    }
}