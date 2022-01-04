package com.example.musicplayer;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Music> music;

    public MusicAdapter(Context context, ArrayList<Music> music) {
        this.context = context;
        this.music = music;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {
        int p = position;
        final Music temp = music.get(position);
        ImageView music_icon;
        TextView song_name, song_artist;

        music_icon = holder.musicThumb;
        song_name = holder.musicName;
        song_artist = holder.musicArtist;

        UiModeManager ui = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);

        song_name.setText(music.get(position).getName());
        song_artist.setText(music.get(position).getArtist());
        Uri uri = Uri.parse(music.get(position).getUriStr());
        String albumId = music.get(position).getAlbumId();
        String imagePath = music.get(position).getImagePath();
        Uri imagePathUri = Uri.parse(imagePath);

        Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (imagePathUri != null) {
                            Glide.with(context).asBitmap().load(imagePathUri).placeholder(R.drawable.music_note_black).into(holder.musicThumb);
                        }
                        else{
                            if(ui.getNightMode() == UiModeManager.MODE_NIGHT_YES)
                                Glide.with(context).load(R.drawable.music_note_white).into(music_icon);
                            else
                                Glide.with(context).load(R.drawable.music_note_black).into(music_icon);

                        }
                    }
                });
            }
        }).start();


        holder.button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("songList", music);
                intent.putExtra("songs", temp.getUriStr());
                intent.putExtra("songImage", temp.getImagePath());
                intent.putExtra("songName", temp.getName());
                intent.putExtra("songArtist", temp.getArtist());
                intent.putExtra("position", p);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return music.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView musicName, musicArtist;
        private ImageView musicThumb, button;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            button = itemView.findViewById(R.id.itemBtn);
            musicName = itemView.findViewById(R.id.songName);
            musicArtist = itemView.findViewById(R.id.songArtist);
            musicThumb = itemView.findViewById(R.id.songImage);

        }
    }
}
