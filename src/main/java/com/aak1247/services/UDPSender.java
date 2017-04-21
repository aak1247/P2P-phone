package com.aak1247.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author  aak12 on 2017/4/20.
 */
public class UDPSender {
    public boolean send(String host,byte[] senddata){
        Utils utils = new Utils();

        try {
            int port = Integer.parseInt(utils.getPhonePort());
            DatagramSocket datagramSocket = new DatagramSocket(port);
            InetAddress inetAddress = InetAddress.getByName(host);
            DatagramPacket datagramPacket = new DatagramPacket(senddata,0,inetAddress,port);
            datagramSocket.send(datagramPacket);
            datagramSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }
}
