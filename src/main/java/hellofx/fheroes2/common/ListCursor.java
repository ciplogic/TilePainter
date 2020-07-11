package hellofx.fheroes2.common;

import java.util.List;

/**
 * Rough equivalent with C++ iterators
 */
public class ListCursor<T> {
    private final List<T> list;
    public int pos;

    public ListCursor(List<T> collection) {
        this.list = collection;
        this.pos = 0;
    }

    public ListCursor(List<T> collection, int pos) {
        this.list = collection;
        this.pos = pos;
    }

    public void next() {
        pos++;
    }

    public T get() {
        if (list.size() > pos)
            return list.get(pos);
        return null;
    }

    public boolean isEqual(ListCursor<T> it3) {
        return pos == it3.pos;
    }

    public ListCursor<T> copy() {
        return new ListCursor<>(list, pos);
    }
}
