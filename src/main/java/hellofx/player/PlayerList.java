package hellofx.player;

import static java.util.stream.IntStream.range;

public class PlayerList {
    Player[] players;
    public PlayerList(int count) {
        players = new Player[count];
        range(0, count).forEach(i->players[i] = new Player());
    }

    public Player get(int index){
        return players[index];
    }
}
