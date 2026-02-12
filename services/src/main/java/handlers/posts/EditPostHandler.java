package handlers.posts;

import handlers.Handler;
import injector.DI;
import io.javalin.http.Context;
import models.Post;
import org.jetbrains.annotations.NotNull;
import repositories.IPostsRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EditPostHandler extends Handler {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);

    @Override
    public void handle(@NotNull Context ctx) {
        var id = ctx.pathParam("id");

        var post = getPost(id);
        if (post.isEmpty()) {
            ctx.status(404).result("Post not found.");
            return;
        }

        ctx.render("posts/edit.jte", Map.of("post", post.get()));
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

