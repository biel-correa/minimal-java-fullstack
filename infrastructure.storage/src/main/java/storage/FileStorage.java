package storage;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface FileStorage {
    StorageObjectMetadata save(String relativePath, InputStream content);

    Optional<StorageObject> read(String relativePath);

    boolean delete(String relativePath);

    List<StorageObjectMetadata> list(String relativeDirectory);
}

