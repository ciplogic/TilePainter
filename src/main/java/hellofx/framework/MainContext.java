package hellofx.framework;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author ciprian
 */
public class MainContext {

    private final Map<String, Object> typeRepo = new HashMap<>();
    Router router = new Router();
    Map<String, Object> objRepo = new HashMap<>();

    public MainContext() {
        setObj("context", this);
    }


    public void notify(String eventName, Object obj) {
        if (!EventNames.onFrame.equals(eventName)) {
            System.out.println("Calling: " + eventName + " with obj: " + obj);
        }

        router.call(eventName, obj);
    }

    public void listen(String eventName, Runnable action) {
        router.register(eventName, i -> {
            action.run();
        });
    }

    public <T> void listen(String eventName, Consumer<T> actionT) {
        router.register(eventName, i -> {
            T tValue = (T) i;
            actionT.accept(tValue);
        });
    }

    public void setObj(String objName, Object obj) {
        objRepo.put(objName, obj);
        setObjectType(obj);
    }

    private void setObjectType(Object obj) {
        String canonicalName = obj.getClass().getCanonicalName();
        if (typeRepo.containsKey(canonicalName))
            return;
        typeRepo.put(canonicalName, obj);
    }

    public <T> T getObj(String objName) {
        return (T) objRepo.get(objName);
    }

    boolean hasMethod(Object obj) {
        var methods = obj.getClass().getMethods();
        for (var m : methods) {
            if (m.getName().equals("setup"))
                return true;
        }
        return false;
    }

    public <T> T inject(T obj) {
        return injectInstance(obj);
    }

    public <T> T injectInstance(T obj) {
        var clazz = obj.getClass();
        var fields = clazz.getFields();
        for (var field : fields) {
            var fieldType = field.getType().getCanonicalName();
            if (this.typeRepo.containsKey(fieldType)) {
                try {
                    field.set(obj, this.typeRepo.get(fieldType));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.toString());
                }

            }
        }
        setObjectType(obj);
        if (hasMethod(obj)) {
            try {
                var method = clazz.getMethod("setup");
                method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e.toString());
            } catch (NoSuchMethodException e) {
                //DO nothing,
            }
        }
        return obj;
    }
}

