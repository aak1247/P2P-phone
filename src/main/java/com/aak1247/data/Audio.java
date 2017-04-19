package com.aak1247.data;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * @author  aak12 on 2017/4/20.
 */
public class Audio {
    private AudioFormat audioFormat = new AudioFormat(8000,8,2,true,true);
    public void record() throws Exception{
        DataLine.Info dateLineInfo = new DataLine.Info(TargetDataLine.class,audioFormat);
        TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(dateLineInfo);
        targetDataLine.open(audioFormat,targetDataLine.getBufferSize());
        targetDataLine.start();
    }
}
