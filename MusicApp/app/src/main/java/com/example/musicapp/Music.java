package com.example.musicapp;

public class Music {
    private String title;
    private String artist;
    private int imagePath; // Đường dẫn đến hình ảnh của bài hát trong thư mục assets
    private int audioPath; // Đường dẫn đến file âm thanh của bài hát trong thư mục assets

    public Music(String title, String artist, int imagePath, int audioPath) {
        this.title = title;
        this.artist = artist;
        this.imagePath = imagePath;
        this.audioPath = audioPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public int getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(int audioPath) {
        this.audioPath = audioPath;
    }
}
