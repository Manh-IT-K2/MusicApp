package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MusicPage extends AppCompatActivity {

    TextView title, timeRun, timeTotal;
    SeekBar seekbar;
    ImageButton pre, stop, next;
    int position = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra("position", 0);
        }
        init();
        setEvent();
        initMedia();


    }

    private void initMedia(){
        mediaPlayer = MediaPlayer.create(MusicPage.this, MainActivity.musicList.get(position).getAudioPath());
        title.setText(MainActivity.musicList.get(position).getTitle());
    }

    private void setEvent(){
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    stop.setImageResource(R.drawable.baseline_play_arrow_24);
                }else{
                    mediaPlayer.start();
                    stop.setImageResource(R.drawable.baseline_pause_24);
                }
                setTimeTotal();
                updateTimeSong();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position > MainActivity.musicList.size() - 1) position = 0;

                if(mediaPlayer.isPlaying()) mediaPlayer.stop();

                initMedia();
                mediaPlayer.start();
                stop.setImageResource(R.drawable.baseline_pause_24);
                setTimeTotal();
                updateTimeSong();
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position < 0) position = MainActivity.musicList.size() - 1;

                if(mediaPlayer.isPlaying()) mediaPlayer.stop();

                initMedia();
                mediaPlayer.start();
                stop.setImageResource(R.drawable.baseline_pause_24);
                setTimeTotal();
                updateTimeSong();
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
    }

    private void updateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                timeRun.setText(format.format(mediaPlayer.getCurrentPosition()));

                seekbar.setProgress(mediaPlayer.getCurrentPosition());

                //Kiểm tra nếu hết bài hát thì tự next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        position++;
                        if(position > MainActivity.musicList.size() - 1) position = 0;

                        if(mediaPlayer.isPlaying()) mediaPlayer.stop();

                        initMedia();
                        mediaPlayer.start();
                        stop.setImageResource(R.drawable.baseline_pause_24);
                        setTimeTotal();
                        updateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void setTimeTotal(){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        timeTotal.setText(format.format(mediaPlayer.getDuration()));

        seekbar.setMax(mediaPlayer.getDuration());
    }

    private void init(){
        title = findViewById(R.id.title);
        timeRun = findViewById(R.id.timeRun);
        timeTotal = findViewById(R.id.timeTotal);

        seekbar = findViewById(R.id.seekbar);

        pre = findViewById(R.id.pre);
        stop = findViewById(R.id.stop);
        next = findViewById(R.id.next);
    }
}