package handlers.posts;

import handlers.Handler;
import injector.DI;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import repositories.IPostsRepository;

public class StorePostHandler extends Handler {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);

    @Override
    public void handle(@NotNull Context ctx) {
        var title = ctx.formParam("title");
        var content = ctx.formParam("content");

        if (title == null || content == null) {
            ctx.status(400).result("Title and content are required.");
            return;
        }

        var post = new models.Post(title, content);
        this.postRepository.store(post);
        ctx.redirect("/");
    }
}

