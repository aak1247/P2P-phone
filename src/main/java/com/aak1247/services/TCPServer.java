package com.aak1247.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * @author aak12 on 2017/4/20.
 */
public class TCPServer {
    public boolean start() {
        try {
            Utils utils = new Utils();
            Properties properties = utils.getProperties();
            int port = Integer.getInteger(utils.getPort());
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务已启动，等待连接");
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp = null;
            String host = socket.getInetAddress().getHostAddress();
//            while ((temp = bufferedReader.readLine())!=null){
            if (properties.containsValue(host)) {
                String user = properties.keySet().stream()
                        .filter(x -> properties.getProperty(x.toString()).equals(host))
                        .findFirst().toString();
                System.out.println(user + "连接成功~");
            } else {
                System.out.println("IP:"+host + "连接成功");
            }
//            }
        } catch (IOException e) {
            /*
             IOException is caused by Serversorker
             */
            e.printStackTrace();
        }
        return false;
    }
}
