package com.aak1247.data;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author aak12 on 2017/4/20.
 */
public class Audio {
    private AudioFormat audioFormat = new AudioFormat(8000, 8, 2, true, true);
    private byte[] buffer;
    //    private DataLine.Info dateLineInfo;
    private TargetDataLine targetDataLine;
    private SourceDataLine sourceDataLine;

    public static void main(String args[]) {
        Audio audio = new Audio();
        try {
            audio.initPlayer();
            audio.initRecorder();
            audio.record(100000);
            audio.play(audio.getBuffer());
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void initRecorder(){
        DataLine.Info targetDataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(targetDataLineInfo);
            targetDataLine.open(audioFormat, targetDataLine.getBufferSize());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initPlayer(){
        DataLine.Info sourceDataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(sourceDataLineInfo);


            sourceDataLine.open(audioFormat, sourceDataLine.getBufferSize());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 录音
     *
     * @param bufferSize 要录的音频长度
     * @throws LineUnavailableException 占用时发生，暂不处理
     *                                  仅录音，不发送
     */
    public int record(int bufferSize) throws LineUnavailableException {
        targetDataLine.start();
        buffer = new byte[bufferSize];
        int result = targetDataLine.read(buffer, 0, bufferSize);
//        targetDataLine.close();
        return result;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    /**
     * 播放声音
     *
     * @param bufferToPlay 用于播放的声音源数组
     * @throws LineUnavailableException 当占用时发生异常
     * @throws IOException              不知道什么时候出来
     */
    public void play(byte[] bufferToPlay) throws LineUnavailableException, IOException {
        sourceDataLine.start();
        sourceDataLine.write(bufferToPlay, 0, bufferToPlay.length);

//            }
        sourceDataLine.drain();
//            sourceDataLine.close();
//        }
    }

    public void flush(){
        this.buffer = null;
    }

    public void close(){
        if (sourceDataLine!=null)this.sourceDataLine.close();
        if (targetDataLine!=null)this.targetDataLine.close();
    }
}
