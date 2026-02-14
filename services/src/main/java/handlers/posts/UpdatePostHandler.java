package handlers.posts;

import handlers.Handler;
import handlers.utils.FileUtils;
import injector.DI;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import models.Post;
import org.jetbrains.annotations.NotNull;
import repositories.IPostsRepository;
import storage.FileStorage;
import storage.StorageException;

import java.util.Optional;
import java.util.UUID;

public class UpdatePostHandler extends Handler {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);
    private final FileStorage fileStorage = DI.getInstance().get(FileStorage.class);

    @Override
    public void handle(@NotNull Context ctx) {
        var id = ctx.pathParam("id");
        var title = ctx.formParam("title");
        var content = ctx.formParam("content");

        if (title == null || content == null) {
            ctx.status(400).result("Title and content are required.");
            return;
        }

        var postOptional = getPost(id);
        if (postOptional.isEmpty()) {
            ctx.status(404).result("Post not found.");
            return;
        }

        var post = postOptional.get();
        post.setTitle(title);
        post.setContent(content);

        var thumbnail = ctx.uploadedFile("thumbnail");
        try {
            updateThumbnailIfPresent(post, thumbnail);
            this.postRepository.update(post);
            ctx.redirect("/");
        } catch (StorageException e) {
            ctx.status(500).result("Failed to update thumbnail");
        }
    }

    private void updateThumbnailIfPresent(Post post, UploadedFile thumbnail) {
        if (thumbnail == null || thumbnail.size() <= 0) {
            return;
        }
        var extension = FileUtils.getFileExtension(thumbnail.filename());
        var targetPath = "thumbnails/" + post.getId() + extension;
        var previousPath = post.getThumbnailPath();
        try (var content = thumbnail.content()) {
            fileStorage.save(targetPath, content);
            if (previousPath != null && !previousPath.equals(targetPath)) {
                fileStorage.delete(previousPath);
            }
            post.setThumbnailPath(targetPath);
        } catch (Exception e) {
            throw new StorageException("Failed to update thumbnail", e);
        }
    }


    private Optional<Post> getPost(String id) {
        try {
            UUID postId = UUID.fromString(id);
            return this.postRepository.findById(postId);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
