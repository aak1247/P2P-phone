package com.aak1247.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author  aak12 on 2017/4/20.
 */
public class UDPSender {
    private DatagramSocket datagramSocket;
    public UDPSender(){
        try {
            datagramSocket = new DatagramSocket();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean send(String host,byte[] senddata){
        Utils utils = new Utils();
        try {
//            int port = Integer.parseInt(utils.getPhonePort());
            InetAddress inetAddress = InetAddress.getByName(host);
//            DatagramPacket datagramPacket = new DatagramPacket(senddata,senddata.length,inetAddress,port);
            DatagramPacket datagramPacket = new DatagramPacket(senddata,senddata.length,inetAddress,8001);
            datagramSocket.send(datagramPacket);
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }
}
