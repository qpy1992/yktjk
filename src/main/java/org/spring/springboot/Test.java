package org.spring.springboot;


import com.github.qcloudsms.*;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.spring.springboot.util.CommonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

import static org.spring.springboot.util.DesUtil.DES_CBC_Decrypt;
import static org.spring.springboot.util.DesUtil.byteToHexString;

public class Test {
    public static void main(String[] args) {
//        try {
//            SmsSingleSender sender = new   SmsSingleSender(1400064887, "87f9f0c67ffa595b442493032da4c0a3");
//            SmsSingleSenderResult result = sender.send(0, "86", "15295751716", "【克立司帝】123456为您的登录验证码，请于1分钟内填写。如非本人操作，请忽略本短信。", "", "123");
//            System.out.print(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        String s = "苏fr188l";
//        System.out.println(s.toUpperCase());
        System.out.print("解密后:"+ CommonUtil.getHexString(1231231231,1));
    }
}

