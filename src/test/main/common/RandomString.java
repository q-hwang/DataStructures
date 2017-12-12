package main.common;

import java.io.UnsupportedEncodingException;

/**
 * Created by HQ on 9/21/2017.
 */
public class RandomString {

    public static String randomString(int n){
        try {
            int size = (int)(Math.random()*n);
            byte[] bytes = new byte[size];
            for (int i = 0; i < size; i++) {
                bytes[i] = (byte) (Math.random() * ((int)'Z' - (int)'A')+(int)'A');
            }
            return new String(bytes, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            System.err.println("encoding");
            return null;
        }
    }
}
