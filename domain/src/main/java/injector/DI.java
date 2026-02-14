package injector;

import java.util.HashMap;
import java.util.Map;

public class DI {
    private static final DI instance = new DI();
    private final Map<String, Object> instances = new HashMap<>();

    private DI() {}

    public <T> void register(Class<T> clazz, T instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Cannot register null instance for class: " + clazz.getName());
        }

        if (!clazz.isInstance(instance)) {
            throw new IllegalStateException("Registered instance is not of expected type: " + clazz.getName());
        }

        instances.put(clazz.getName(), instance);
    }

    public <T> T get(Class<T> clazz) {
        Object instance = instances.get(clazz.getName());
        if (instance == null) {
            throw new IllegalStateException("No instance registered for class: " + clazz.getName());
        }

        return (T) instance;
    }

    public static DI getInstance() {
        return instance;
    }
}
