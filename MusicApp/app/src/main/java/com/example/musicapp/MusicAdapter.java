package com.example.musicapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.util.List;
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private Context context;
    private List<Music> musicList;
    private MediaPlayer mediaPlayer;
    private int currentPlayingPosition = -1; // Vị trí của bài hát đang phát, -1 nghĩa là không có bài hát nào đang phát

    public MusicAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
        this.mediaPlayer = new MediaPlayer();
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.bind(music);
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView;
        private TextView artistTextView;
        private ImageButton playButton;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.textView3);
            artistTextView = itemView.findViewById(R.id.textView4);
            playButton = itemView.findViewById(R.id.imageButton);
        }

        public void bind(Music music) {
            titleTextView.setText(music.getTitle());
            artistTextView.setText(music.getArtist());

            // Load image from assets folder
            try {
                InputStream inputStream = context.getResources().openRawResource(music.getImagePath());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Set click listener for play button
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentPlayingPosition != getAdapterPosition()) {
                        // Tạm dừng bài hát hiện tại nếu có
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            // Đổi biểu tượng của nút play
                            if (currentPlayingPosition != -1) {
                                notifyItemChanged(currentPlayingPosition);
                            }
                        }
                        // Bắt đầu phát bài hát mới
                        currentPlayingPosition = getAdapterPosition();
                        playMusic(music.getAudioPath());
                        // Đổi biểu tượng của nút play
                        playButton.setImageResource(android.R.drawable.ic_media_pause);
                    } else {
                        // Nếu đang phát bài hát này, kiểm tra trạng thái của MediaPlayer
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            // Dừng phát nhạc
                            mediaPlayer.pause();
                            // Đổi biểu tượng của ImageButton thành biểu tượng phát nhạc
                            playButton.setImageResource(android.R.drawable.ic_media_play);
                        } else {
                            // Tiếp tục phát nhạc
                            mediaPlayer.start();
                            // Đổi biểu tượng của ImageButton thành biểu tượng tạm dừng
                            playButton.setImageResource(android.R.drawable.ic_media_pause);
                        }
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();// Lấy vị trí khi nhấn
                    if (position != RecyclerView.NO_POSITION) { //Vị trí hợp lệ
                        Intent intent = new Intent(context, MusicPage.class);
                        intent.putExtra("position", position);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    private void playMusic(int audioResId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, audioResId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
