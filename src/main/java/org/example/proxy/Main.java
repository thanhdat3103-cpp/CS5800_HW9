package org.example.proxy;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 1. Create 8 sample songs
        List<Song> songs = List.of(
                new Song(1, "Morning Light", "Alex", "Sunrise", 210),
                new Song(2, "Blue Sky", "Alex", "Sunrise", 195),
                new Song(3, "Evening Rain", "Mia", "Twilight", 240),
                new Song(4, "Hello", "Mia", "Greetings", 200),
                new Song(5, "Hello", "Leo", "First Notes", 225),
                new Song(6, "City Lights", "Leo", "Night Drive", 180),
                new Song(7, "Ocean Waves", "Nora", "Seaside", 230),
                new Song(8, "Silent Road", "Nora", "Night Drive", 220)
        );

        // 2. Create the real server
        SongService realService = new RealSongService(songs);

        // 3. Create the Proxy
        SongService proxy = new SongServiceProxy(realService);

        long start;

        // 4. Search by ID - First request
        System.out.println("\n===== SEARCH BY ID =====");

        start = System.currentTimeMillis();
        System.out.println("Result: " + proxy.searchById(1));
        System.out.println("Time: "
                + (System.currentTimeMillis() - start) + " ms");

        // Search by ID - Second request
        start = System.currentTimeMillis();
        System.out.println("Result: " + proxy.searchById(1));
        System.out.println("Time: "
                + (System.currentTimeMillis() - start) + " ms");

        // 5. Search by Title
        System.out.println("\n===== SEARCH BY TITLE =====");

        start = System.currentTimeMillis();
        System.out.println("Results: " + proxy.searchByTitle("Hello"));
        System.out.println("Time: "
                + (System.currentTimeMillis() - start) + " ms");

        // Search again using lowercase
        start = System.currentTimeMillis();
        System.out.println("Results: " + proxy.searchByTitle("hello"));
        System.out.println("Time: "
                + (System.currentTimeMillis() - start) + " ms");

        // 6. Search by Album
        System.out.println("\n===== SEARCH BY ALBUM =====");

        start = System.currentTimeMillis();
        System.out.println("Results: " + proxy.searchByAlbum("Sunrise"));
        System.out.println("Time: "
                + (System.currentTimeMillis() - start) + " ms");

        // Search again using lowercase
        start = System.currentTimeMillis();
        System.out.println("Results: " + proxy.searchByAlbum("sunrise"));
        System.out.println("Time: "
                + (System.currentTimeMillis() - start) + " ms");

        // 7. Test searches with no matching songs
        System.out.println("\n===== NOT FOUND TEST =====");

        // Search by an ID that does not exist
        System.out.println("First ID search: "
                + proxy.searchById(999));

        System.out.println("Second ID search: "
                + proxy.searchById(999));

        // Search by a title that does not exist
        System.out.println("First title search: "
                + proxy.searchByTitle("Unknown Song"));

        System.out.println("Second title search: "
                + proxy.searchByTitle("Unknown Song"));

        // Search by an album that does not exist
        System.out.println("First album search: "
                + proxy.searchByAlbum("Unknown Album"));

        System.out.println("Second album search: "
                + proxy.searchByAlbum("Unknown Album"));
    }
}