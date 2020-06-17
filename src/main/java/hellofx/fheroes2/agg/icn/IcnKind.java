package hellofx.fheroes2.agg.icn;

import hellofx.fheroes2.serialize.FileUtils;
import hellofx.fheroes2.system.Settings;

import java.util.HashMap;
import java.util.Map;

public class IcnKind {
    public static final int UNKNOWN = 0;
    public static final int ADVBORDE = 1;
    public static final int ADVBORD = 2;
    public static final int ADVBTNS = 3;
    public static final int ADVEBTNS = 4;
    public static final int ADVMCO = 5;
    public static final int AELEM = 6;
    public static final int APANBKGE = 7;
    public static final int APANBKG = 8;
    public static final int APANELE = 9;
    public static final int APANEL = 10;
    public static final int ARCHER2 = 11;
    public static final int ARCHER = 12;
    public static final int ARCH_MSL = 13;
    public static final int ART32 = 14;
    public static final int ARTFX = 15;
    public static final int ARTIFACT = 16;
    public static final int BARB32 = 17;
    public static final int B_BFLG32 = 18;
    public static final int BERZERK = 19;
    public static final int B_FLAG32 = 20;
    public static final int BIGBAR = 21;
    public static final int BLDGXTRA = 22;
    public static final int BLESS = 23;
    public static final int BLIND = 24;
    public static final int BLUEFIRE = 25;
    public static final int BOAR = 26;
    public static final int BOAT32 = 27;
    public static final int BOATSHAD = 28;
    public static final int BOATWIND = 29;
    public static final int BOOK = 30;
    public static final int BORDEDIT = 31;
    public static final int BOULDER = 32;
    public static final int BRCREST = 33;
    public static final int BROTHERS = 34;
    public static final int BTNBAUD = 35;
    public static final int BTNCMPGN = 36;
    public static final int BTNCOM = 37;
    public static final int BTNDCCFG = 38;
    public static final int BTNDC = 39;
    public static final int BTNEMAIN = 40;
    public static final int BTNENEW = 41;
    public static final int BTNESIZE = 42;
    public static final int BTNHOTST = 43;
    public static final int BTNMCFG = 44;
    public static final int BTNMODEM = 45;
    public static final int BTNMP = 46;
    public static final int BTNNET2 = 47;
    public static final int BTNNET = 48;
    public static final int BTNNEWGM = 49;
    public static final int BTNSHNGL = 50;
    public static final int BUILDING = 51;
    public static final int BUYBUILD = 52;
    public static final int BUYBUILE = 53;
    public static final int CAMPBKGE = 54;
    public static final int CAMPBKGG = 55;
    public static final int CAMPXTRE = 56;
    public static final int CAMPXTRG = 57;
    public static final int CAPTCOVR = 58;
    public static final int CASLBAR = 59;
    public static final int CASLWIND = 60;
    public static final int CASLXTRA = 61;
    public static final int CASTBKGB = 62;
    public static final int CASTBKGK = 63;
    public static final int CASTBKGN = 64;
    public static final int CASTBKGS = 65;
    public static final int CASTBKGW = 66;
    public static final int CASTBKGZ = 67;
    public static final int CASTLEB = 68;
    public static final int CASTLEK = 69;
    public static final int CASTLEN = 70;
    public static final int CASTLES = 71;
    public static final int CASTLEW = 72;
    public static final int CASTLEZ = 73;
    public static final int CATAPULT = 74;
    public static final int CAVALRYB = 75;
    public static final int CAVALRYR = 76;
    public static final int CBKGBEAC = 77;
    public static final int CBKGCRCK = 78;
    public static final int CBKGDIMT = 79;
    public static final int CBKGDITR = 80;
    public static final int CBKGDSRT = 81;
    public static final int CBKGGRAV = 82;
    public static final int CBKGGRMT = 83;
    public static final int CBKGGRTR = 84;
    public static final int CBKGLAVA = 85;
    public static final int CBKGSNMT = 86;
    public static final int CBKGSNTR = 87;
    public static final int CBKGSWMP = 88;
    public static final int CBKGWATR = 89;
    public static final int CELLWIN = 90;
    public static final int CENTAUR = 91;
    public static final int CFLGSMAL = 92;
    public static final int CLOP32 = 93;
    public static final int CLOUDLUK = 94;
    public static final int CMBTCAPB = 95;
    public static final int CMBTCAPK = 96;
    public static final int CMBTCAPN = 97;
    public static final int CMBTCAPS = 98;
    public static final int CMBTCAPW = 99;
    public static final int CMBTCAPZ = 100;
    public static final int CMBTFLE1 = 101;
    public static final int CMBTFLE2 = 102;
    public static final int CMBTFLE3 = 103;
    public static final int CMBTHROB = 104;
    public static final int CMBTHROK = 105;
    public static final int CMBTHRON = 106;
    public static final int CMBTHROS = 107;
    public static final int CMBTHROW = 108;
    public static final int CMBTHROZ = 109;
    public static final int CMBTLOS1 = 110;
    public static final int CMBTLOS2 = 111;
    public static final int CMBTLOS3 = 112;
    public static final int CMBTMISC = 113;
    public static final int CMBTSURR = 114;
    public static final int CMSECO = 115;
    public static final int COBJ0000 = 116;
    public static final int COBJ0001 = 117;
    public static final int COBJ0002 = 118;
    public static final int COBJ0003 = 119;
    public static final int COBJ0004 = 120;
    public static final int COBJ0005 = 121;
    public static final int COBJ0006 = 122;
    public static final int COBJ0007 = 123;
    public static final int COBJ0008 = 124;
    public static final int COBJ0009 = 125;
    public static final int COBJ0010 = 126;
    public static final int COBJ0011 = 127;
    public static final int COBJ0012 = 128;
    public static final int COBJ0013 = 129;
    public static final int COBJ0014 = 130;
    public static final int COBJ0015 = 131;
    public static final int COBJ0016 = 132;
    public static final int COBJ0017 = 133;
    public static final int COBJ0018 = 134;
    public static final int COBJ0019 = 135;
    public static final int COBJ0020 = 136;
    public static final int COBJ0021 = 137;
    public static final int COBJ0022 = 138;
    public static final int COBJ0023 = 139;
    public static final int COBJ0024 = 140;
    public static final int COBJ0025 = 141;
    public static final int COBJ0026 = 142;
    public static final int COBJ0027 = 143;
    public static final int COBJ0028 = 144;
    public static final int COBJ0029 = 145;
    public static final int COBJ0030 = 146;
    public static final int COBJ0031 = 147;
    public static final int COLDRAY = 148;
    public static final int COLDRING = 149;
    public static final int CONGRATS = 150;
    public static final int COVR0001 = 151;
    public static final int COVR0002 = 152;
    public static final int COVR0003 = 153;
    public static final int COVR0004 = 154;
    public static final int COVR0005 = 155;
    public static final int COVR0006 = 156;
    public static final int COVR0007 = 157;
    public static final int COVR0008 = 158;
    public static final int COVR0009 = 159;
    public static final int COVR0010 = 160;
    public static final int COVR0011 = 161;
    public static final int COVR0012 = 162;
    public static final int COVR0013 = 163;
    public static final int COVR0014 = 164;
    public static final int COVR0015 = 165;
    public static final int COVR0016 = 166;
    public static final int COVR0017 = 167;
    public static final int COVR0018 = 168;
    public static final int COVR0019 = 169;
    public static final int COVR0020 = 170;
    public static final int COVR0021 = 171;
    public static final int COVR0022 = 172;
    public static final int COVR0023 = 173;
    public static final int COVR0024 = 174;
    public static final int CPANBKGE = 175;
    public static final int CPANBKG = 176;
    public static final int CPANELE = 177;
    public static final int CPANEL = 178;
    public static final int CREST = 179;
    public static final int CSPANBKE = 180;
    public static final int CSPANBKG = 181;
    public static final int CSPANBTE = 182;
    public static final int CSPANBTN = 183;
    public static final int CSPANEL = 184;
    public static final int CSTLBARB = 185;
    public static final int CSTLCAPB = 186;
    public static final int CSTLCAPK = 187;
    public static final int CSTLCAPN = 188;
    public static final int CSTLCAPS = 189;
    public static final int CSTLCAPW = 190;
    public static final int CSTLCAPZ = 191;
    public static final int CSTLKNGT = 192;
    public static final int CSTLNECR = 193;
    public static final int CSTLSORC = 194;
    public static final int CSTLWRLK = 195;
    public static final int CSTLWZRD = 196;
    public static final int CTRACK00 = 197;
    public static final int CTRACK01 = 198;
    public static final int CTRACK02 = 199;
    public static final int CTRACK03 = 200;
    public static final int CTRACK04 = 201;
    public static final int CTRACK05 = 202;
    public static final int CTRACK06 = 203;
    public static final int CURSE = 204;
    public static final int CYCLOPS = 205;
    public static final int DISRRAY = 206;
    public static final int DRAGBLAK = 207;
    public static final int DRAGBONE = 208;
    public static final int DRAGGREE = 209;
    public static final int DRAGRED = 210;
    public static final int DRAGSLAY = 211;
    public static final int DROPLISL = 212;
    public static final int DROPLIST = 213;
    public static final int DRUID2 = 214;
    public static final int DRUID = 215;
    public static final int DRUIDMSL = 216;
    public static final int DUMMY = 217;
    public static final int DWARF2 = 218;
    public static final int DWARF = 219;
    public static final int ECPANEL = 220;
    public static final int EDITBTNS = 221;
    public static final int EDITOR = 222;
    public static final int EDITPANL = 223;
    public static final int EELEM = 224;
    public static final int ELECTRIC = 225;
    public static final int ELF2 = 226;
    public static final int ELF = 227;
    public static final int ELF__MSL = 228;
    public static final int ESCROLL = 229;
    public static final int ESPANBKG = 230;
    public static final int ESPANBTN = 231;
    public static final int ESPANEL = 232;
    public static final int EVIW_ALL = 233;
    public static final int EVIWDDOR = 234;
    public static final int EVIWHROS = 235;
    public static final int EVIWMINE = 236;
    public static final int EVIWPUZL = 237;
    public static final int EVIWRSRC = 238;
    public static final int EVIWRTFX = 239;
    public static final int EVIWTWNS = 240;
    public static final int EVIWWRLD = 241;
    public static final int EXPMRL = 242;
    public static final int EXTRAOVR = 243;
    public static final int FELEM = 244;
    public static final int FIREBAL2 = 245;
    public static final int FIREBALL = 246;
    public static final int FLAG32 = 247;
    public static final int FONT = 248;
    public static final int FRNG0001 = 249;
    public static final int FRNG0002 = 250;
    public static final int FRNG0003 = 251;
    public static final int FRNG0004 = 252;
    public static final int FRNG0005 = 253;
    public static final int FRNG0006 = 254;
    public static final int FRNG0007 = 255;
    public static final int FRNG0008 = 256;
    public static final int FRNG0009 = 257;
    public static final int FRNG0010 = 258;
    public static final int FRNG0011 = 259;
    public static final int FRNG0012 = 260;
    public static final int FRNG0013 = 261;
    public static final int FROTH = 262;
    public static final int GARGOYLE = 263;
    public static final int G_BFLG32 = 264;
    public static final int GENIE = 265;
    public static final int G_FLAG32 = 266;
    public static final int GHOST = 267;
    public static final int GOBLIN = 268;
    public static final int GOLEM2 = 269;
    public static final int GOLEM = 270;
    public static final int GRIFFIN = 271;
    public static final int GROUND12 = 272;
    public static final int GROUND4 = 273;
    public static final int GROUND6 = 274;
    public static final int HALFLING = 275;
    public static final int HALFLMSL = 276;
    public static final int HASTE = 277;
    public static final int HEROBKG = 278;
    public static final int HEROES = 279;
    public static final int HEROEXTE = 280;
    public static final int HEROEXTG = 281;
    public static final int HEROFL00 = 282;
    public static final int HEROFL01 = 283;
    public static final int HEROFL02 = 284;
    public static final int HEROFL03 = 285;
    public static final int HEROFL04 = 286;
    public static final int HEROFL05 = 287;
    public static final int HEROFL06 = 288;
    public static final int HEROLOGE = 289;
    public static final int HEROLOGO = 290;
    public static final int HISCORE = 291;
    public static final int HOURGLAS = 292;
    public static final int HSBKG = 293;
    public static final int HSBTNS = 294;
    public static final int HSICONS = 295;
    public static final int HYDRA = 296;
    public static final int HYPNOTIZ = 297;
    public static final int ICECLOUD = 298;
    public static final int KEEP = 299;
    public static final int KNGT32 = 300;
    public static final int LETTER12 = 301;
    public static final int LETTER4 = 302;
    public static final int LETTER6 = 303;
    public static final int LGNDXTRA = 304;
    public static final int LGNDXTRE = 305;
    public static final int LICH2 = 306;
    public static final int LICHCLOD = 307;
    public static final int LICH = 308;
    public static final int LICH_MSL = 309;
    public static final int LISTBOX = 310;
    public static final int LISTBOXS = 311;
    public static final int LOCATORE = 312;
    public static final int LOCATORS = 313;
    public static final int MAGE1 = 314;
    public static final int MAGE2 = 315;
    public static final int MAGEGLDB = 316;
    public static final int MAGEGLDK = 317;
    public static final int MAGEGLDN = 318;
    public static final int MAGEGLDS = 319;
    public static final int MAGEGLDW = 320;
    public static final int MAGEGLDZ = 321;
    public static final int MAGIC01 = 322;
    public static final int MAGIC02 = 323;
    public static final int MAGIC03 = 324;
    public static final int MAGIC04 = 325;
    public static final int MAGIC06 = 326;
    public static final int MAGIC07 = 327;
    public static final int MAGIC08 = 328;
    public static final int MANA = 329;
    public static final int MEDUSA = 330;
    public static final int METEOR = 331;
    public static final int MINICAPT = 332;
    public static final int MINIHERO = 333;
    public static final int MINILKMR = 334;
    public static final int MINIMON = 335;
    public static final int MINIPORT = 336;
    public static final int MINISS = 337;
    public static final int MINITOWN = 338;
    public static final int MINOTAU2 = 339;
    public static final int MINOTAUR = 340;
    public static final int MISC12 = 341;
    public static final int MISC4 = 342;
    public static final int MISC6 = 343;
    public static final int MOATPART = 344;
    public static final int MOATWHOL = 345;
    public static final int MOBILITY = 346;
    public static final int MONH0000 = 347;
    public static final int MONH0001 = 348;
    public static final int MONH0002 = 349;
    public static final int MONH0003 = 350;
    public static final int MONH0004 = 351;
    public static final int MONH0005 = 352;
    public static final int MONH0006 = 353;
    public static final int MONH0007 = 354;
    public static final int MONH0008 = 355;
    public static final int MONH0009 = 356;
    public static final int MONH0010 = 357;
    public static final int MONH0011 = 358;
    public static final int MONH0012 = 359;
    public static final int MONH0013 = 360;
    public static final int MONH0014 = 361;
    public static final int MONH0015 = 362;
    public static final int MONH0016 = 363;
    public static final int MONH0017 = 364;
    public static final int MONH0018 = 365;
    public static final int MONH0019 = 366;
    public static final int MONH0020 = 367;
    public static final int MONH0021 = 368;
    public static final int MONH0022 = 369;
    public static final int MONH0023 = 370;
    public static final int MONH0024 = 371;
    public static final int MONH0025 = 372;
    public static final int MONH0026 = 373;
    public static final int MONH0027 = 374;
    public static final int MONH0028 = 375;
    public static final int MONH0029 = 376;
    public static final int MONH0030 = 377;
    public static final int MONH0031 = 378;
    public static final int MONH0032 = 379;
    public static final int MONH0033 = 380;
    public static final int MONH0034 = 381;
    public static final int MONH0035 = 382;
    public static final int MONH0036 = 383;
    public static final int MONH0037 = 384;
    public static final int MONH0038 = 385;
    public static final int MONH0039 = 386;
    public static final int MONH0040 = 387;
    public static final int MONH0041 = 388;
    public static final int MONH0042 = 389;
    public static final int MONH0043 = 390;
    public static final int MONH0044 = 391;
    public static final int MONH0045 = 392;
    public static final int MONH0046 = 393;
    public static final int MONH0047 = 394;
    public static final int MONH0048 = 395;
    public static final int MONH0049 = 396;
    public static final int MONH0050 = 397;
    public static final int MONH0051 = 398;
    public static final int MONH0052 = 399;
    public static final int MONH0053 = 400;
    public static final int MONH0054 = 401;
    public static final int MONH0055 = 402;
    public static final int MONH0056 = 403;
    public static final int MONH0057 = 404;
    public static final int MONH0058 = 405;
    public static final int MONH0059 = 406;
    public static final int MONH0060 = 407;
    public static final int MONH0061 = 408;
    public static final int MONH0062 = 409;
    public static final int MONH0063 = 410;
    public static final int MONH0064 = 411;
    public static final int MONH0065 = 412;
    public static final int MONS32 = 413;
    public static final int MORALEB = 414;
    public static final int MORALEG = 415;
    public static final int MTNCRCK = 416;
    public static final int MTNDIRT = 417;
    public static final int MTNDSRT = 418;
    public static final int MTNGRAS = 419;
    public static final int MTNLAVA = 420;
    public static final int MTNMULT = 421;
    public static final int MTNSNOW = 422;
    public static final int MTNSWMP = 423;
    public static final int MUMMY2 = 424;
    public static final int MUMMYW = 425;
    public static final int NECR32 = 426;
    public static final int NETBOX = 427;
    public static final int NGEXTRA = 428;
    public static final int NGHSBKG = 429;
    public static final int NGMPBKG = 430;
    public static final int NGSPBKG = 431;
    public static final int NOMAD = 432;
    public static final int O_BFLG32 = 433;
    public static final int OBJNARTI = 434;
    public static final int OBJNCRCK = 435;
    public static final int OBJNDIRT = 436;
    public static final int OBJNDSRT = 437;
    public static final int OBJNGRA2 = 438;
    public static final int OBJNGRAS = 439;
    public static final int OBJNHAUN = 440;
    public static final int OBJNLAV2 = 441;
    public static final int OBJNLAV3 = 442;
    public static final int OBJNLAVA = 443;
    public static final int OBJNMUL2 = 444;
    public static final int OBJNMULT = 445;
    public static final int OBJNRSRC = 446;
    public static final int OBJNSNOW = 447;
    public static final int OBJNSWMP = 448;
    public static final int OBJNTOWN = 449;
    public static final int OBJNTWBA = 450;
    public static final int OBJNTWRD = 451;
    public static final int OBJNTWSH = 452;
    public static final int OBJNWAT2 = 453;
    public static final int OBJNWATR = 454;
    public static final int OBJNXTRA = 455;
    public static final int OBJPALET = 456;
    public static final int O_FLAG32 = 457;
    public static final int OGRE2 = 458;
    public static final int OGRE = 459;
    public static final int ORC2 = 460;
    public static final int ORC = 461;
    public static final int ORC__MSL = 462;
    public static final int OVERBACK = 463;
    public static final int OVERLAY = 464;
    public static final int OVERVIEW = 465;
    public static final int PALADIN2 = 466;
    public static final int PALADIN = 467;
    public static final int PARALYZE = 468;
    public static final int P_BFLG32 = 469;
    public static final int PEASANT = 470;
    public static final int P_FLAG32 = 471;
    public static final int PHOENIX = 472;
    public static final int PHYSICAL = 473;
    public static final int PIKEMAN2 = 474;
    public static final int PIKEMAN = 475;
    public static final int PORT0000 = 476;
    public static final int PORT0001 = 477;
    public static final int PORT0002 = 478;
    public static final int PORT0003 = 479;
    public static final int PORT0004 = 480;
    public static final int PORT0005 = 481;
    public static final int PORT0006 = 482;
    public static final int PORT0007 = 483;
    public static final int PORT0008 = 484;
    public static final int PORT0009 = 485;
    public static final int PORT0010 = 486;
    public static final int PORT0011 = 487;
    public static final int PORT0012 = 488;
    public static final int PORT0013 = 489;
    public static final int PORT0014 = 490;
    public static final int PORT0015 = 491;
    public static final int PORT0016 = 492;
    public static final int PORT0017 = 493;
    public static final int PORT0018 = 494;
    public static final int PORT0019 = 495;
    public static final int PORT0020 = 496;
    public static final int PORT0021 = 497;
    public static final int PORT0022 = 498;
    public static final int PORT0023 = 499;
    public static final int PORT0024 = 500;
    public static final int PORT0025 = 501;
    public static final int PORT0026 = 502;
    public static final int PORT0027 = 503;
    public static final int PORT0028 = 504;
    public static final int PORT0029 = 505;
    public static final int PORT0030 = 506;
    public static final int PORT0031 = 507;
    public static final int PORT0032 = 508;
    public static final int PORT0033 = 509;
    public static final int PORT0034 = 510;
    public static final int PORT0035 = 511;
    public static final int PORT0036 = 512;
    public static final int PORT0037 = 513;
    public static final int PORT0038 = 514;
    public static final int PORT0039 = 515;
    public static final int PORT0040 = 516;
    public static final int PORT0041 = 517;
    public static final int PORT0042 = 518;
    public static final int PORT0043 = 519;
    public static final int PORT0044 = 520;
    public static final int PORT0045 = 521;
    public static final int PORT0046 = 522;
    public static final int PORT0047 = 523;
    public static final int PORT0048 = 524;
    public static final int PORT0049 = 525;
    public static final int PORT0050 = 526;
    public static final int PORT0051 = 527;
    public static final int PORT0052 = 528;
    public static final int PORT0053 = 529;
    public static final int PORT0054 = 530;
    public static final int PORT0055 = 531;
    public static final int PORT0056 = 532;
    public static final int PORT0057 = 533;
    public static final int PORT0058 = 534;
    public static final int PORT0059 = 535;
    public static final int PORT0060 = 536;
    public static final int PORT0061 = 537;
    public static final int PORT0062 = 538;
    public static final int PORT0063 = 539;
    public static final int PORT0064 = 540;
    public static final int PORT0065 = 541;
    public static final int PORT0066 = 542;
    public static final int PORT0067 = 543;
    public static final int PORT0068 = 544;
    public static final int PORT0069 = 545;
    public static final int PORT0070 = 546;
    public static final int PORT0090 = 547;
    public static final int PORT0091 = 548;
    public static final int PORT0092 = 549;
    public static final int PORT0093 = 550;
    public static final int PORT0094 = 551;
    public static final int PORT0095 = 552;
    public static final int PORTCFLG = 553;
    public static final int PORTMEDI = 554;
    public static final int PORTXTRA = 555;
    public static final int PRIMSKIL = 556;
    public static final int PUZZLE = 557;
    public static final int QWIKHERO = 558;
    public static final int QWIKINFO = 559;
    public static final int QWIKTOWN = 560;
    public static final int RADAR = 561;
    public static final int R_BFLG32 = 562;
    public static final int RECR2BKG = 563;
    public static final int RECRBKG = 564;
    public static final int RECRUIT = 565;
    public static final int REDBACK = 566;
    public static final int REDDEATH = 567;
    public static final int REDFIRE = 568;
    public static final int REQBKG = 569;
    public static final int REQSBKG = 570;
    public static final int REQUEST = 571;
    public static final int REQUESTS = 572;
    public static final int RESOURCE = 573;
    public static final int RESSMALL = 574;
    public static final int R_FLAG32 = 575;
    public static final int ROAD = 576;
    public static final int ROC = 577;
    public static final int ROGUE = 578;
    public static final int ROUTE = 579;
    public static final int SCENIBKG = 580;
    public static final int SCROLL2 = 581;
    public static final int SCROLLCN = 582;
    public static final int SCROLLE = 583;
    public static final int SCROLL = 584;
    public static final int SECSKILL = 585;
    public static final int SHADOW32 = 586;
    public static final int SHIELD = 587;
    public static final int SHNGANIM = 588;
    public static final int SKELETON = 589;
    public static final int SMALCLOD = 590;
    public static final int SMALFONT = 591;
    public static final int SMALLBAR = 592;
    public static final int SORC32 = 593;
    public static final int SPANBKGE = 594;
    public static final int SPANBKG = 595;
    public static final int SPANBTNE = 596;
    public static final int SPANBTN = 597;
    public static final int SPANEL = 598;
    public static final int SPARKS = 599;
    public static final int SPELCO = 600;
    public static final int SPELLINF = 601;
    public static final int SPELLINL = 602;
    public static final int SPELLS = 603;
    public static final int SPRITE = 604;
    public static final int STELSKIN = 605;
    public static final int STONBACK = 606;
    public static final int STONBAKE = 607;
    public static final int STONEBAK = 608;
    public static final int STONEBK2 = 609;
    public static final int STONSKIN = 610;
    public static final int STORM = 611;
    public static final int STREAM = 612;
    public static final int STRIP = 613;
    public static final int SUNMOONE = 614;
    public static final int SUNMOON = 615;
    public static final int SURDRBKE = 616;
    public static final int SURDRBKG = 617;
    public static final int SURRENDE = 618;
    public static final int SURRENDR = 619;
    public static final int SWAPBTN = 620;
    public static final int SWAPWIN = 621;
    public static final int SWORDSM2 = 622;
    public static final int SWORDSMN = 623;
    public static final int SYSTEME = 624;
    public static final int SYSTEM = 625;
    public static final int TAVWIN = 626;
    public static final int TENT = 627;
    public static final int TERRAINS = 628;
    public static final int TEXTBACK = 629;
    public static final int TEXTBAK2 = 630;
    public static final int TEXTBAR = 631;
    public static final int TITANBLA = 632;
    public static final int TITANBLU = 633;
    public static final int TITANMSL = 634;
    public static final int TOWNBKG0 = 635;
    public static final int TOWNBKG1 = 636;
    public static final int TOWNBKG2 = 637;
    public static final int TOWNBKG3 = 638;
    public static final int TOWNBKG4 = 639;
    public static final int TOWNBKG5 = 640;
    public static final int TOWNFIX = 641;
    public static final int TOWNNAME = 642;
    public static final int TOWNWIND = 643;
    public static final int TRADPOSE = 644;
    public static final int TRADPOST = 645;
    public static final int TREASURY = 646;
    public static final int TREDECI = 647;
    public static final int TREEVIL = 648;
    public static final int TREFALL = 649;
    public static final int TREFIR = 650;
    public static final int TREJNGL = 651;
    public static final int TRESNOW = 652;
    public static final int TROLL2 = 653;
    public static final int TROLL = 654;
    public static final int TROLLMSL = 655;
    public static final int TWNBBOAT = 656;
    public static final int TWNBCAPT = 657;
    public static final int TWNBCSTL = 658;
    public static final int TWNBDOCK = 659;
    public static final int TWNBDW_0 = 660;
    public static final int TWNBDW_1 = 661;
    public static final int TWNBDW_2 = 662;
    public static final int TWNBDW_3 = 663;
    public static final int TWNBDW_4 = 664;
    public static final int TWNBDW_5 = 665;
    public static final int TWNBEXT0 = 666;
    public static final int TWNBEXT1 = 667;
    public static final int TWNBEXT2 = 668;
    public static final int TWNBEXT3 = 669;
    public static final int TWNBLTUR = 670;
    public static final int TWNBMAGE = 671;
    public static final int TWNBMARK = 672;
    public static final int TWNBMOAT = 673;
    public static final int TWNBRTUR = 674;
    public static final int TWNBSPEC = 675;
    public static final int TWNBSTAT = 676;
    public static final int TWNBTENT = 677;
    public static final int TWNBTHIE = 678;
    public static final int TWNBTVRN = 679;
    public static final int TWNBUP_1 = 680;
    public static final int TWNBUP_3 = 681;
    public static final int TWNBUP_4 = 682;
    public static final int TWNBWEL2 = 683;
    public static final int TWNBWELL = 684;
    public static final int TWNKBOAT = 685;
    public static final int TWNKCAPT = 686;
    public static final int TWNKCSTL = 687;
    public static final int TWNKDOCK = 688;
    public static final int TWNKDW_0 = 689;
    public static final int TWNKDW_1 = 690;
    public static final int TWNKDW_2 = 691;
    public static final int TWNKDW_3 = 692;
    public static final int TWNKDW_4 = 693;
    public static final int TWNKDW_5 = 694;
    public static final int TWNKEXT0 = 695;
    public static final int TWNKEXT1 = 696;
    public static final int TWNKEXT2 = 697;
    public static final int TWNKLTUR = 698;
    public static final int TWNKMAGE = 699;
    public static final int TWNKMARK = 700;
    public static final int TWNKMOAT = 701;
    public static final int TWNKRTUR = 702;
    public static final int TWNKSPEC = 703;
    public static final int TWNKSTAT = 704;
    public static final int TWNKTENT = 705;
    public static final int TWNKTHIE = 706;
    public static final int TWNKTVRN = 707;
    public static final int TWNKUP_1 = 708;
    public static final int TWNKUP_2 = 709;
    public static final int TWNKUP_3 = 710;
    public static final int TWNKUP_4 = 711;
    public static final int TWNKUP_5 = 712;
    public static final int TWNKWEL2 = 713;
    public static final int TWNKWELL = 714;
    public static final int TWNNBOAT = 715;
    public static final int TWNNCAPT = 716;
    public static final int TWNNCSTL = 717;
    public static final int TWNNDOCK = 718;
    public static final int TWNNDW_0 = 719;
    public static final int TWNNDW_1 = 720;
    public static final int TWNNDW_2 = 721;
    public static final int TWNNDW_3 = 722;
    public static final int TWNNDW_4 = 723;
    public static final int TWNNDW_5 = 724;
    public static final int TWNNEXT0 = 725;
    public static final int TWNNLTUR = 726;
    public static final int TWNNMAGE = 727;
    public static final int TWNNMARK = 728;
    public static final int TWNNMOAT = 729;
    public static final int TWNNRTUR = 730;
    public static final int TWNNSPEC = 731;
    public static final int TWNNSTAT = 732;
    public static final int TWNNTENT = 733;
    public static final int TWNNTHIE = 734;
    public static final int TWNNTVRN = 735;
    public static final int TWNNUP_1 = 736;
    public static final int TWNNUP_2 = 737;
    public static final int TWNNUP_3 = 738;
    public static final int TWNNUP_4 = 739;
    public static final int TWNNWEL2 = 740;
    public static final int TWNNWELL = 741;
    public static final int TWNSBOAT = 742;
    public static final int TWNSCAPT = 743;
    public static final int TWNSCSTL = 744;
    public static final int TWNSDOCK = 745;
    public static final int TWNSDW_0 = 746;
    public static final int TWNSDW_1 = 747;
    public static final int TWNSDW_2 = 748;
    public static final int TWNSDW_3 = 749;
    public static final int TWNSDW_4 = 750;
    public static final int TWNSDW_5 = 751;
    public static final int TWNSEXT0 = 752;
    public static final int TWNSEXT1 = 753;
    public static final int TWNSLTUR = 754;
    public static final int TWNSMAGE = 755;
    public static final int TWNSMARK = 756;
    public static final int TWNSMOAT = 757;
    public static final int TWNSRTUR = 758;
    public static final int TWNSSPEC = 759;
    public static final int TWNSSTAT = 760;
    public static final int TWNSTENT = 761;
    public static final int TWNSTHIE = 762;
    public static final int TWNSTVRN = 763;
    public static final int TWNSUP_1 = 764;
    public static final int TWNSUP_2 = 765;
    public static final int TWNSUP_3 = 766;
    public static final int TWNSWEL2 = 767;
    public static final int TWNSWELL = 768;
    public static final int TWNWBOAT = 769;
    public static final int TWNWCAPT = 770;
    public static final int TWNWCSTL = 771;
    public static final int TWNWDOCK = 772;
    public static final int TWNWDW_0 = 773;
    public static final int TWNWDW_1 = 774;
    public static final int TWNWDW_2 = 775;
    public static final int TWNWDW_3 = 776;
    public static final int TWNWDW_4 = 777;
    public static final int TWNWDW_5 = 778;
    public static final int TWNWEXT0 = 779;
    public static final int TWNWLTUR = 780;
    public static final int TWNWMAGE = 781;
    public static final int TWNWMARK = 782;
    public static final int TWNWMOAT = 783;
    public static final int TWNWRTUR = 784;
    public static final int TWNWSPEC = 785;
    public static final int TWNWSTAT = 786;
    public static final int TWNWTENT = 787;
    public static final int TWNWTHIE = 788;
    public static final int TWNWTVRN = 789;
    public static final int TWNWUP_3 = 790;
    public static final int TWNWUP5B = 791;
    public static final int TWNWUP_5 = 792;
    public static final int TWNWWEL2 = 793;
    public static final int TWNWWELL = 794;
    public static final int TWNZBOAT = 795;
    public static final int TWNZCAPT = 796;
    public static final int TWNZCSTL = 797;
    public static final int TWNZDOCK = 798;
    public static final int TWNZDW_0 = 799;
    public static final int TWNZDW_1 = 800;
    public static final int TWNZDW_2 = 801;
    public static final int TWNZDW_3 = 802;
    public static final int TWNZDW_4 = 803;
    public static final int TWNZDW_5 = 804;
    public static final int TWNZEXT0 = 805;
    public static final int TWNZLTUR = 806;
    public static final int TWNZMAGE = 807;
    public static final int TWNZMARK = 808;
    public static final int TWNZMOAT = 809;
    public static final int TWNZRTUR = 810;
    public static final int TWNZSPEC = 811;
    public static final int TWNZSTAT = 812;
    public static final int TWNZTENT = 813;
    public static final int TWNZTHIE = 814;
    public static final int TWNZTVRN = 815;
    public static final int TWNZUP_2 = 816;
    public static final int TWNZUP_4 = 817;
    public static final int TWNZUP_5 = 818;
    public static final int TWNZWEL2 = 819;
    public static final int TWNZWELL = 820;
    public static final int UNICORN = 821;
    public static final int VAMPIRE2 = 822;
    public static final int VAMPIRE = 823;
    public static final int VGENBKGE = 824;
    public static final int VGENBKG = 825;
    public static final int VIEW_ALL = 826;
    public static final int VIEWARME = 827;
    public static final int VIEWARMY = 828;
    public static final int VIEWARSM = 829;
    public static final int VIEWDDOR = 830;
    public static final int VIEWGEN = 831;
    public static final int VIEWHROS = 832;
    public static final int VIEWMINE = 833;
    public static final int VIEWPUZL = 834;
    public static final int VIEWRSRC = 835;
    public static final int VIEWRTFX = 836;
    public static final int VIEWTWNS = 837;
    public static final int VIEWWRLD = 838;
    public static final int VWFLAG12 = 839;
    public static final int VWFLAG4 = 840;
    public static final int VWFLAG6 = 841;
    public static final int WELEM = 842;
    public static final int WELLBKG = 843;
    public static final int WELLXTRA = 844;
    public static final int WINCMBBE = 845;
    public static final int WINCMBTB = 846;
    public static final int WINCMBT = 847;
    public static final int WINLOSEB = 848;
    public static final int WINLOSEE = 849;
    public static final int WINLOSE = 850;
    public static final int WOLF = 851;
    public static final int WRLK32 = 852;
    public static final int WZRD32 = 853;
    public static final int X_LOC1 = 854;
    public static final int X_LOC2 = 855;
    public static final int X_LOC3 = 856;
    public static final int XPRIMARY = 857;
    public static final int Y_BFLG32 = 858;
    public static final int Y_FLAG32 = 859;
    public static final int YINYANG = 860;
    public static final int ZOMBIE2 = 861;
    public static final int ZOMBIE = 862;
    public static final int ROUTERED = 863;
    public static final int TELEPORT1 = 864;
    public static final int TELEPORT2 = 865;
    public static final int TELEPORT3 = 866;
    public static final int FOUNTAIN = 867;
    public static final int TREASURE = 868;
    public static final int YELLOW_FONT = 869;
    public static final int YELLOW_SMALFONT = 870;
    public static final int BATTLESKIP = 871;
    public static final int BATTLEWAIT = 872;
    public static final int BATTLEAUTO = 873;
    public static final int BATTLESETS = 874;
    public static final int BUYMAX = 875;
    public static final int BTNCONFIG = 876;
    public static final int BTNBATTLEONLY = 877;
    public static final int BOAT12 = 878;
    public static final int BTNGIFT = 879;
    public static final int BTNMIN = 880;
    public static final int CSLMARKER = 881;
    public static final int LASTICN = 882;

