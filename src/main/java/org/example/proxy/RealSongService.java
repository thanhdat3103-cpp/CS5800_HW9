package org.example.proxy;

import java.util.ArrayList;
import java.util.List;

public class RealSongService implements SongService {

    private final List<Song> songs;

    // Constructor
    public RealSongService(List<Song> songs) {
        this.songs = new ArrayList<>(songs);
    }

    // Search for a song by its ID
    @Override
    public Song searchById(Integer songID) {

        // Simulate a 1-second server delay
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}

        for (Song song : songs) {
            if (song.getSongID().equals(songID)) {
                return song;
            }
        }

        return null;
    }

    // Search for songs by title
    @Override
    public List<Song> searchByTitle(String title) {

        // Simulate a 1-second server delay
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}

        List<Song> results = new ArrayList<>();

        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                results.add(song);
            }
        }

        return results;
    }

    // Search for songs by album
    @Override
    public List<Song> searchByAlbum(String album) {

        // Simulate a 1-second server delay
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}

        List<Song> results = new ArrayList<>();

        for (Song song : songs) {
            if (song.getAlbum().equalsIgnoreCase(album)) {
                results.add(song);
            }
        }

        return results;
    }
}