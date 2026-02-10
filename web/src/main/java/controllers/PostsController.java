package controllers;

import io.javalin.Javalin;
import services.PostsService;

public class PostsController {
    private final PostsService postsService;

    public PostsController(Javalin app) {
        this.postsService = new PostsService();
        createRoutes(app);
    }

    private void createRoutes(Javalin app) {
        app.get("/", this.postsService::getAll);
        app.get("/create", this.postsService::create);
        app.post("/create", this.postsService::store);
        app.get("/{id}/edit", this.postsService::edit);
        app.post("/{id}/update", this.postsService::update);
        app.delete("/{id}", this.postsService::delete);
    }
}
