package hellofx.fheroes2.maps;

import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.kingdom.H2Color;
import hellofx.framework.controls.Painter;

import static hellofx.fheroes2.serialize.ByteVectorReader.toByte;

public class Tiles {
    public Addons addons_level1 = new Addons();
    public Addons addons_level2 = new Addons(); // 16
    public int maps_index = 0;
    public short pack_sprite_index = 0;
    public short tile_passable = 0;
    public byte mp2_object = 0;
    public byte fog_colors = 0;
    public byte quantity1 = 0;
    public byte quantity2 = 0;
    public byte quantity3 = 0;

    public void Init(int index, Mp2Tile mp2) {
        tile_passable = Direction.DIRECTION_ALL;
        quantity1 = Mp2Tile.quantity1;
        quantity2 = Mp2Tile.quantity2;
        quantity3 = 0;
        fog_colors = H2Color.ALL;

        SetTile(Mp2Tile.tileIndex, Mp2Tile.shape);
        SetIndex(index);
        SetObject(Mp2Tile.generalObject);

        addons_level1._items.clear();
        addons_level2._items.clear();

        AddonsPushLevel1(mp2);
        AddonsPushLevel2(mp2);
    }

    private void AddonsPushLevel1(Mp2Tile mt) {
        if (Mp2Tile.objectName1 != 0 && toByte(Mp2Tile.indexName1) < 0xFF)
            AddonsPushLevel1(new TilesAddon(0, Mp2Tile.uniqNumber1, Mp2Tile.objectName1, Mp2Tile.indexName1));
    }

    private void AddonsPushLevel2(Mp2Tile mt) {
        if (Mp2Tile.objectName2 != 0 && toByte(Mp2Tile.indexName2) < 0xFF)
            AddonsPushLevel1(new TilesAddon(0, Mp2Tile.uniqNumber2, Mp2Tile.objectName2, Mp2Tile.indexName2));
    }

    private void SetObject(byte generalObject) {
        mp2_object = generalObject;
    }

    private void SetIndex(int index) {
        maps_index = index;
    }

    private void SetTile(int sprite_index, int shape) {
        pack_sprite_index = (short) PackTileSpriteIndex(sprite_index, shape);
    }

    private int PackTileSpriteIndex(int index, int shape) {
        return shape << 14 | 0x3FFF & index;
    }

    public void AddonsPushLevel1(Mp2Addon ma) {
        if (ma.objectNameN1 != 0 && toByte(ma.indexNameN1) < 0xFF)
            AddonsPushLevel1(new TilesAddon(ma.quantityN, ma.uniqNumberN1, ma.objectNameN1, ma.indexNameN1));

    }

    private void AddonsPushLevel1(TilesAddon ta) {
        if (TilesAddon.ForceLevel2(ta))
            addons_level1._items.add(ta);
        else
            addons_level2._items.add(ta);
    }

    public void AddonsPushLevel2(Mp2Addon mp2Addon) {
    }

    public void AddonsSort() {
    }

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

    public Heroes GetHeroes() {
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

    public void RedrawBottom(Painter dst, boolean b) {
        //TODO
    }

    public void RedrawObjects(Painter dst) {
        //TODO
    }

    public void RedrawTop(Painter dst) {
        //TODO
    }

    public boolean isFog(int colors) {
        //TODO
        return false;
    }

    public void RedrawFogs(Painter dst, int colors) {
    }

    public void RedrawTile(Painter dst) {

    }
}
