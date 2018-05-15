package org.spring.springboot.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
    public static String GetUtf8(String  str){
        try {
            str = new String(str.getBytes("iso8859-1"),
                    "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    public static String GetFbillno(int count){
        String head = "QX";
        String date = new SimpleDateFormat("HHmmss").format(new Date());
        String tail = null;
        if(count<10){
            tail = "000"+count;
        }else{
            tail = "00"+count;
        }
        count ++;
        return head+date+tail;
    }
}
