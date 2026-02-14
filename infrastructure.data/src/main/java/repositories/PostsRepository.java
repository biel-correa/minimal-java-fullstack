package repositories;

import dataAccess.DatabaseConnector;
import injector.DI;
import models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostsRepository implements IPostsRepository {
    private final DatabaseConnector db = DI.getInstance().get(DatabaseConnector.class);

    @Override
    public List<Post> getAll() {
        String query = "SELECT * FROM posts";
        try (var resultSet = db.select(query)) {
            List<Post> posts = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String thumbnailPath = resultSet.getString("thumbnail_path");
                posts.add(new Post(UUID.fromString(id), title, content, thumbnailPath));
            }

            return posts;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch posts from database", e);
        }
    }

    @Override
    public Optional<Post> findById(UUID id) {
        String query = "SELECT * FROM posts WHERE id = ?";
        try (var resultSet = db.select(query, id.toString())) {
            if (resultSet.next()) {
                String postId = resultSet.getString("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String thumbnailPath = resultSet.getString("thumbnail_path");
                return Optional.of(new Post(UUID.fromString(postId), title, content, thumbnailPath));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch post from database", e);
        }
    }

    @Override
    public void store(Post post) {
        try {
            db.execute("INSERT INTO posts (id, title, content, thumbnail_path) VALUES (?, ?, ?, ?)",
                    post.getId().toString(), post.getTitle(), post.getContent(), post.getThumbnailPath());
        } catch (Exception e) {
            throw new RuntimeException("Failed to store post in database", e);
        }
    }

    @Override
    public void update(Post post) {
        try {
            db.execute("UPDATE posts SET title = ?, content = ?, thumbnail_path = ? WHERE id = ?",
                    post.getTitle(), post.getContent(), post.getThumbnailPath(), post.getId().toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update post in database", e);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            db.execute("DELETE FROM posts WHERE id = ?", id.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete post from database", e);
        }
    }
}
