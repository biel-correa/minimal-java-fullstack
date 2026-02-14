package models;

import java.util.UUID;

public class Post {
    private final UUID id;
    private String title;
    private String content;
    private String thumbnailPath;

    public Post(String title, String content) {
        this(UUID.randomUUID(), title, content, null);
    }

    public Post(UUID id, String title, String content) {
        this(id, title, content, null);
    }

    public Post(UUID id, String title, String content, String thumbnailPath) {
        if (id == null) {
            throw new IllegalArgumentException("Post id cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Post title cannot be null or blank");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Post content cannot be null or blank");
        }
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnailPath = thumbnailPath;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Post title cannot be null or blank");
        }
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Post content cannot be null or blank");
        }
        this.content = content;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
