# infrastructure.storage

This module provides file storage abstractions plus a `LocalFileStorage` implementation backed by the local filesystem.

## Usage

```java
var storage = new LocalFileStorage(StorageConfig.of(Path.of("./storage")));
storage.save("assets/logo.png", inputStream);
Optional<StorageObject> object = storage.read("assets/logo.png");
```

## Tests

```bash
mvn -pl infrastructure.storage test
```

