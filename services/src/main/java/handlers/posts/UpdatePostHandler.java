package handlers.posts;

import handlers.Handler;
import injector.DI;
import io.javalin.http.Context;
import models.Post;
import org.jetbrains.annotations.NotNull;
import repositories.IPostsRepository;

import java.util.Optional;
import java.util.UUID;

public class UpdatePostHandler extends Handler {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);

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

        try {
            this.postRepository.update(post);
            ctx.redirect("/");
        } catch (Exception e) {
            ctx.status(500).result("Failed to update post.");
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

