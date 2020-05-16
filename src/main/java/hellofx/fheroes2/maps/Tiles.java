package hellofx.fheroes2.maps;

import hellofx.fheroes2.heroes.Heroes;

import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class Tiles {
    public void Init(int index, Mp2Tile mp2tile) {
    }

    public void AddonsPushLevel1(Mp2Addon mp2Addon) {
    }

    public void AddonsPushLevel2(Mp2Addon mp2Addon) {
    }

    public void AddonsSort() {
    }

    public Addons addons_level1;
    public Addons addons_level2; // 16

    public int maps_index = 0;

    public short pack_sprite_index = 0;
    public short tile_passable = 0;

    public byte mp2_object = 0;
    public byte fog_colors = 0;

    public byte quantity1 = 0;
    public byte quantity2 = 0;
    public byte quantity3 = 0;

    public int GetQuantity1() {
        return toByte(quantity1);
    }

    public int GetQuantity2() {
        return toByte(quantity2);
    }

    public int GetQuantity3() {
        return toByte(quantity3);
    }

    public int GetObject() {
        return GetObject(true);
    }

    public int GetObject(boolean skip_hero) {
        if (!skip_hero && H2Obj.OBJ_HEROES == mp2_object) {
            var hero = GetHeroes();
            return hero != null ? hero.GetMapsObject() : H2Obj.OBJ_ZERO;
        }

        return mp2_object;
    }

    private Heroes GetHeroes() {
        //TODO
        return null;
    }

    public int GetIndex() {
        return maps_index;
    }

    public TilesAddon FindObjectConst(int objs) {
        //TODO
        return null;
    }
}
