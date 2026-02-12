package handlers.posts;

import handlers.Handler;
import injector.DI;
import io.javalin.http.Context;
import repositories.IPostsRepository;

import java.util.Map;

public class GetAllPostsHandler extends Handler {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);

    @Override
    public void handle(Context ctx) {
        var result = this.postRepository.getAll();
        ctx.render("index.jte", Map.of("posts", result));
    }
}
