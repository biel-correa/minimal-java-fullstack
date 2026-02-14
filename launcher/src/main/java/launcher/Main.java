package launcher;

import controllers.PostsController;
import dataAccess.DatabaseConnector;
import dataAccess.SqliteDatabase;
import environment.Environment;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import injector.DI;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import repositories.IPostsRepository;
import repositories.PostsRepository;
import storage.FileStorage;
import storage.LocalFileStorage;
import storage.StorageConfig;

import java.nio.file.Path;

public class Main {
    static void main() {
        new Main().start();
    }

    private void start() {
        loadDotEnv();
        Path storageRoot = resolveStorageRoot();

        var app = Javalin.create(config -> {
            var codeResolver = new DirectoryCodeResolver(Path.of(Environment.getInstance().get("TEMPLATE_PATH")));
            var templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
            config.fileRenderer(new JavalinJte(templateEngine));
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = storageRoot.toString();
                staticFiles.hostedPath = "/uploads";
                staticFiles.location = Location.EXTERNAL;
            });
        }).start(7070);

        registerDependencies(app, storageRoot);
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
        final DatabaseConnector db = DI.getInstance().get(DatabaseConnector.class);
        db.migrate();
    }

    private void registerDependencies(Javalin app, Path storageRoot) {
        final DI injector = DI.getInstance();

        injector.register(DatabaseConnector.class, new SqliteDatabase());
        injector.register(FileStorage.class, new LocalFileStorage(StorageConfig.of(storageRoot)));
        injector.register(IPostsRepository.class, new PostsRepository());
        injector.register(PostsController.class, new PostsController(app));
    }

    private Path resolveStorageRoot() {
        Environment env = Environment.getInstance();
        String root = env.get("STORAGE_ROOT");
        if (root == null || root.isBlank()) {
            root = "storage/uploads";
            env.set("STORAGE_ROOT", root);
        }
        return Path.of(root);
    }
}
