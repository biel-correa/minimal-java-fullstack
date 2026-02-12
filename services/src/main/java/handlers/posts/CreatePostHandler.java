package handlers.posts;

import handlers.Handler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class CreatePostHandler extends Handler {
    @Override
    public void handle(@NotNull Context ctx) {
        ctx.render("posts/create.jte");
    }
}

