package storage;

import java.util.Objects;

public record StorageObject(StorageObjectMetadata metadata, byte[] content) {
    public StorageObject {
        Objects.requireNonNull(metadata, "metadata");
        Objects.requireNonNull(content, "content");
    }
}

