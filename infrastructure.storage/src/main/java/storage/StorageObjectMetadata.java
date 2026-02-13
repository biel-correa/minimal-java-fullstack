package storage;

import java.time.Instant;
import java.util.Objects;

public record StorageObjectMetadata(String relativePath, long size, Instant lastModified) {
    public StorageObjectMetadata {
        Objects.requireNonNull(relativePath, "relativePath");
        Objects.requireNonNull(lastModified, "lastModified");
    }
}

