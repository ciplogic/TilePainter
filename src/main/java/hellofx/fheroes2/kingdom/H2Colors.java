package hellofx.fheroes2.kingdom;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public class H2Colors {
    public IntArrayList _items = new IntArrayList();

    public H2Colors(int colors) {

        if ((colors & H2Color.BLUE) != 0) _items.add(H2Color.BLUE);
        if ((colors & H2Color.GREEN) != 0) _items.add(H2Color.GREEN);
        if ((colors & H2Color.RED) != 0) _items.add(H2Color.RED);
        if ((colors & H2Color.YELLOW) != 0) _items.add(H2Color.YELLOW);
        if ((colors & H2Color.ORANGE) != 0) _items.add(H2Color.ORANGE);
        if ((colors & H2Color.PURPLE) != 0) _items.add(H2Color.PURPLE);
    }
}
