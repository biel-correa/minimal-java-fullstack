package handlers.posts;

import handlers.Handler;
import injector.DI;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import models.Post;
import org.jetbrains.annotations.NotNull;
import repositories.IPostsRepository;
import storage.FileStorage;
import storage.StorageException;

import java.util.UUID;

public class StorePostHandler extends Handler {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);
    private final FileStorage fileStorage = DI.getInstance().get(FileStorage.class);

    @Override
    public void handle(@NotNull Context ctx) {
        var title = ctx.formParam("title");
        var content = ctx.formParam("content");

        if (title == null || content == null) {
            ctx.status(400).result("Title and content are required.");
            return;
        }

        var post = new Post(title, content);
        var uploadedThumbnail = ctx.uploadedFile("thumbnail");
        try {
            var thumbnailPath = persistThumbnailIfPresent(post.getId(), uploadedThumbnail);
            post.setThumbnailPath(thumbnailPath);
            this.postRepository.store(post);
            ctx.redirect("/");
        } catch (StorageException e) {
            ctx.status(500).result("Failed to store thumbnail");
        }
    }

    private String persistThumbnailIfPresent(UUID postId, UploadedFile thumbnail) {
        if (thumbnail == null || thumbnail.size() <= 0) {
            return null;
        }
        var extension = getExtension(thumbnail.filename());
        var targetPath = "thumbnails/" + postId + extension;
        try (var content = thumbnail.content()) {
            fileStorage.save(targetPath, content);
            return targetPath;
        } catch (Exception e) {
            throw new StorageException("Failed to save thumbnail", e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        var suffix = filename.substring(filename.lastIndexOf('.'));
        return suffix.toLowerCase();
    }
}
