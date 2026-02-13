package storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LocalFileStorage implements FileStorage {
    private final Path root;

    public LocalFileStorage(StorageConfig config) {
        Objects.requireNonNull(config, "config");
        this.root = config.rootPath().toAbsolutePath().normalize();
        try {
            if (Files.exists(root)) {
                if (!Files.isDirectory(root)) {
                    throw new StorageException("Storage root is not a directory: " + root);
                }
            } else if (config.createIfMissing()) {
                Files.createDirectories(root);
            } else {
                throw new StorageException("Storage root does not exist: " + root);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to initialize storage root: " + root, e);
        }
    }

    @Override
    public StorageObjectMetadata save(String relativePath, InputStream content) {
        Objects.requireNonNull(content, "content");
        Path target = ensurePathWithinRoot(relativePath);
        try {
            Path parent = target.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (OutputStream output = Files.newOutputStream(target,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                content.transferTo(output);
            }
            return toMetadata(target);
        } catch (IOException e) {
            throw new StorageException("Failed to save file: " + relativePath, e);
        }
    }

    @Override
    public Optional<StorageObject> read(String relativePath) {
        Path target = ensurePathWithinRoot(relativePath);
        if (!Files.exists(target) || Files.isDirectory(target)) {
            return Optional.empty();
        }
        try {
            byte[] data = Files.readAllBytes(target);
            return Optional.of(new StorageObject(toMetadata(target), data));
        } catch (IOException e) {
            throw new StorageException("Failed to read file: " + relativePath, e);
        }
    }

    @Override
    public boolean delete(String relativePath) {
        Path target = ensurePathWithinRoot(relativePath);
        try {
            return Files.deleteIfExists(target);
        } catch (IOException e) {
            throw new StorageException("Failed to delete file: " + relativePath, e);
        }
    }

    @Override
    public List<StorageObjectMetadata> list(String relativeDirectory) {
        Path directory = (relativeDirectory == null || relativeDirectory.isBlank())
                ? root
                : ensurePathWithinRoot(relativeDirectory);
        if (!Files.exists(directory)) {
            return List.of();
        }
        if (!Files.isDirectory(directory)) {
            throw new StorageException("Path is not a directory: " + relativeDirectory);
        }
        List<StorageObjectMetadata> results = new ArrayList<>();
        try (var stream = Files.list(directory)) {
            stream.filter(Files::isRegularFile)
                    .map(path -> {
                        try {
                            return toMetadata(path);
                        } catch (IOException e) {
                            throw new StorageException("Failed to read metadata for: " + path, e);
                        }
                    })
                    .forEach(results::add);
        } catch (IOException e) {
            throw new StorageException("Failed to list directory: " + relativeDirectory, e);
        }
        return results;
    }

    private Path ensurePathWithinRoot(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            throw new StorageException("relativePath cannot be blank");
        }
        Path resolved = root.resolve(relativePath).normalize();
        // Prevent path traversal by ensuring the resolved path is inside the root.
        if (!resolved.startsWith(root)) {
            throw new StorageException("Attempt to access outside storage root: " + relativePath);
        }
        return resolved;
    }

    private StorageObjectMetadata toMetadata(Path path) throws IOException {
        return new StorageObjectMetadata(
                root.relativize(path).toString().replace('\\', '/'),
                Files.size(path),
                Files.getLastModifiedTime(path).toInstant()
        );
    }
}

