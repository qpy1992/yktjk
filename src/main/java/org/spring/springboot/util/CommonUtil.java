package org.spring.springboot.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.weixin.MyX509TrustManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 通用工具类
 * 
 * @author liufeng
 * @date 2013-10-17
 */
@Controller
public class CommonUtil {

	/**
	 * 获取4字节的16进制数
	 */
	public static String getHexString(long number,int type){
		String hex = Long.toHexString(number);
		switch (type){
			case 0:
				break;
			case 1:
				while (hex.length()<8){
					hex = "0"+hex.toUpperCase();
				}
		}
		return hex;
	}
}