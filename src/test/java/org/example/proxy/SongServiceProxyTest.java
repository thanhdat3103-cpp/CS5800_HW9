package org.example.proxy;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SongServiceProxyTest {

    // Fake server used for testing
    private static class CountingSongService implements SongService {

        // Track the number of real service calls
        int idCallCount = 0;
        int titleCallCount = 0;
        int albumCallCount = 0;

        // Sample song
        private final Song song =
                new Song(1, "Morning Light", "Alex", "Sunrise", 210);

        // Search by ID
        @Override
        public Song searchById(Integer songID) {

            idCallCount++;

            if (Integer.valueOf(1).equals(songID)) {
                return song;
            }

            return null;
        }

        // Search by Title
        @Override
        public List<Song> searchByTitle(String title) {

            titleCallCount++;

            if ("Morning Light".equalsIgnoreCase(title)) {
                return List.of(song);
            }

            return List.of();
        }

        // Search by Album
        @Override
        public List<Song> searchByAlbum(String album) {

            albumCallCount++;

            if ("Sunrise".equalsIgnoreCase(album)) {
                return List.of(song);
            }

            return List.of();
        }
    }

    // TEST 1: Verify caching for searchById()
    @Test
    void testSearchByIdUsesCache() {

        // Arrange
        CountingSongService fakeServer = new CountingSongService();
        SongService proxy = new SongServiceProxy(fakeServer);

        // Act
        Song firstResult = proxy.searchById(1);
        Song secondResult = proxy.searchById(1);

        // Assert
        assertNotNull(firstResult);
        assertSame(firstResult, secondResult);
        assertEquals(1, fakeServer.idCallCount);
    }

    // TEST 2: Verify caching for searchByTitle()
    @Test
    void testSearchByTitleUsesCache() {

        // Arrange
        CountingSongService fakeServer = new CountingSongService();
        SongService proxy = new SongServiceProxy(fakeServer);

        // Act
        List<Song> firstResult =
                proxy.searchByTitle("Morning Light");

        List<Song> secondResult =
                proxy.searchByTitle("morning light");

        // Assert
        assertEquals(1, firstResult.size());
        assertEquals(1, secondResult.size());

        assertEquals("Morning Light",
                firstResult.get(0).getTitle());

        assertEquals(firstResult, secondResult);
        assertEquals(1, fakeServer.titleCallCount);
    }

    // TEST 3: Verify caching for searchByAlbum()
    @Test
    void testSearchByAlbumUsesCache() {

        // Arrange
        CountingSongService fakeServer = new CountingSongService();
        SongService proxy = new SongServiceProxy(fakeServer);

        // Act
        List<Song> firstResult =
                proxy.searchByAlbum("Sunrise");

        List<Song> secondResult =
                proxy.searchByAlbum("sunrise");

        // Assert
        assertEquals(1, firstResult.size());
        assertEquals(1, secondResult.size());

        assertEquals("Sunrise",
                firstResult.get(0).getAlbum());

        assertEquals(firstResult, secondResult);
        assertEquals(1, fakeServer.albumCallCount);
    }

    // TEST 4: Verify negative caching for a missing song ID
    @Test
    void testMissingSongIdIsCached() {

        // Arrange
        CountingSongService fakeServer = new CountingSongService();
        SongService proxy = new SongServiceProxy(fakeServer);

        // Act
        Song firstResult = proxy.searchById(999);
        Song secondResult = proxy.searchById(999);

        // Assert: Both searches return null
        assertNull(firstResult);
        assertNull(secondResult);

        // Server must only be called once
        assertEquals(1, fakeServer.idCallCount);
    }

    // TEST 5: Verify negative caching for a missing title
    @Test
    void testMissingTitleIsCached() {

        // Arrange
        CountingSongService fakeServer = new CountingSongService();
        SongService proxy = new SongServiceProxy(fakeServer);

        // Act
        List<Song> firstResult =
                proxy.searchByTitle("Unknown Song");

        List<Song> secondResult =
                proxy.searchByTitle("unknown song");

        // Assert: Both searches return empty lists
        assertTrue(firstResult.isEmpty());
        assertTrue(secondResult.isEmpty());

        // Server must only be called once
        assertEquals(1, fakeServer.titleCallCount);
    }

    // TEST 6: Verify negative caching for a missing album
    @Test
    void testMissingAlbumIsCached() {

        // Arrange
        CountingSongService fakeServer = new CountingSongService();
        SongService proxy = new SongServiceProxy(fakeServer);

        // Act
        List<Song> firstResult =
                proxy.searchByAlbum("Unknown Album");

        List<Song> secondResult =
                proxy.searchByAlbum("unknown album");

        // Assert: Both searches return empty lists
        assertTrue(firstResult.isEmpty());
        assertTrue(secondResult.isEmpty());

        // Server must only be called once
        assertEquals(1, fakeServer.albumCallCount);
    }
}