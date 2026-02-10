package injector;

import java.util.HashMap;
import java.util.Map;

public class DI {
    private static final DI instance = new DI();
    private final Map<String, Object> instances = new HashMap<>();

    private DI() {}

    public <T> void register(Class<T> clazz, T instance) {
        instances.put(clazz.getName(), instance);
    }

    public <T> T get(Class<T> clazz) {
        Object instance = instances.get(clazz.getName());
        if (instance == null) {
            throw new RuntimeException("No instance registered for class: " + clazz.getName());
        }

        return (T) instance;
    }

    public static DI getInstance() {
        return instance;
    }
}
