package hellofx.fheroes2.gui;

import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.system.Settings;
import hellofx.player.Player;

public class Interface {
    public static Heroes GetFocusHeroes() {
        Player player = Settings.Get().GetPlayers().GetCurrent();
        return player != null ? player.GetFocus().GetHeroes() : null;
    }
}
