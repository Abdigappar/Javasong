package models;

public abstract class MediaItem implements Playable {
    protected String title;
    protected String artist;

    public MediaItem(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }

    public abstract void displayInfo();
}
