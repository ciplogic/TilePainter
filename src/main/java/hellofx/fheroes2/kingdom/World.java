package hellofx.fheroes2.kingdom;

import hellofx.fheroes2.game.GameConsts;
import hellofx.fheroes2.game.GameStatic;
import hellofx.fheroes2.maps.*;
import hellofx.fheroes2.serialize.ByteVectorReader;
import hellofx.fheroes2.serialize.FileUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static hellofx.fheroes2.game.GameConsts.MP2OFFSETDATA;
import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class World {
    IntArrayList vec_object = new IntArrayList();
    private short w;
    private short h;

    public boolean loadMapMp2(String fileName) {
        if (!FileUtils.Exists(fileName))
            return false;
        var vectorBytes = FileUtils.ReadAllBytes(fileName);
        var fs = new ByteVectorReader(vectorBytes);

        // check (mp2, mx2) ID
        if (fs.getBE32() != 0x5C000000)
            return false;

        var endof_mp2 = fs.size();
        fs.seek(endof_mp2 - 4);
        GameStatic.uniq = fs.getLE32();

        // offset data
        fs.seek(MP2OFFSETDATA - 2 * 4);

        // width
        var mapWidth = fs.getLE32();
        switch (mapWidth) {
            case MapSizeConsts.SMALL:
            case MapSizeConsts.MEDIUM:
            case MapSizeConsts.LARGE:
            case MapSizeConsts.XLARGE:
                this.w = (short) mapWidth;
                break;
            default:
                this.w = 0;
                break;
        }
        // width
        var mapHeight = fs.getLE32();
        switch (mapHeight) {
            case MapSizeConsts.SMALL:
            case MapSizeConsts.MEDIUM:
            case MapSizeConsts.LARGE:
            case MapSizeConsts.XLARGE:
                this.h = (short) mapWidth;
                break;
            default:
                this.h = 0;
                break;
        }
        if (w == 0 || h == 0 || w != h)
            return false;
        fs.skip(w * h * GameConsts.SIZEOFMP2TILE);
        var addonCount = fs.getLE32();
        var vec_mp2addons = new ArrayList<Mp2Addon>(addonCount);
        IntStream.range(0, addonCount).forEach(
                i -> {
                    var addon = new Mp2Addon();
                    addon.loadFromMp2Stream(fs);
                    vec_mp2addons.add(addon);
                }
        );
        var endof_addons = fs.tell();
        fs.seek(MP2OFFSETDATA);
        IntStream.range(0, w * h)
                .forEach(index -> {
                    var mp2tile = new Mp2Tile();

                    // offset first addon
                    var offsetAddonsBlock = mp2tile.loadFromMp2Stream(fs);

                    switch (toByte(Mp2Tile.generalObject)) {
                        case IcnObjConsts.OBJ_RNDTOWN:
                        case IcnObjConsts.OBJ_RNDCASTLE:
                        case IcnObjConsts.OBJ_CASTLE:
                        case IcnObjConsts.OBJ_HEROES:
                        case IcnObjConsts.OBJ_SIGN:
                        case IcnObjConsts.OBJ_BOTTLE:
                        case IcnObjConsts.OBJ_EVENT:
                        case IcnObjConsts.OBJ_SPHINX:
                        case IcnObjConsts.OBJ_JAIL:
                            vec_object.add(index);
                            break;
                        default:
                            break;
                    }

                    var tile = new Tiles();

                    tile.Init(index, mp2tile);

                    // load all addon for current tils
                    while (offsetAddonsBlock != 0) {
                        if (vec_mp2addons.size() <= offsetAddonsBlock) {
                            break;
                        }
                        tile.AddonsPushLevel1(vec_mp2addons.get(offsetAddonsBlock));
                        tile.AddonsPushLevel2(vec_mp2addons.get(offsetAddonsBlock));
                        offsetAddonsBlock = vec_mp2addons.get(offsetAddonsBlock).indexAddon;
                    }

                    tile.AddonsSort();

                });

        // after addons
        fs.seek(endof_addons);
        return true;
    }
}
