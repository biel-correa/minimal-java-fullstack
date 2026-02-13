package handlers.posts;

import handlers.Handler;
import injector.DI;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import repositories.IPostsRepository;
import models.Post;
import storage.FileStorage;
import storage.StorageException;

import java.util.Optional;
import java.util.UUID;

public class DeletePostHandler extends Handler {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);
    private final FileStorage fileStorage = DI.getInstance().get(FileStorage.class);

    @Override
    public void handle(@NotNull Context ctx) {
        var id = ctx.pathParam("id");

        try {
            UUID postId = UUID.fromString(id);
            Optional<Post> post = this.postRepository.findById(postId);
            this.postRepository.delete(postId);
            post.map(Post::getThumbnailPath)
                .filter(path -> path != null && !path.isBlank())
                .ifPresent(this::deleteThumbnailQuietly);
            ctx.status(200).result("");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result("Invalid post ID.");
        } catch (Exception e) {
            ctx.status(500).result("Failed to delete post.");
        }
    }

    private void deleteThumbnailQuietly(String path) {
        try {
            fileStorage.delete(path);
        } catch (StorageException ignored) {
        }
    }
}
