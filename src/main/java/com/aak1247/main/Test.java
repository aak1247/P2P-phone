package com.aak1247.main;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

/**
 * @author  aak12 on 2017/4/22.
 */
public class Test {
    public static void main(String arge[]){
        RegularExpression regularExpression = new RegularExpression("^((\\d){1,3}.){3}(\\d){1,3}$");
        System.out.println(regularExpression.matches("192.168.0.1"));
    }
}
