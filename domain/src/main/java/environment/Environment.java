package environment;

import java.util.*;

public class Environment {
    private static Environment instance;
    private final List<String> validKeys = List.of("TEMPLATE_PATH", "MIGRATIONS_PATH", "STORAGE_ROOT");
    private final Map<String, String> env = new HashMap<>();

    private Environment() {
    }

    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment();
        }
        return instance;
    }

    public void set(String key, String value) {
        if (!validKeys.contains(key)) {
            return;
        }

        env.put(key, value);
    }

    public String get(String key) {
        if (!validKeys.contains(key)) {
            throw new IllegalArgumentException("Invalid environment variable key: " + key);
        }

        return env.get(key);
    }
}
