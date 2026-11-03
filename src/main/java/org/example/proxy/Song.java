package org.example.proxy;

public class Song {

    private final Integer songID;
    private final String title;
    private final String artist;
    private final String album;
    private final int duration;

    // Constructor
    public Song(Integer songID, String title,
                String artist, String album, int duration) {
        this.songID = songID;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    // Getter methods
    public Integer getSongID() {
        return songID;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getDuration() {
        return duration;
    }

    // Display song information
    @Override
    public String toString() {
        return "Song ID: " + songID
                + ", Title: " + title
                + ", Artist: " + artist
                + ", Album: " + album
                + ", Duration: " + duration + " seconds";
    }
}