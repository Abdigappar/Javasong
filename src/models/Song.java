package models;

public class Song {
    private String title;
    private String artist;
    private String album;
    private int duration;
    private String filePath;

    public Song(String title, String artist, String album, int duration) {
        this(title, artist, album, duration, "songs/" + title.toLowerCase().replace(" ", "_") + ".mp3");
    }

    public Song(String title, String artist, String album, int duration, String filePath) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.filePath = filePath;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDuration() { return duration; }
    public String getFilePath() { return filePath; }
}
