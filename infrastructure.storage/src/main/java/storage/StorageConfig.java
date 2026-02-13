package storage;

import java.nio.file.Path;
import java.util.Objects;

public record StorageConfig(Path rootPath, boolean createIfMissing) {
    public StorageConfig {
        Objects.requireNonNull(rootPath, "rootPath");
    }

    public static StorageConfig of(Path rootPath) {
        return new StorageConfig(rootPath, true);
    }
}

