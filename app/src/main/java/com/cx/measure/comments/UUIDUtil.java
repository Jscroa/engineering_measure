package com.cx.measure.comments;

import java.util.UUID;

/**
 * Created by yyao on 2016/6/16.
 */
public class UUIDUtil {
    public static String createUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        return uuidStr.replace("-","");
    }
}
