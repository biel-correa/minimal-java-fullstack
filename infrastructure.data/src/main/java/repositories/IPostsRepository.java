package repositories;

import models.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPostsRepository {
        List<Post> getAll();
        Optional<Post> findById(UUID id);
        void store(Post post);
        void update(Post post);
        void delete(UUID id);
}
