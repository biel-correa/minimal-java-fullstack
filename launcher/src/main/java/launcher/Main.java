package launcher;

import controllers.PostsController;
import dataAccess.IDatabaseConnector;
import dataAccess.InMemoryDatabase;
import environment.Environment;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import injector.DI;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import repositories.IPostsRepository;
import repositories.PostsRepository;

import java.nio.file.Path;

public class Main {
    void main() {
        loadDotEnv();

        var app = Javalin.create(config -> {
            var codeResolver = new DirectoryCodeResolver(Path.of(Environment.getInstance().get("TEMPLATE_PATH")));
            var templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
            config.fileRenderer(new JavalinJte(templateEngine));
        }).start(7070);

        registerDependencies(app);
        runMigrations();
    }

    private void loadDotEnv() {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .load();

        Environment env = Environment.getInstance();
        dotenv.entries().forEach(entry -> env.set(entry.getKey(), entry.getValue()));
    }

    private void runMigrations() {
        final IDatabaseConnector db = DI.getInstance().get(IDatabaseConnector.class);
        db.migrate();
    }

    private void registerDependencies(Javalin app) {
        final DI injector = DI.getInstance();

        injector.register(IDatabaseConnector.class, new InMemoryDatabase());

        injector.register(IPostsRepository.class, new PostsRepository());

        injector.register(PostsController.class, new PostsController(app));
    }
}
