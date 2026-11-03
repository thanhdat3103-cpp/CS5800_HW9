package org.example.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SongServiceProxy implements SongService {

    private final SongService realService;

    // Cache for different types of searches
    private final Map<Integer, Song> idCache = new HashMap<>();

    private final Map<String, List<Song>> titleCache = new HashMap<>();

    private final Map<String, List<Song>> albumCache = new HashMap<>();

    // Constructor
    public SongServiceProxy(SongService realService) {
        this.realService = realService;
    }

    // Search by song ID
    @Override
    public Song searchById(Integer songID) {

        if (idCache.containsKey(songID)) {
            System.out.println("Cache HIT - Song ID: " + songID);
            return idCache.get(songID);
        }

        System.out.println("Cache MISS - Song ID: " + songID);

        Song song = realService.searchById(songID);

        idCache.put(songID, song);

        return song;
    }

    // Search by song title
    @Override
    public List<Song> searchByTitle(String title) {

        String key = title == null
                ? null : title.toLowerCase(Locale.ROOT);

        if (titleCache.containsKey(key)) {
            System.out.println("Cache HIT - Title: " + title);
            return new ArrayList<>(titleCache.get(key));
        }

        System.out.println("Cache MISS - Title: " + title);

        List<Song> results = realService.searchByTitle(title);

        titleCache.put(key, new ArrayList<>(results));

        return new ArrayList<>(results);
    }

    // Search by album
    @Override
    public List<Song> searchByAlbum(String album) {

        String key = album == null
                ? null : album.toLowerCase(Locale.ROOT);

        if (albumCache.containsKey(key)) {
            System.out.println("Cache HIT - Album: " + album);
            return new ArrayList<>(albumCache.get(key));
        }

        System.out.println("Cache MISS - Album: " + album);

        List<Song> results = realService.searchByAlbum(album);

        albumCache.put(key, new ArrayList<>(results));

        return new ArrayList<>(results);
    }
}