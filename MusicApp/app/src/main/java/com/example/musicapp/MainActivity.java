package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

// Trong Activity hoặc Fragment của bạn
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    public static List<Music> musicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo danh sách nhạc
        musicList.add(new Music("Simple Love", "Artist 1", R.drawable.bird_1, R.raw.simplelove));
        musicList.add(new Music("Có Hẹn Với Thanh Xuân", "Artist 2", R.drawable.bird_2,R.raw.cohenvoithanhxuan));
        musicList.add(new Music("Đừng Xem Ai Đó Là Cả Thế Giới", "Artist 3", R.drawable.bird_3,R.raw.dungxemaidolacathegioi));
        musicList.add(new Music("Em Thích", "Artist 4", R.drawable.bird_4,R.raw.emthich));
        musicList.add(new Music("Hình Như Ta Thích Nhau", "Artist 5", R.drawable.bird_5,R.raw.hinhnhutathichnhau));
        musicList.add(new Music("Nước Mắt Em Lau Bằng Tình Yêu Mới", "Artist 6", R.drawable.bird_6,R.raw.nuocmatemlaubangtinhyeumoi));
        musicList.add(new Music("Về Bên Anh", "Artist 7", R.drawable.bird_7,R.raw.vebenanh));
//        // Thêm các bài hát khác vào danh sách

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.rcvMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Adapter và truyền danh sách nhạc vào
        musicAdapter = new MusicAdapter(this, musicList);
        recyclerView.setAdapter(musicAdapter);
    }
}
