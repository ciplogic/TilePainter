package hellofx.fheroes2.system;

import hellofx.fheroes2.kingdom.H2Color;
import hellofx.fheroes2.kingdom.H2Colors;
import hellofx.fheroes2.kingdom.RaceKind;
import hellofx.fheroes2.maps.FileInfo;
import hellofx.player.Player;
import hellofx.player.PlayerControl;

import java.util.ArrayList;
import java.util.List;

import static hellofx.common.Utilities.writeTodo;
import static hellofx.fheroes2.game.GameConsts.KINGDOMMAX;

public class Players {
    static Player[] _players = new Player[KINGDOMMAX + 1];
    private static int human_colors;
    List<Player> _items = new ArrayList<>();
    private int current_color;

    public Players() {

    }

    public static Player Get(int color) {
        return _players[H2Color.GetIndex(color)];
    }

    public static int GetPlayerRace(int color) {
        var player = Get(color);
        return player != null ? player.GetRace() : RaceKind.NONE;
    }

    public static int FriendColors() {
        int colors = 0;
        var players = Settings.Get().GetPlayers();

        if (0 != (players.current_color & HumanColors())) {
            var player = players.GetCurrent();
            if (player != null)
                colors = player.GetFriends();
        } else
            colors = HumanColors();

        return colors;
    }

    private Player GetCurrent() {
        return Get(current_color);
    }

    public static int HumanColors() {
        //TODO
        writeTodo("HumanColors");
        return 0;
    }

    public int GetColors() {
        return GetColors(0xFF, false);
    }

    public int GetColors(int control, boolean strong) {
        int res = 0;

        for (var it : _items)
            if (control == 0xFF ||
                    (strong && it.GetControl() == control) ||
                    (!strong && ((it.GetControl() & control) != 0)))
                res |= it.GetColor();

        return res;
    }

    public void Init(FileInfo fi) {
        if (fi.kingdom_colors == 0) {
            return;
        }
        clear();
        H2Colors vcolors = new H2Colors(fi.kingdom_colors);

        Player first = null;

        for (int vcolor : vcolors._items) {
            var player = new Player(vcolor);
            player.SetRace(fi.KingdomRace(vcolor));
            player.SetControl(PlayerControl.CONTROL_AI);
            player.SetFriends(vcolor | fi.unions[H2Color.GetIndex(vcolor)]);

            if ((vcolor & fi.HumanOnlyColors()) != 0 && Settings.Get().GameType(GameType.TYPE_MULTI))
                player.SetControl(PlayerControl.CONTROL_HUMAN);
            else if ((vcolor & fi.AllowHumanColors()) != 0)
                player.SetControl(player.GetControl() | PlayerControl.CONTROL_HUMAN);

            if (first != null && ((player.GetControl() & PlayerControl.CONTROL_HUMAN) != 0))
                first = player;

            _items.add(player);
            _players[H2Color.GetIndex(vcolor)] = player;
        }

        if (first != null)
            first.SetControl(PlayerControl.CONTROL_HUMAN);
    }

    private void clear() {
        _items.clear();
    }

    public void SetStartGame() {
        _items.forEach(player -> {
            player.SetPlay(true);
            PlayerFocusReset(player);
            PlayerFixRandomRace(player);
            PlayerFixMultiControl(player);
        });


        current_color = H2Color.NONE;
        human_colors = H2Color.NONE;
    }

    private void PlayerFixMultiControl(Player player) {
    }

    private void PlayerFixRandomRace(Player player) {
        if (player != null && player.GetRace() == RaceKind.RAND) {
            player.SetRace(RaceKind.Rand());
        }

    }

    private void PlayerFocusReset(Player player) {

        if (player != null) player.GetFocus().Reset();
    }
}
