package hellofx.fheroes2.common;

import java.util.List;

public class Cursor {
    public static <T> ListCursor<T> end(List<T> collection) {
        var result = new ListCursor<T>(collection, collection.size());
        return result;
    }

}
