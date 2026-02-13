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
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
