package services;

import injector.DI;
import org.jetbrains.annotations.NotNull;
import repositories.IPostsRepository;

import java.util.Map;
import java.util.UUID;

public class PostsService {
    private final IPostsRepository postRepository = DI.getInstance().get(IPostsRepository.class);

    public void getAll(@NotNull io.javalin.http.Context context) {
        var result = this.postRepository.getAll();
        context.render("index.jte", Map.of("posts", result));
    }

    public void create(@NotNull io.javalin.http.Context context) {
        context.render("posts/create.jte");
    }

    public void store(@NotNull io.javalin.http.Context context) {
        var title = context.formParam("title");
        var content = context.formParam("content");

        if (title == null || content == null) {
            context.status(400).result("Title and content are required.");
            return;
        }

        var post = new models.Post(title, content);
        this.postRepository.store(post);
        context.redirect("/");
    }

    public void edit(@NotNull io.javalin.http.Context context) {
        var id = context.pathParam("id");

        try {
            UUID postId = UUID.fromString(id);
            var postOptional = this.postRepository.findById(postId);

            if (postOptional.isEmpty()) {
                context.status(404).result("Post not found.");
                return;
            }

            context.render("posts/edit.jte", Map.of("post", postOptional.get()));
        } catch (IllegalArgumentException e) {
            context.status(400).result("Invalid post ID.");
        }
    }

    public void update(@NotNull io.javalin.http.Context context) {
        var id = context.pathParam("id");
        var title = context.formParam("title");
        var content = context.formParam("content");

        if (title == null || content == null) {
            context.status(400).result("Title and content are required.");
            return;
        }

        try {
            UUID postId = UUID.fromString(id);
            var postOptional = this.postRepository.findById(postId);

            if (postOptional.isEmpty()) {
                context.status(404).result("Post not found.");
                return;
            }

            var post = postOptional.get();
            post.setTitle(title);
            post.setContent(content);
            this.postRepository.update(post);
            context.redirect("/");
        } catch (IllegalArgumentException e) {
            context.status(400).result("Invalid post ID.");
        } catch (Exception e) {
            context.status(500).result("Failed to update post.");
        }
    }

    public void delete(@NotNull io.javalin.http.Context context) {
        var id = context.pathParam("id");

        try {
            UUID postId = UUID.fromString(id);
            this.postRepository.delete(postId);
            context.status(200).result("");
        } catch (IllegalArgumentException e) {
            context.status(400).result("Invalid post ID.");
        } catch (Exception e) {
            context.status(500).result("Failed to delete post.");
        }
    }
}
