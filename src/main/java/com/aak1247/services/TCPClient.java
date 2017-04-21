package com.aak1247.services;

import java.io.*;
import java.net.Socket;

/**
 * @author aak12 on 2017/4/20.
 */
public class TCPClient {
    public void connect(String host, int port){
        try {
            Socket socket = new Socket(host,port);
            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);
            printWriter.print("connecting");
            printWriter.flush();

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String info = "";
            String temp = null;
            while ((temp=bufferedReader.readLine())!=null){
                info+=temp;
                System.out.println(info);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
            socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        TCPClient tcpClient = new TCPClient();
        tcpClient.connect("localhost",6666);
    }
}
