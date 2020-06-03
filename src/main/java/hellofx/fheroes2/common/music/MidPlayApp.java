package hellofx.fheroes2.common.music;

import java.io.File;

public class MidPlayApp {

    public static void main(String[] args) {

        MidPlay mp = new MidPlay();

        //--シーケンサ準備
        mp.prepare(false); //ここでfalseにすると、デフォルトシーケンサに接続しなくなる

        //--デバイス一覧を取得
        String[] dlist = mp.getDeviceList();

        mp.setDevice(0);

        //--読み込み、再生
        mp.load(new File("./out.mid"));
        // mp.play();

        //--midiファイルをwavに変換
        mp.convWav(new File("./out.mid"), new File("./conv.wav"));
    }
}
