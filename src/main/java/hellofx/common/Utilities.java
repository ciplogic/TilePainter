package hellofx.common;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Utilities {

    public static void setBackground(Region root, Color color) {
        root.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    public static void writeTodo(String message) {
        System.out.println("TODO: " + message);
    }

    public static void timeIt(String message, Runnable action) {
        var start = System.nanoTime();
        action.run();
        var end = System.nanoTime();
        var delta = (end - start) / 1000.0;
        System.out.println("Time to do " + message + " is in ms: " + delta);
    }

    public static <T> void resizeList(List<T> list, int newSize, Supplier<T> creator) {
        list.clear();
        IntStream.range(0, newSize).forEach(i -> {
            var item = creator.get();
            list.add(item);
        });
    }

    public static String getResource(String path) {
        var file = new File(path);
        var result = file.toURI().toString();
        return result;
    }


    public static <T> T find_if(T[] addons, Predicate<T> isFound) {
        return Arrays.stream(addons).filter(isFound).findFirst().orElse(null);
    }

    public static <T> int count(T[] addons, Predicate<T> isFound) {
        return (int) Arrays.stream(addons).filter(isFound).count();
    }

    public static <T> int count(List<T> addons, Predicate<T> isFound) {
        return (int) addons.stream().filter(isFound).count();
    }

    public static boolean contains(int[] addons, IntPredicate isFound) {
        return Arrays.stream(addons).filter(isFound).findFirst().isPresent();
    }

    public static boolean contains(int[] addons, int value) {
        return contains(addons, i -> i == value);
    }


    public static <T> T find_if(List<T> addons, Predicate<T> isFound) {
        return addons.stream().filter(isFound::test).findFirst().orElse(null);
    }

    public static <T> T minValueInCollection(List<T> _items, FuncToInt<T> mapValue) {
        if (_items.size() == 0)
            return null;
        var first = _items.get(0);
        var minVal = mapValue.mapping(first);
        for (var i = 1; i < _items.size(); i++) {
            var item = _items.get(i);
            var itemSpeed = mapValue.mapping(item);
            if (itemSpeed < minVal) {
                first = item;
                minVal = itemSpeed;
            }
        }
        return first;
    }

    public interface FuncToInt<T> {
        int mapping(T value);
    }

    public static String StringLower(String text) {
        return text.toLowerCase();
    }

    public static <T> void removeLast(List<T> items) {
        items.remove(items.size() - 1);
    }
}
