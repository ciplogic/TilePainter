package hellofx.fheroes2.agg.icn;

import hellofx.fheroes2.system.Settings;

public class Icn {
    public static boolean SkipLocalAlpha(int icn) {
        switch (icn) {
            case IcnKind.SYSTEM:
            case IcnKind.SYSTEME:
            case IcnKind.BUYBUILD:
            case IcnKind.BUYBUILE:
            case IcnKind.BOOK:
            case IcnKind.CSPANBKE:
            case IcnKind.CPANBKGE:
            case IcnKind.CAMPBKGE:
                return true;

            default:
                break;
        }

        return false;
    }

    public static int AnimationFrame(int icn, int start, int ticket, boolean quantity) {
        switch (icn) {
            case IcnKind.TELEPORT1:
            case IcnKind.TELEPORT2:
            case IcnKind.TELEPORT3:
                return start + ticket % 8;

            case IcnKind.FOUNTAIN:
            case IcnKind.TREASURE:
                return start + ticket % 2;

            case IcnKind.TWNBBOAT:
            case IcnKind.TWNKBOAT:
            case IcnKind.TWNNBOAT:
            case IcnKind.TWNSBOAT:
            case IcnKind.TWNWBOAT:
            case IcnKind.TWNZBOAT:
                return 1 + ticket % 9;

            case IcnKind.CMBTCAPB:
            case IcnKind.CMBTCAPK:
            case IcnKind.CMBTCAPN:
            case IcnKind.CMBTCAPS:
            case IcnKind.CMBTCAPW:
            case IcnKind.CMBTCAPZ:
                return 1 + ticket % 10;

            case IcnKind.CMBTHROB:
                return 1 + ticket % 18;
            case IcnKind.CMBTHROK:
                return 1 + ticket % 19;
            case IcnKind.CMBTHRON:
                return 1 + ticket % 19;
            case IcnKind.CMBTHROS:
                return 1 + ticket % 16;
            case IcnKind.CMBTHROW:
                return 1 + ticket % 16;
            case IcnKind.CMBTHROZ:
                return 1 + ticket % 18;

            case IcnKind.HEROFL00:
            case IcnKind.HEROFL01:
            case IcnKind.HEROFL02:
            case IcnKind.HEROFL03:
            case IcnKind.HEROFL04:
            case IcnKind.HEROFL05:
                return ticket % 5;

            case IcnKind.TWNBDOCK:
            case IcnKind.TWNKDOCK:
            case IcnKind.TWNNDOCK:
            case IcnKind.TWNSDOCK:
            case IcnKind.TWNWDOCK:
            case IcnKind.TWNZDOCK:

            case IcnKind.TWNBEXT0:
            case IcnKind.TWNKEXT0:
            case IcnKind.TWNNEXT0:
            case IcnKind.TWNSEXT0:
            case IcnKind.TWNWEXT0:
            case IcnKind.TWNZEXT0:

            case IcnKind.TWNBCAPT:
            case IcnKind.TWNBDW_3:
            case IcnKind.TWNBDW_4:
            case IcnKind.TWNBDW_5:
            case IcnKind.TWNBEXT1:
            case IcnKind.TWNBMOAT:
            case IcnKind.TWNBUP_3:
            case IcnKind.TWNBUP_4:
            case IcnKind.TWNKCSTL:
            case IcnKind.TWNKDW_0:
            case IcnKind.TWNKLTUR:
            case IcnKind.TWNKRTUR:
            case IcnKind.TWNKTHIE:
            case IcnKind.TWNKTVRN:
            case IcnKind.TWNNCSTL:
            case IcnKind.TWNNDW_2:
            case IcnKind.TWNNUP_2:
            case IcnKind.TWNSCAPT:
            case IcnKind.TWNSCSTL:
            case IcnKind.TWNSDW_0:
            case IcnKind.TWNSDW_1:
            case IcnKind.TWNSEXT1:
            case IcnKind.TWNSTHIE:
            case IcnKind.TWNSTVRN:
            case IcnKind.TWNSUP_1:
            case IcnKind.TWNSWEL2:
            case IcnKind.TWNWCAPT:
            case IcnKind.TWNWCSTL:
            case IcnKind.TWNWMOAT:
            case IcnKind.TWNZCSTL:
            case IcnKind.TWNZDW_0:
            case IcnKind.TWNZDW_2:
            case IcnKind.TWNZTHIE:
            case IcnKind.TWNZUP_2:
                return 1 + ticket % 5;

            case IcnKind.TWNBCSTL:
            case IcnKind.TWNKDW_2:
            case IcnKind.TWNKUP_2:
            case IcnKind.TWNNDW_5:
            case IcnKind.TWNNWEL2:
            case IcnKind.TWNWDW_0:
            case IcnKind.TWNWWEL2:
            case IcnKind.TWNZTVRN:
                return 1 + ticket % 6;

            case IcnKind.TWNKDW_4:
            case IcnKind.TWNKUP_4:
                return 1 + ticket % 7;

            case IcnKind.TAVWIN:
                return 2 + ticket % 20;

            case IcnKind.CMBTLOS1:
                return 1 + ticket % 30;
            case IcnKind.CMBTLOS2:
                return 1 + ticket % 29;
            case IcnKind.CMBTLOS3:
                return 1 + ticket % 22;
            case IcnKind.CMBTFLE1:
                return 1 + ticket % 43;
            case IcnKind.CMBTFLE2:
                return 1 + ticket % 26;
            case IcnKind.CMBTFLE3:
                return 1 + ticket % 25;
            case IcnKind.CMBTSURR:
                return 1 + ticket % 20;

            case IcnKind.WINCMBT:
                return 1 + ticket % 20;

            case IcnKind.MINIMON:
                return start + 1 + ticket % 6;

            case IcnKind.TWNNMAGE:
                return start + 1 + ticket % 5;

            case IcnKind.TWNBMAGE:
                return 4 == start ? start + 1 + ticket % 8 : 0;

            case IcnKind.SHNGANIM:
                return 1 + ticket % 39;

            case IcnKind.BTNSHNGL:
                return start + ticket % 4;

            case IcnKind.OBJNHAUN:
                return ticket % 15;

            case IcnKind.OBJNWATR:

                switch (start) {
                    // buttle
                    case 0x00:
                        return start + ticket % 11 + 1;

                    // shadow
                    case 0x0C:
                        // chest
                    case 0x13:
                        // shadow
                    case 0x26:
                        // flotsam
                    case 0x2D:
                        // unkn
                    case 0x37:
                        // boat
                    case 0x3E:
                        // waves
                    case 0x45:
                        // seaweed
                    case 0x4C:
                    case 0x53:
                    case 0x5A:
                    case 0x61:
                    case 0x68:
                        // sailor-man
                    case 0x6F:
                        // shadow
                    case 0xBC:
                        // buoy
                    case 0xC3:
                        // broken ship (right)
                    case 0xE2:
                    case 0xE9:
                    case 0xF1:
                    case 0xF8:
                        return start + ticket % 6 + 1;

                    // seagull on stones
                    case 0x76:
                    case 0x86:
                    case 0x96:
                    case 0xA6:
                        return start + ticket % 15 + 1;

                    // whirlpool
                    case 0xCA:
                    case 0xCE:
                    case 0xD2:
                    case 0xD6:
                    case 0xDA:
                    case 0xDE:
                        return start + ticket % 3 + 1;

                    default:
                        return 0;
                }

            case IcnKind.OBJNWAT2:

                switch (start) {
                    // sail broken ship (left)
                    case 0x03:
                    case 0x0C:
                        return start + ticket % 6 + 1;

                    default:
                        return 0;
                }
            case IcnKind.OBJNCRCK:
                switch (start) {
                    // pool of oil
                    case 0x50:
                    case 0x5B:
                    case 0x66:
                    case 0x71:
                    case 0x7C:
                    case 0x89:
                    case 0x94:
                    case 0x9F:
                    case 0xAA:
                        // smoke from chimney
                    case 0xBE:
                        // shadow smoke
                    case 0xCA:
                        return start + ticket % 10 + 1;

                    default:
                        return 0;
                }
            case IcnKind.OBJNDIRT:

                switch (start) {
                    // mill
                    case 0x99:
                    case 0x9D:
                    case 0xA1:
                    case 0xA5:
                    case 0xA9:
                    case 0xAD:
                    case 0xB1:
                    case 0xB5:
                    case 0xB9:
                    case 0xBD:
                        return start + ticket % 3 + 1;

                    default:
                        return 0;
                }
            case IcnKind.OBJNDSRT:

                switch (start) {
                    // campfire
                    case 0x36:
                    case 0x3D:
                        return start + ticket % 6 + 1;

                    default:
                        return 0;
                }
            case IcnKind.OBJNGRA2:

                switch (start) {
                    // mill
                    case 0x17:
                    case 0x1B:
                    case 0x1F:
                    case 0x23:
                    case 0x27:
                    case 0x2B:
                    case 0x2F:
                    case 0x33:
                    case 0x37:
                    case 0x3B:
                        return start + ticket % 3 + 1;

                    // smoke from chimney
                    case 0x3F:
                    case 0x46:
                    case 0x4D:
                        // archerhouse
                    case 0x54:
                        // smoke from chimney
                    case 0x5D:
                    case 0x64:
                        // shadow smoke
                    case 0x6B:
                        // peasanthunt
                    case 0x72:
                        return start + ticket % 6 + 1;

                    default:
                        return 0;
                }
            case IcnKind.OBJNLAV2:

                switch (start) {
                    // middle volcano
                    case 0x00:
                        // shadow
                    case 0x07:
                    case 0x0E:
                        // lava
                    case 0x15:
                        return start + ticket % 6 + 1;

                    // small volcano
                    // shadow
                    case 0x21:
                    case 0x2C:
                        // lava
                    case 0x37:
                    case 0x43:
                        return start + ticket % 10 + 1;

                    default:
                        return 0;
                }

            case IcnKind.OBJNLAV3:

                // big volcano
                switch (start) {
                    // smoke
                    case 0x00:
                    case 0x0F:
                    case 0x1E:
                    case 0x2D:
                    case 0x3C:
                    case 0x4B:
                    case 0x5A:
                    case 0x69:
                    case 0x87:
                    case 0x96:
                    case 0xA5:
                        // shadow
                    case 0x78:
                    case 0xB4:
                    case 0xC3:
                    case 0xD2:
                    case 0xE1:
                        return start + ticket % 14 + 1;

                    default:
                        return 0;
                }
            case IcnKind.OBJNLAVA:
                switch (start) {
                    // shadow of lava
                    case 0x4E:
                    case 0x58:
                    case 0x62:
                        return start + ticket % 9 + 1;

                    default:
                        return 0;
                }
            case IcnKind.OBJNMUL2:

                switch (start) {
                    // lighthouse
                    case 0x3D:
                        return start + ticket % 9 + 1;

                    // alchemytower
                    case 0x1B:
                        // watermill
                    case 0x53:
                    case 0x5A:
                    case 0x62:
                    case 0x69:
                        // fire in wagoncamp
                    case 0x81:
                        // smoke smithy (2 chimney)
                    case 0xA6:
                        // smoke smithy (1 chimney)
                    case 0xAD:
                        // shadow smoke
                    case 0xB4:
                        return start + ticket % 6 + 1;

                    // magic garden
                    case 0xBE:
                        return quantity ? start + ticket % 6 + 1 : start + 7;

                    default:
                        return 0;
                }
            case IcnKind.OBJNMULT:

                switch (start) {
                    // smoke
                    case 0x05:
                        // shadow
                    case 0x0F:
                    case 0x19:
                        return start + ticket % 9 + 1;

                    // smoke
                    case 0x24:
                        // shadow
                    case 0x2D:
                        return start + ticket % 8 + 1;

                    // smoke
                    case 0x5A:
                        // shadow
                    case 0x61:
                    case 0x68:
                    case 0x7C:
                        // campfire
                    case 0x83:
                        return start + ticket % 6 + 1;

                    default:
                        return 0;
                }
            case IcnKind.OBJNSNOW:

                switch (start) {
                    // firecamp
                    case 0x04:
                        // alchemytower
                    case 0x97:
                        // watermill
                    case 0xA2:
                    case 0xA9:
                    case 0xB1:
                    case 0xB8:
                        return start + ticket % 6 + 1;

                    // mill
                    case 0x60:
                    case 0x64:
                    case 0x68:
                    case 0x6C:
                    case 0x70:
                    case 0x74:
                    case 0x78:
                    case 0x7C:
                    case 0x80:
                    case 0x84:
                        return start + ticket % 3 + 1;

                    default:
                        return 0;
                }

            case IcnKind.OBJNSWMP:

                switch (start) {
                    // shadow
                    case 0x00:
                    case 0x0E:
                    case 0x2B:
                        // smoke
                    case 0x07:
                    case 0x22:
                    case 0x33:
                        // light in window
                    case 0x16:
                    case 0x3A:
                    case 0x43:
                    case 0x4A:
                        return start + ticket % 6 + 1;

                    default:
                        return 0;
                }

                // extra objects for loyalty version
            case IcnKind.X_LOC1:

                if (Settings.Get().PriceLoyaltyVersion())
                    switch (start) {
                        // alchemist tower
                        case 0x04:
                        case 0x0D:
                        case 0x16:
                            // arena
                        case 0x1F:
                        case 0x28:
                        case 0x32:
                        case 0x3B:
                            // earth altar
                        case 0x55:
                        case 0x5E:
                        case 0x67:
                            return start + ticket % 8 + 1;

                        default:
                            return 0;
                    }
                // extra objects for loyalty version
            case IcnKind.X_LOC2:

                if (Settings.Get().PriceLoyaltyVersion())
                    switch (start) {
                        // mermaid
                        case 0x0A:
                        case 0x13:
                        case 0x1C:
                        case 0x25:
                            // sirens
                        case 0x2F:
                        case 0x38:
                        case 0x41:
                        case 0x4A:
                        case 0x53:
                        case 0x5C:
                        case 0x66:
                            return start + ticket % 8 + 1;

                        default:
                            return 0;
                    }
                break;

            // extra objects for loyalty version
            case IcnKind.X_LOC3:

                if (Settings.Get().PriceLoyaltyVersion())
                    switch (start) {
                        // hut magi
                        case 0x00:
                        case 0x0A:
                        case 0x14:
                            // eye magi
                        case 0x20:
                        case 0x29:
                        case 0x32:
                            return start + ticket % 8 + 1;

                        // barrier
                        case 0x3C:
                        case 0x42:
                        case 0x48:
                        case 0x4E:
                        case 0x54:
                        case 0x5A:
                        case 0x60:
                        case 0x66:
                            return start + ticket % 4 + 1;

                        default:
                            return 0;
                    }
                break;


            default:
                break;
        }

        return 0;
    }
}
