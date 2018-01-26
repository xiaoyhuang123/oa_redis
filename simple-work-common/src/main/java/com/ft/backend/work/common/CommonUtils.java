package com.ft.backend.work.common;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * Created by huanghongyi on 2017/11/2.
 */
public class CommonUtils {

    public static String getMD5Str(String origStr) throws Exception{
        MessageDigest md =
                MessageDigest.getInstance("md5");
        byte[] buf = md.digest(origStr.getBytes());
        BASE64Encoder encoder = new BASE64Encoder();
        String str2 = encoder.encode(buf);
        return str2;

    }
}
