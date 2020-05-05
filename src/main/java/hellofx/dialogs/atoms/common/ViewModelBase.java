package hellofx.dialogs.atoms.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ViewModelBase {
    private final List<Consumer<String>> eventList = new ArrayList<>();

    public void notify(String propertyName) {
        for (var event : eventList) {
            event.accept(propertyName);
        }
    }

    public void listen(Consumer<String> onEvent) {
        eventList.add(onEvent);
    }
}
