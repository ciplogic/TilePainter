package hellofx.fheroes2.common.music;


import javax.sound.midi.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class MidPlay {
    //--
    private Sequencer sequencer;
    private MidiDevice.Info[] infos;
    private MidiDevice device;
    private Sequence seq;

    //--コンストラクタ
    public MidPlay() {
    }

    //--シーケンサを準備
    public void prepare(boolean stat) {
        try {
            sequencer = MidiSystem.getSequencer(stat);
            sequencer.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--デバイスリストを返す
    public String[] getDeviceList() {
        infos = MidiSystem.getMidiDeviceInfo();
        String[] rst = new String[infos.length];
        for (int i = 0; i < rst.length; i++) {
            rst[i] = "[" + i + "]" + infos[i].getName();
        }
        return rst;
    }

    //--番号を指定してシーケンサにデバイスを充て、シーケンサとデバイスをつなぐ
    public void setDevice(int id) {
        if (infos == null) {
            infos = MidiSystem.getMidiDeviceInfo();
        }
        try {
            device = MidiSystem.getMidiDevice(infos[id]);
            if (!device.isOpen()) device.open();

            Receiver rc = device.getReceiver();
            sequencer.getTransmitter().setReceiver(rc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--wavに変換
    public void convWav(File inputMid, File saveto) {
        try {
            //--audioinputstreamとmidiファイルを繋げるだけ…?
            AudioInputStream stream = AudioSystem.getAudioInputStream(inputMid);
            //--AudioSystem.writeで書き込み
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, saveto);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //--ファイル読み込み
    public void load(File file) {
        try {
            seq = MidiSystem.getSequence(file);
            sequencer.setSequence(seq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--再生
    public void play() {
        sequencer.start();
    }

}

