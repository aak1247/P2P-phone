package com.aak1247.services;

import java.io.*;
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
            int port = Integer.parseInt(utils.getPort());
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
                System.out.println(user + "已连接~");
            } else {
                System.out.println("IP:"+host + "已连接");
            }

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.print("服务器连接成功");
            printWriter.flush();
            socket.shutdownOutput();
            printWriter.close();
            outputStream.close();
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
//            }
        } catch (IOException e) {
            /*
             IOException is caused by Serversorker
             */
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String args[]){
        TCPServer tcpServer = new TCPServer();
        tcpServer.start();
    }
}
