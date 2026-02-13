package storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocalFileStorageTest {
    @TempDir
    Path tempDir;

    @Test
    void saveAndReadRoundTrip() {
        LocalFileStorage storage = new LocalFileStorage(StorageConfig.of(tempDir));
        byte[] payload = "hello world".getBytes(StandardCharsets.UTF_8);
        StorageObjectMetadata metadata = storage.save("posts/1.txt", toStream(payload));

        assertEquals("posts/1.txt", metadata.relativePath());
        assertEquals(payload.length, metadata.size());

        StorageObject stored = storage.read("posts/1.txt").orElseThrow();
        assertArrayEquals(payload, stored.content());
    }

    @Test
    void deleteRemovesFile() {
        LocalFileStorage storage = new LocalFileStorage(StorageConfig.of(tempDir));
        storage.save("posts/remove.me", toStream("bye".getBytes(StandardCharsets.UTF_8)));

        assertTrue(storage.delete("posts/remove.me"));
        assertFalse(storage.read("posts/remove.me").isPresent());
        assertFalse(storage.delete("posts/remove.me"));
    }

    @Test
    void listReturnsMetadata() {
        LocalFileStorage storage = new LocalFileStorage(StorageConfig.of(tempDir));
        storage.save("a.txt", toStream("a".getBytes(StandardCharsets.UTF_8)));
        storage.save("sub/b.txt", toStream("bb".getBytes(StandardCharsets.UTF_8)));

        List<StorageObjectMetadata> rootList = storage.list("");
        assertEquals(1, rootList.size());
        assertEquals("a.txt", rootList.get(0).relativePath());

        List<StorageObjectMetadata> subList = storage.list("sub");
        assertEquals(1, subList.size());
        StorageObjectMetadata meta = subList.get(0);
        assertEquals("sub/b.txt", meta.relativePath());
        assertEquals(2, meta.size());
        assertTrue(meta.lastModified().isBefore(Instant.now().plusSeconds(1)));
    }

    @Test
    void preventsPathTraversal() {
        LocalFileStorage storage = new LocalFileStorage(StorageConfig.of(tempDir));
        assertThrows(StorageException.class,
                () -> storage.save("../escape.txt", toStream("x".getBytes(StandardCharsets.UTF_8))));
    }

    @Test
    void failsWhenRootMissingAndCreationDisabled() {
        Path newDir = tempDir.resolve("missing");
        assertThrows(StorageException.class,
                () -> new LocalFileStorage(new StorageConfig(newDir, false)));
    }

    private InputStream toStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
}

