package handlers.posts;

import handlers.Handler;
import injector.DI;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import repositories.IPostsRepository;

import java.util.UUID;

public class DeletePostHandler extends Handler {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);

    @Override
    public void handle(@NotNull Context ctx) {
        var id = ctx.pathParam("id");

        try {
            UUID postId = UUID.fromString(id);
            this.postRepository.delete(postId);
            ctx.status(200).result("");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result("Invalid post ID.");
        } catch (Exception e) {
            ctx.status(500).result("Failed to delete post.");
        }
    }
}

