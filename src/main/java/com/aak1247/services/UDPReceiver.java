package com.aak1247.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author aak12 on 2017/4/20.
 */
public class UDPReceiver {

    public byte[] receive(){
        Utils utils = new Utils();

        try {
            DatagramSocket datagramSocket = new DatagramSocket(Integer.parseInt(utils.getPhonePort()));
            byte[] recvBuf = new byte[1000];
            DatagramPacket datagramPacket = new DatagramPacket(recvBuf,0,recvBuf.length);
            datagramSocket.receive(datagramPacket);
            return recvBuf;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
