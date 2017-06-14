package com.aak1247.main;

import com.aak1247.data.Audio;
import com.aak1247.services.*;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author aak12 on 2017/4/19.
 */
public class Call  {
    /*
    使用tcp建立连接，比对身份（无实时性要求，但需要可靠性）
    使用udp进行通信，循环发送数据包
     */
    public void call() throws Exception{
        TCPClient tcpClient = new TCPClient();
        Utils utils = new Utils();
        Audio audio = new Audio();
        UDPReceiver udpReceiver = new UDPReceiver();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入要拨打的用户名或地址：");
        String user = bufferedReader.readLine();
        String host = null;
        RegularExpression regularExpression = new RegularExpression("^((\\d){1,3}.){3}(\\d){1,3}$");
        if ((host = utils.getHost(user)) == null){
            if(regularExpression.matches(user)){
                host = user;
            }
        }
        if (host == null) System.out.println("输入错误");
        tcpClient.connect(host,Integer.parseInt(utils.getPort()));
        UDPSender udpSender = new UDPSender();
        System.out.println("是否继续(y/n):");
        String answer = bufferedReader.readLine();
        if (answer.equalsIgnoreCase("n")){
            System.out.println("连接关闭");
            return;
        }else if (!answer.equalsIgnoreCase("y")){
            System.out.println("输入异常");
        }
        call(host);
//        System.out.println("通话开始，按q结束通话");
//        final String trueHost = host;
//        Thread say = new Thread(()->{
//            try{
//                while (!Thread.currentThread().isInterrupted()){
//                    audio.record(10);
//                    byte[] sound = audio.getBuffer();
//                    udpSender.send(trueHost,sound);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        });
//        Thread listen = new Thread(()->{
//            while (!Thread.currentThread().isInterrupted()){
//                try {
//                    byte[] sound = udpReceiver.receive();
//                    audio.play(sound);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//        say.start();
//        listen.start();
//        while (!bufferedReader.readLine().equalsIgnoreCase("q"));
//        say.interrupt();
//        listen.interrupt();
//        System.out.println("通话结束");
        if (regularExpression.matches(host)){
            System.out.println("是否保存联系人？(y/n)");
            if(bufferedReader.readLine().equalsIgnoreCase("y")){
                System.out.println("请输入联系人姓名:");
                String name = bufferedReader.readLine();
                utils.getProperties().setProperty("host."+name,host);
                System.out.println("成功保存。");
            }
        }
    }

    public void startup()throws Exception{
        TCPServer tcpServer = new TCPServer();
        Thread waitForCall = new Thread(()->{
            String host = tcpServer.start();
            try {
                call(host);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        waitForCall.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("按C发起通话");
        while (!bufferedReader.readLine().equalsIgnoreCase("C"));
        call();
    }

    public void call(final String host) throws Exception{
        UDPSender udpSender = new UDPSender();
        UDPReceiver udpReceiver = new UDPReceiver();
        Audio receiver = new Audio();
        receiver.initPlayer();
        Audio sender = new Audio();
        sender.initRecorder();
//        Audio audio = new Audio();
        Utils utils = new Utils();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("通话开始，按q结束通话");
        Thread say = new Thread(()->{
            try{
                while (!Thread.currentThread().isInterrupted()){
                    sender.record(1024);
                    byte[] sound = sender.getBuffer();
                    udpSender.send(host,sound);
                    sender.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        Thread listen = new Thread(()->{
            while (!Thread.currentThread().isInterrupted()){
                try {
                    byte[] sound = udpReceiver.receive();
                    receiver.play(sound);
                    receiver.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        say.start();
        listen.start();
        while (!bufferedReader.readLine().equalsIgnoreCase("q"));
        say.interrupt();
        listen.interrupt();
        System.out.println("通话结束");
    }
    public static void main(String args[]){
        Call call = new Call();
        try {
            call.startup();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
