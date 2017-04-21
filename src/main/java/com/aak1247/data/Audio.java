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

    public static void main(String args[]) {
        Audio audio = new Audio();
        try {
            audio.record(100000);
            audio.play(audio.getBuffer());
        } catch (LineUnavailableException | IOException e) {
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
        DataLine.Info dateLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(dateLineInfo);
        targetDataLine.open(audioFormat, targetDataLine.getBufferSize());
        targetDataLine.start();
        buffer = new byte[bufferSize];
        return targetDataLine.read(buffer, 0, bufferSize);
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
        DataLine.Info dateLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dateLineInfo);
        sourceDataLine.open(audioFormat, sourceDataLine.getBufferSize());
        sourceDataLine.start();
        InputStream inputStream = new ByteArrayInputStream(bufferToPlay);
        AudioInputStream audioInputStream = new AudioInputStream(inputStream, audioFormat, bufferToPlay.length / audioFormat.getFrameSize());
        int count = 0;

        byte[] tempBuffer = new byte[512];
        while (count != -1) {
            count = audioInputStream.read(tempBuffer, 0, tempBuffer.length);
            if (count >= 0) {
                sourceDataLine.write(bufferToPlay, 0, bufferToPlay.length);

            }
            sourceDataLine.drain();
            sourceDataLine.close();
        }
    }

    public void flush(){
        this.buffer = null;
    }
}
