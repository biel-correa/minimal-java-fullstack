package controllers;

import handlers.posts.*;
import io.javalin.Javalin;

public class PostsController {
    public PostsController(Javalin app) {
        createRoutes(app);
    }

    private void createRoutes(Javalin app) {
        app.get("/", new GetAllPostsHandler()::handle);
        app.get("/create", new CreatePostHandler()::handle);
        app.post("/create", new StorePostHandler()::handle);
        app.get("/{id}/edit", new EditPostHandler()::handle);
        app.post("/{id}/update", new UpdatePostHandler()::handle);
        app.delete("/{id}", new DeletePostHandler()::handle);
    }
}
