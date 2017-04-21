package com.aak1247.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author aak12 on 2017/4/20.
 */
public class Utils {
    private Properties properties = new Properties();

    public Utils() {
        String url = (new File("")).getAbsolutePath();
        try {
            FileInputStream fileInputStream = new FileInputStream(url + "\\\\src\\\\main\\\\resources\\\\application.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @return 返回配置文件中设定的端口号
     */
    public String getPort() {
        return properties.getProperty("server.port");
    }

    /**
     * @param username 要连接的用户名
     * @return 返回host，查找不到时返回null
     */
    public String getHost(String username) {
        return properties.getProperty("host." + username);
    }

    public boolean setHost(String username, String host) {
        String url = (new File("")).getAbsolutePath();
        properties.setProperty("host." + username, host);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(url + "\\\\src\\\\main\\\\resources\\\\application.properties");
            properties.store(fileOutputStream, "\nadd/modify host of " + username);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public String getPhonePort(){
        return this.properties.getProperty("phone.port");
    }

    public static void main(String[] args) {
        Utils utils = new Utils();
        utils.setHost("aak1247", "192.169.0.1");
        System.out.println(utils.getHost("aak1247"));
        System.out.println(utils.getPort());
    }
}