    static Map<String, Integer> _names = new HashMap<>();
    static Map<Integer, String> _toName = new HashMap<>();

    static {
        FileUtils.FillEnumValues(IcnKind.class, _names, _toName);
    }

    public static String GetString(int icn) {
        if (icn == UNKNOWN)
            return "Unknown";
        var actualName = _toName.get(icn);
        return actualName + ".ICN";
    }

    public static int AnimationFrame(int icn, int start, int ticket, boolean quantity) {
        switch (icn) {
            case TELEPORT1:
            case TELEPORT2:
            case TELEPORT3:
                return start + ticket % 8;

            case FOUNTAIN:
            case TREASURE:
                return start + ticket % 2;

            case TWNBBOAT:
            case TWNKBOAT:
            case TWNNBOAT:
            case TWNSBOAT:
            case TWNWBOAT:
            case TWNZBOAT:
                return 1 + ticket % 9;

            case CMBTCAPB:
            case CMBTCAPK:
            case CMBTCAPN:
            case CMBTCAPS:
            case CMBTCAPW:
            case CMBTCAPZ:
                return 1 + ticket % 10;

            case CMBTHROB:
                return 1 + ticket % 18;
            case CMBTHROK:
                return 1 + ticket % 19;
            case CMBTHRON:
                return 1 + ticket % 19;
            case CMBTHROS:
                return 1 + ticket % 16;
            case CMBTHROW:
                return 1 + ticket % 16;
            case CMBTHROZ:
                return 1 + ticket % 18;

            case HEROFL00:
            case HEROFL01:
            case HEROFL02:
            case HEROFL03:
            case HEROFL04:
            case HEROFL05:
                return ticket % 5;

            case TWNBDOCK:
            case TWNKDOCK:
            case TWNNDOCK:
            case TWNSDOCK:
            case TWNWDOCK:
            case TWNZDOCK:

            case TWNBEXT0:
            case TWNKEXT0:
            case TWNNEXT0:
            case TWNSEXT0:
            case TWNWEXT0:
            case TWNZEXT0:

            case TWNBCAPT:
            case TWNBDW_3:
            case TWNBDW_4:
            case TWNBDW_5:
            case TWNBEXT1:
            case TWNBMOAT:
            case TWNBUP_3:
            case TWNBUP_4:
            case TWNKCSTL:
            case TWNKDW_0:
            case TWNKLTUR:
            case TWNKRTUR:
            case TWNKTHIE:
            case TWNKTVRN:
            case TWNNCSTL:
            case TWNNDW_2:
            case TWNNUP_2:
            case TWNSCAPT:
            case TWNSCSTL:
            case TWNSDW_0:
            case TWNSDW_1:
            case TWNSEXT1:
            case TWNSTHIE:
            case TWNSTVRN:
            case TWNSUP_1:
            case TWNSWEL2:
            case TWNWCAPT:
            case TWNWCSTL:
            case TWNWMOAT:
            case TWNZCSTL:
            case TWNZDW_0:
            case TWNZDW_2:
            case TWNZTHIE:
            case TWNZUP_2:
                return 1 + ticket % 5;

            case TWNBCSTL:
            case TWNKDW_2:
            case TWNKUP_2:
            case TWNNDW_5:
            case TWNNWEL2:
            case TWNWDW_0:
            case TWNWWEL2:
            case TWNZTVRN:
                return 1 + ticket % 6;

            case TWNKDW_4:
            case TWNKUP_4:
                return 1 + ticket % 7;

            case TAVWIN:
                return 2 + ticket % 20;

            case CMBTLOS1:
                return 1 + ticket % 30;
            case CMBTLOS2:
                return 1 + ticket % 29;
            case CMBTLOS3:
                return 1 + ticket % 22;
            case CMBTFLE1:
                return 1 + ticket % 43;
            case CMBTFLE2:
                return 1 + ticket % 26;
            case CMBTFLE3:
                return 1 + ticket % 25;
            case CMBTSURR:
                return 1 + ticket % 20;

            case WINCMBT:
                return 1 + ticket % 20;

            case MINIMON:
                return start + 1 + ticket % 6;

            case TWNNMAGE:
                return start + 1 + ticket % 5;

            case TWNBMAGE:
                return 4 == start ? start + 1 + ticket % 8 : 0;

            case SHNGANIM:
                return 1 + ticket % 39;

            case BTNSHNGL:
                return start + ticket % 4;

            case OBJNHAUN:
                return ticket % 15;

            case OBJNWATR:

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

            case OBJNWAT2:

                switch (start) {
                    // sail broken ship (left)
                    case 0x03:
                    case 0x0C:
                        return start + ticket % 6 + 1;

                    default:
                        return 0;
                }

            case OBJNCRCK:

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

            case OBJNDIRT:

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

            case OBJNDSRT:

                switch (start) {
                    // campfire
                    case 0x36:
                    case 0x3D:
                        return start + ticket % 6 + 1;

                    default:
                        return 0;
                }

            case OBJNGRA2:

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

            case OBJNLAV2:

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

            case OBJNLAV3:

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

            case OBJNLAVA:

                switch (start) {
                    // shadow of lava
                    case 0x4E:
                    case 0x58:
                    case 0x62:
                        return start + ticket % 9 + 1;

                    default:
                        return 0;
                }

            case OBJNMUL2:

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

            case OBJNMULT:

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

            case OBJNSNOW:

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

            case OBJNSWMP:

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
            case X_LOC1:

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
                break;

            // extra objects for loyalty version
            case X_LOC2:

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
            case X_LOC3:

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
                return 0;
        }
        return 0;
    }
}
