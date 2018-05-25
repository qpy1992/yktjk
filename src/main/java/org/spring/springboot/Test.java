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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.spring.springboot.util.CommonUtil;
import org.spring.springboot.util.Consts;
import org.spring.springboot.util.HttpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;

import static org.spring.springboot.util.DesUtil.DES_CBC_Decrypt;
import static org.spring.springboot.util.DesUtil.byteToHexString;

public class Test {
    public static void main(String[] args) {
        try {
            Document document = DocumentHelper.createDocument();
            Element xml = document.addElement("xml");
            xml.addElement("appid").setText(Consts.WX_APPID);
            xml.addElement("attach").setText("支付测试");
            xml.addElement("body").setText("一卡通-停车缴费");
            xml.addElement("mch_id").setText(Consts.MCD_ID);
            xml.addElement("nonce_str").setText(UUID.randomUUID().toString().replaceAll("-",""));
            xml.addElement("notify_url").setText("http://www.weixin.qq.com/wxpay/pay.php");
            xml.addElement("out_trade_no").setText("a001");
            xml.addElement("spbill_create_ip").setText("205.168.1.102");
            xml.addElement("total_fee").setText("20");
            xml.addElement("trade_type").setText("APP");
            xml.addElement("sign").setText("0CB01533B8C1EF103065174F50BCA001");
            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
            outputFormat.setSuppressDeclaration(false);
            outputFormat.setNewlines(false);
            StringWriter stringWriter = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
            xmlWriter.write(document);
            String wxxml = stringWriter.toString().substring(38);
            System.out.println(wxxml);
            String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder",wxxml);
            System.out.println(result);
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(result); // 将字符串转为XML
                Element rootElt = doc.getRootElement(); // 获取根节点
                System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
                String return_code = rootElt.elementTextTrim("return_code");
                System.out.println(return_code);
                String return_msg = rootElt.elementTextTrim("return_msg");
                System.out.println(return_msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

