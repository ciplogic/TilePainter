package hellofx.fheroes2.gui;

import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.H2Rect;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.maps.H2Obj;
import hellofx.fheroes2.maps.Tiles;
import hellofx.fheroes2.system.Players;
import hellofx.framework.controls.Painter;

import static hellofx.fheroes2.game.GameConsts.TILEWIDTH;
import static hellofx.fheroes2.gui.LevelKind.*;

public class GameArea {
    H2Rect rectMaps = new H2Rect();
    H2Point rectMapsPosition = new H2Point();

    public void Repaint(Painter dst, int flag, H2Rect rt) {
        var world = World.Instance;
        for (int stepX = 0; stepX < rt.w; ++stepX) {
            var ox = rt.x + rectMaps.x + stepX;
            for (int stepY = 0; stepY < rt.h; ++stepY) {
                var oy = rt.y + rectMaps.y + stepY;
                var currentTile = world.GetTiles(ox, oy);
                currentTile.RedrawTile(dst);
            }
        }
        for (int stepX = 0; stepX < rt.w; ++stepX) {
            var ox = rt.x + rectMaps.x + stepX;
            for (int stepY = 0; stepY < rt.h; ++stepY) {
                var oy = rt.y + rectMaps.y + stepY;
                var currentTile = world.GetTiles(ox, oy);
                // bottom
                if ((flag & LEVEL_BOTTOM) != 0)
                    currentTile.RedrawBottom(dst, (flag & LEVEL_OBJECTS) == 0);

                // ext object
                if ((flag & LEVEL_OBJECTS) != 0)
                    currentTile.RedrawObjects(dst);

                // top
                if ((flag & LEVEL_TOP) != 0)
                    currentTile.RedrawTop(dst);
            }
        }
        // heroes
        for (int oy = rt.y; oy < rt.y + rt.h; ++oy)
            for (int ox = rt.x; ox < rt.x + rt.w; ++ox) {
                var tile = world.GetTiles(rectMaps.x + ox, rectMaps.y + oy);

                if (tile.GetObject() == H2Obj.OBJ_HEROES && ((flag & LEVEL_HEROES) != 0)) {
                    Heroes hero = tile.GetHeroes();
                    if (hero != null)
                        hero.Redraw(dst, rectMapsPosition.x + TILEWIDTH * ox, rectMapsPosition.y + TILEWIDTH * oy, true);
                }
            }

        DrawHeroRoute(dst, flag, rt);
        // redraw fog
        if ((flag & LEVEL_FOG) != 0) {
            int colors = Players.FriendColors();

            for (int oy = rt.y; oy < rt.y + rt.h; ++oy)
                for (int ox = rt.x; ox < rt.x + rt.w; ++ox) {
                    Tiles tile = world.GetTiles(rectMaps.x + ox, rectMaps.y + oy);

                    if (tile.isFog(colors))
                        tile.RedrawFogs(dst, colors);
                }
        }


    }

    private void DrawHeroRoute(Painter dst, int flag, H2Rect rt) {
    }
}
