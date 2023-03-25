package com.lk.utils;




import java.util.UUID;

public class IDUtils {
    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
