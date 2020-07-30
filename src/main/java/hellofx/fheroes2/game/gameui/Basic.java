package hellofx.fheroes2.game.gameui;

import hellofx.fheroes2.common.H2Rect;
import hellofx.fheroes2.gui.*;

public class Basic {
    GameArea gameArea = new GameArea();
    Radar radar;
    IconsPanel iconsPanel;
    ButtonsArea buttonsArea;
    StatusWindow statusWindow;
    ControlPanel controlPanel;

    HeroesBar heroesBar;

    int redraw;

    H2Rect scrollLeft;
    H2Rect scrollRight;
    H2Rect scrollBottom;
    H2Rect scrollTop;

    Text system_info;
}
