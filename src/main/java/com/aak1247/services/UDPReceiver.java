package com.aak1247.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author aak12 on 2017/4/20.
 */
public class UDPReceiver {
    private DatagramSocket datagramSocket;
    public UDPReceiver(){
        try {
//            Utils utils = new Utils();
//            this.datagramSocket = new DatagramSocket(Integer.parseInt(utils.getPhonePort()));
            this.datagramSocket = new DatagramSocket(8001);
            System.out.println("udp socket created successfully");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public byte[] receive(){
        try {
            byte[] recvBuf = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(recvBuf,recvBuf.length);
            datagramSocket.receive(datagramPacket);
            return recvBuf;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
