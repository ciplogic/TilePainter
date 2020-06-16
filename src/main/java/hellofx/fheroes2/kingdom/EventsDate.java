package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.resource.Funds;
import hellofx.fheroes2.serialize.ByteVectorReader;

import static hellofx.common.Utilities.writeTodo;

public class EventsDate {
    public Funds resource;
    public boolean computer = false;
    public int first = 0;
    public int subsequent;
    public int colors;
    public String message = "";

    public void LoadFromMP2(ByteVectorReader bvr) {
        //TODO
        writeTodo("EventsDate");
    }
}
