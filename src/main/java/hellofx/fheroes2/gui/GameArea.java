package hellofx.fheroes2.gui;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.TilKind;
import hellofx.fheroes2.common.H2Point;
import hellofx.fheroes2.common.H2Rect;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.maps.Mp2Kind;
import hellofx.fheroes2.maps.Tiles;
import hellofx.fheroes2.system.Players;
import hellofx.framework.controls.Painter;
import javafx.scene.image.Image;

import static hellofx.fheroes2.game.GameConsts.TILEWIDTH;
import static hellofx.fheroes2.gui.LevelKind.*;

public class GameArea {
    public H2Rect rectMaps = new H2Rect(0, 0, 30, 20);
    public H2Point cameraTopLeft = new H2Point();

    public void Repaint(Painter dst, int flag, H2Rect rt, Agg agg) {
        var world = World.Instance;
        var camDivX = cameraTopLeft.x / 32;
        var camModX = cameraTopLeft.x % 32;
        var camDivY = cameraTopLeft.y / 32;
        var camModY = cameraTopLeft.y % 32;
        for (int stepX = 0; stepX < rt.w; ++stepX) {
            var ox = rt.x + camDivX + stepX;
            for (int stepY = 0; stepY < rt.h; ++stepY) {
                var oy = rt.y + camDivY + stepY;
                var currentTile = world.GetTiles(ox, oy);
                Image tileSurface;
                if (currentTile == null) {
                    tileSurface = Agg.GetTIL(TilKind.GROUND32, 320, 0);
                } else
                    tileSurface = currentTile.RedrawTile();
                int finalPositionX = (stepX) * 32 - camModX;
                int finalPositionY = stepY * 32 - camModY;
                dst.drawImage(tileSurface, finalPositionX, finalPositionY);
            }
        }
        for (int stepX = 0; stepX < rt.w; ++stepX) {
            var ox = rt.x + rectMaps.x + stepX;
            for (int stepY = 0; stepY < rt.h; ++stepY) {
                var oy = rt.y + rectMaps.y + stepY;
                var currentTile = world.GetTiles(ox, oy);

                if (currentTile == null)
                    continue;
                // bottom
                if ((flag & LEVEL_BOTTOM) != 0) {
                    var tileSurface = currentTile.RedrawBottom(dst, (flag & LEVEL_OBJECTS) == 0, agg);
                    if (tileSurface != null) {
                        int finalPositionX = (stepX) * 32 - camModX;
                        int finalPositionY = stepY * 32 - camModY;
                        dst.drawImage(tileSurface.toImage(), finalPositionX, finalPositionY);
                    }
                }

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

                if (tile == null)
                    continue;
                if (tile.GetObject() == Mp2Kind.OBJ_HEROES && ((flag & LEVEL_HEROES) != 0)) {
                    Heroes hero = tile.GetHeroes();
                    if (hero != null)
                        hero.Redraw(dst, cameraTopLeft.x + TILEWIDTH * ox, cameraTopLeft.y + TILEWIDTH * oy, true);
                }
            }

        DrawHeroRoute(dst, flag, rt);
        // redraw fog
        if ((flag & LEVEL_FOG) != 0) {
            int colors = Players.FriendColors();

            for (int oy = rt.y; oy < rt.y + rt.h; ++oy)
                for (int ox = rt.x; ox < rt.x + rt.w; ++ox) {
                    Tiles tile = world.GetTiles(rectMaps.x + ox, rectMaps.y + oy);

                    if (tile == null)
                        continue;
                    if (tile.isFog(colors))
                        tile.RedrawFogs(dst, colors);
                }
        }


    }

    private void DrawHeroRoute(Painter dst, int flag, H2Rect rt) {
    }
}
