package repositories;

import dataAccess.IDatabaseConnector;
import injector.DI;
import models.Post;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostsRepository implements IPostsRepository {
    private final IDatabaseConnector db = DI.getInstance().get(IDatabaseConnector.class);

    @Override
    public List<Post> getAll() {
        try (ResultSet dbResult = db.select("SELECT * FROM posts")) {
            List<Post> posts = new ArrayList<>();

            while (dbResult.next()) {
                String id = dbResult.getString("id");
                String title = dbResult.getString("title");
                String content = dbResult.getString("content");
                posts.add(new Post(UUID.fromString(id), title, content));
            }

            return posts;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch posts from database", e);
        }
    }

    @Override
    public Optional<Post> findById(UUID id) {
        try (ResultSet dbResult = db.select("SELECT * FROM posts WHERE id = ?", id.toString())) {
            if (dbResult.next()) {
                String postId = dbResult.getString("id");
                String title = dbResult.getString("title");
                String content = dbResult.getString("content");
                return Optional.of(new Post(UUID.fromString(postId), title, content));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch post from database", e);
        }
    }

    @Override
    public void store(Post post) {
        try {
            db.execute("INSERT INTO posts (id, title, content) VALUES (?, ?, ?)", post.getId().toString(), post.getTitle(), post.getContent());
        } catch (Exception e) {
            throw new RuntimeException("Failed to store post in database", e);
        }
    }

    @Override
    public void update(Post post) {
        try {
            db.execute("UPDATE posts SET title = ?, content = ? WHERE id = ?", post.getTitle(), post.getContent(), post.getId().toString());
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
