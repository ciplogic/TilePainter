/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofx.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author ciprian
 */
class Router {
    Map<String, ArrayList<Consumer<Object>>> eventRepo = new HashMap<>();

    void call(String eventName, Object obj) {
        if (!eventRepo.containsKey(eventName)) {
            eventRepo.put(eventName, new ArrayList<>());
        }
        var eventList = eventRepo.get(eventName);
        for (Consumer<Object> c : eventList) {
            c.accept(obj);
        }

    }

    void register(String eventName, Consumer<Object> action) {
        if (!eventRepo.containsKey(eventName)) {
            eventRepo.put(eventName, new ArrayList<>());
        }
        var eventList = eventRepo.get(eventName);
        eventList.add(action);
    }
}
