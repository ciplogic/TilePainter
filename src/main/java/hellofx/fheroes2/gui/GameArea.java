package hellofx.fheroes2.gui;

import hellofx.fheroes2.agg.Agg;
import hellofx.fheroes2.agg.Bitmap;
import hellofx.fheroes2.agg.Sprite;
import hellofx.fheroes2.agg.TilKind;
import hellofx.fheroes2.agg.icn.IcnKind;
import hellofx.fheroes2.common.H2Rect;
import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.SkillT;
import hellofx.fheroes2.heroes.route.Path;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.maps.Ground;
import hellofx.fheroes2.maps.Mp2Kind;
import hellofx.fheroes2.maps.Tiles;
import hellofx.fheroes2.maps.objects.Maps;
import hellofx.fheroes2.system.Players;
import hellofx.framework.controls.Painter;

import java.util.ArrayList;

import static hellofx.fheroes2.game.GameConsts.TILEWIDTH;
import static hellofx.fheroes2.gui.LevelKind.*;

public class GameArea {
    public H2Rect rectMaps = new H2Rect(0, 0, 30, 20);

    public GameCamera camera;

    public void Repaint(Painter dst, int flag, H2Rect rt, Agg agg) {
        var world = World.Instance;
        var tileX = camera.getLeftTile();
        var tileY = camera.getTopTile();
        for (int stepX = 0; stepX < rt.w; ++stepX) {
            var ox = tileX + stepX;
            for (int stepY = 0; stepY < rt.h; ++stepY) {
                var oy = tileY + stepY;
                var currentTile = world.GetTiles(ox, oy);
                Bitmap tileSurface;
                if (currentTile == null) {
                    tileSurface = Agg.GetTIL(TilKind.GROUND32, 320, 0);
                } else
                    tileSurface = currentTile.RedrawTile();
                camera.drawImageOnTile(dst, tileSurface, ox, oy);
            }
        }
        var spritesToPaint = new ArrayList<Sprite>();
        for (int stepX = 0; stepX < rt.w; ++stepX) {
            var ox = tileX + stepX;
            for (int stepY = 0; stepY < rt.h; ++stepY) {
                var oy = tileY + stepY;
                var currentTile = world.GetTiles(ox, oy);
                if (currentTile == null)
                    continue;
                if (currentTile.maps_index == 33) {
                    int t = 6;
                }
                // bottom
                if ((flag & LEVEL_BOTTOM) != 0) {
                    currentTile.RedrawBottom((flag & LEVEL_OBJECTS) == 0, spritesToPaint);
                    spritesToPaint.forEach(sprite -> {
                        camera.drawSpriteOnTile(dst, sprite, ox, oy);
                    });
                }


                // ext object
                if ((flag & LEVEL_OBJECTS) != 0) {
                    currentTile.RedrawObjects(spritesToPaint);
                    spritesToPaint.forEach(sprite -> {
                        camera.drawSpriteOnTile(dst, sprite, ox, oy);
                    });
                }

                // top0
                if ((flag & LEVEL_TOP) != 0) {
                    currentTile.RedrawTop(dst, spritesToPaint, null);
                    spritesToPaint.forEach(sprite -> {
                        camera.drawSpriteOnTile(dst, sprite, ox, oy);
                    });

                }
            }
        }
        // heroes
        for (int oy = rt.y; oy < rt.y + rt.h; ++oy)
            for (int ox = rt.x; ox < rt.x + rt.w; ++ox) {
                var tile = world.GetTiles(rectMaps.x + ox, rectMaps.y + oy);

                if (tile == null)
                    continue;
                if (tile.GetObject() == Mp2Kind.OBJ_HEROES && (flag & LEVEL_HEROES) != 0) {
                    Heroes hero = tile.GetHeroes();
                    if (hero != null)
                        hero.Redraw(dst, TILEWIDTH * ox, TILEWIDTH * oy, true);
                }
            }

        var routeTiles = DrawHeroRoute(dst, flag, rt);
        routeTiles.forEach(sprite -> {
            camera.drawSpriteOnTile(dst, sprite, sprite.pos.x, sprite.pos.y);
        });
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

    private ArrayList<Sprite> DrawHeroRoute(Painter dst, int flag, H2Rect rt) {

        // route
        Heroes hero = 0 != (flag & LEVEL_HEROES) ? Interface.GetFocusHeroes() : null;

        var sprites = new ArrayList<Sprite>();
        if (hero == null || !hero.GetPath().isShow())
            return sprites;
        int green = hero.GetPath().GetAllowStep();

        boolean skipfirst = hero.isEnableMove() && 45 > hero.GetSpriteIndex() && 2 < hero.GetSpriteIndex() % 9;

        var it1 = hero.GetPath().begin();
        var it2 = hero.GetPath().end();
        var it3 = it1.copy();

        for (; !it1.isEqual(it2); it1.next()) {
            int from = it1.get().GetIndex();
            var mp = Maps.GetPoint(from);

            it3.next();
            --green;

            // is visible
            if (!(new H2Rect(rectMaps.x + rt.x, rectMaps.y + rt.y, rt.w, rt.h).contains(mp))
                    || (it1.pos == 0 && skipfirst))
                continue;

            int index = 0;
            if (!it2.isEqual(it3)) {
                int penalty = Ground.GetPenalty(from, Direction.CENTER,
                        hero.GetLevelSkill(SkillT.PATHFINDING));
                index = Path.GetIndexSprite(it1.get().GetDirection(), it3.get().GetDirection(), penalty);
            }

            Sprite sprite = Agg.GetICN(0 > green ? IcnKind.ROUTERED : IcnKind.ROUTE, index).clone();
            sprite.pos.x -= 14;
            sprites.add(sprite);
        }
        return sprites;
    }
}
