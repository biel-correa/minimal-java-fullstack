package handlers;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public abstract class Handler {
    public abstract void handle(@NotNull Context ctx);
}
