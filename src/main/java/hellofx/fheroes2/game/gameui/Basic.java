package hellofx.fheroes2.game.gameui;

import hellofx.fheroes2.common.H2Rect;
import hellofx.fheroes2.gui.*;

public class Basic {
    GameArea gameArea = new GameArea();
    Radar radar = new Radar();
    IconsPanel iconsPanel = new IconsPanel();
    ButtonsArea buttonsArea = new ButtonsArea();
    StatusWindow statusWindow = new StatusWindow();
    ControlPanel controlPanel = new ControlPanel();

    HeroesBar heroesBar = new HeroesBar();

    int redraw;

    H2Rect scrollLeft;
    H2Rect scrollRight;
    H2Rect scrollBottom;
    H2Rect scrollTop;

    Text system_info;
}
