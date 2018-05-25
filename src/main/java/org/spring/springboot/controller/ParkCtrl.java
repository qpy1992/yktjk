package org.spring.springboot.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.spring.springboot.service.ParkService;
import org.spring.springboot.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
public class ParkCtrl {
    @Autowired
    private ParkService parkService;

    //map
    Map<String,Object> map = new HashMap<String, Object>();
    Map<String,Object> map1 = new HashMap<String, Object>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 车辆进入接口
     * @param plateNo
     * @param parkId
     * @return
     */
    @ResponseBody
    @ApiOperation(value="车辆进入接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "plateNo", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "parkId", value = "停车场id", required = true, dataType = "String")
    })
    @RequestMapping(value = "ParkIn",method= RequestMethod.POST)
    public Object ParkIn(@RequestParam String plateNo,@RequestParam String parkId) {
        String resMsg = null;
        Integer resCode = null;
        String inTime = null;
        Integer count = null;
        try{
            map.put("plateNo", plateNo);
            count = parkService.selectplateNo(map);
            if(count.equals(0)){
                Date date = new Date();
                inTime = sdf.format(date);
                System.out.println(inTime+"inTime");
                map.put("plateNo", plateNo);
                map.put("inTime", inTime);
                map.put("parkId", parkId);
                resCode = parkService.ParkIn(map);
                if(resCode.equals(1)){
                    resCode = 0;       //0正常
                    resMsg = "";
                }
            }else{
                resCode = 1;       //1不正常
                resMsg = "当前车牌号已存在！";
            }
        }catch(Exception e){
            resCode = 1;
            resMsg = e.getMessage();
        }
        map1.put("resCode", resCode);
        map1.put("resMsg", resMsg);
        return map1;
    }

    /**
     * 车辆离开接口
     * @param plateNo
     * @return
     */
    @ResponseBody
    @ApiOperation(value="车辆离开接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "plateNo", value = "车牌号", required = true, dataType = "String")
    })
    @RequestMapping(value = "ParkOut",method= RequestMethod.POST)
    public Object ParkOut(@RequestParam String plateNo) {
        map1 = new HashMap<String, Object>();
        Integer resCode = null;
        String resMsg = null;
        String outTime = null;
        Integer count = null;
        try{
            map.put("plateNo", plateNo);
            count = parkService.selectplateNo(map);
            if(count.equals(1)){
                Date date = new Date();
                outTime = sdf.format(date);
                map.put("plateNo", plateNo);
                map.put("outTime", outTime);
                resCode = parkService.updateParkout(map);
                if(resCode.equals(1)){
                    resCode = 0;       //0正常
                    resMsg = "";
                }
            }else{
                resCode = 1;       //0正常
                resMsg = "请输入正确的车牌号！";
            }
        }catch(Exception e){
            resCode = 1;
            resMsg = e.getMessage();
        }
        map1.put("resCode", resCode);
        map1.put("resMsg", resMsg);
        return map1;
    }


    /**
     * 缴费查询接口
     * @param plateNo
     * @return
     */
    @ResponseBody
    @ApiOperation(value="缴费查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "plateNo", value = "车牌号", required = true, dataType = "String")
    })
    @RequestMapping(value = "parkingRecordSearch",method= RequestMethod.POST)
    public Object parkingRecordSearch(@RequestParam String plateNo) {
        Integer resCode = null;
        String resMsg = null;
        map1 = new HashMap<String, Object>();
        Map<String,Object> parklist = new HashMap<String, Object>();
        String  payTime = null;
        Integer  amount = null;
        Integer  discount = null;
        Integer  payType = null;
        Integer count =null;
        try{
            map.put("plateNo", plateNo);
            count = parkService.selectplateNo(map);
            if(count.equals(1)){
                map.put("plateNo", plateNo);
                parklist = parkService.parkingRecordSearch(map);
                try{
                    payTime = parklist.get("payTime").toString();
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    amount = (Integer)parklist.get("amount");
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    discount =  (Integer)parklist.get("discount");
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    payType = (Integer)parklist.get("payType");
                }catch(Exception e){
                    e.printStackTrace();
                }
                resCode = 0;
                resMsg = "";
            }else{
                resCode = 1;       //0正常
                resMsg = "请输入正确的车牌号！";
            }
        }catch(Exception e){
            resCode = 1;
            resMsg = e.getMessage();
        }
        map1.put("resCode", resCode);
        map1.put("resMsg", resMsg);
        map1.put("payTime", payTime);
        map1.put("amount", amount);
        map1.put("discount", discount);
        map1.put("payType", payType);
        map1.put("parklist",parklist);
        return map1;
    }

    /**
     * 车位预约
     * @param json
     * @return
     */
    @ResponseBody
    @ApiOperation(value="{userid:\"3\",no:\"苏F12345\",time:\"2018-05-09 10:10:00\",length:\"2小时\",project_detail_id:\"1d2g3g4j5dasd\"}",notes="车位预约")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "json", value = "json数据", required = true, dataType = "String")
    })
    @RequestMapping(value = "parkReserve",method= RequestMethod.POST)
    public int parkReserve(@RequestBody String json){
        try {
            JSONObject j = JSONObject.fromObject(json);
            String userid = j.getString("userid");
            String no = j.getString("no");
            String time = j.getString("time");
            String length = j.getString("length");
            String projectdetail_id = j.getString("project_detail_id");
            map.put("userid", userid);
            map.put("no", no);
            map.put("time",time);
            map.put("length",length);
            map.put("project_detail_id",projectdetail_id);
            parkService.parkReserve(map);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 2;
        }
    }

    /**
     * 缴费
     * @param json
     * @return
     */
    @ResponseBody
    @ApiOperation(value="{userid:\"3\",time:\"\",device_id:\"\",amount:\"\",paycode:\"\"}",notes="缴费")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "json", value = "json", required = true, dataType = "String")
    })
    @RequestMapping(value = "pay",method= RequestMethod.GET)
    public String pay(@RequestParam String json){
        try {
            JSONObject j = JSONObject.fromObject(json);
            String userid = j.getString("userid");
            String time = j.getString("time");
            String device_id = j.getString("device_id");
            String amount = j.getString("amount");
            String paycode = j.getString("paycode");
            map.put("userid", userid);
            map.put("time", time);
            map.put("device_id", device_id);
            map.put("amount", amount);
            map.put("paycode", paycode);
            parkService.pay(map);
            return "SUCCESS";
        }catch (Exception e){
            e.printStackTrace();
            return "FAIL";
        }
    }

    /**
     * 缴费记录
     * @param userid
     * @param starttime
     * @param endtime
     * @return
     */
    @ResponseBody
    @ApiOperation(value="缴费记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userid", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "starttime", value = "开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "endtime", value = "结束时间", required = true, dataType = "String")
    })
    @RequestMapping(value = "payRecord",method= RequestMethod.GET)
    public JSONObject payRecord(@RequestParam String userid,@RequestParam String starttime,@RequestParam String endtime){
        map.put("userid",userid);
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        List<Map<String,Object>> maplist = parkService.payRecord(map);
        JSONObject j = new JSONObject();
        j.put("list",maplist);
        return j;
    }

    /**
     * 车牌锁定解锁
     * @param plateno
     * @param fstatus
     * @return
     */
    @ResponseBody
    @ApiOperation(value="车辆锁定，解锁")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userid", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "plateno", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "fstatus", value = "状态", required = true, dataType = "String")
    })
    @RequestMapping(value = "lock",method= RequestMethod.POST)
    public String lock(@RequestParam String userid,@RequestParam String plateno,@RequestParam String fstatus){
        try {
            map.put("userid",userid);
            map.put("fstatus",fstatus);
            map.put("plateno",plateno);
            parkService.lock(map);
            return "SUCCESS";
        }catch (Exception e){
            e.printStackTrace();
            return "FAIL";
        }
    }

    /**
     * 入场车辆状态信息
     * @param userid
     * @return
     */
    @ResponseBody
    @ApiOperation(value="入场车辆状态信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userid", value = "用户id", required = true, dataType = "String")
    })
    @RequestMapping(value = "getStatus",method= RequestMethod.GET)
    public JSONObject getStatus(@RequestParam String userid){
        try {
            List<String> list = parkService.searchPlate(userid);
            List<Map<String,Object>> maps = parkService.searchStatus(list);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("list",maps);
            JSONObject json = JSONObject.fromObject(map1);
            return json;
        }catch (Exception e){
            e.printStackTrace();
            String s = "{result:\"FAIL\"}";
            JSONObject json = JSONObject.fromObject(s);
            return json;
        }
    }

    /**
     * 微信支付，统一下单
     * @param json
     * @return
     */
    @ResponseBody
    @ApiOperation(value="{userid:\"3\",time:\"\",device_id:\"\",paycode:\",ip:\"221.130.87.105\",fee:\"20\"}",notes="微信支付，统一下单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "json", value = "json格式数据", required = true, dataType = "String")
    })
    @RequestMapping(value = "unifiedOrder",method= RequestMethod.POST)
    public JSONObject unifiedOrder(@RequestParam String json){
        JSONObject jResult = new JSONObject();
        try {
            JSONObject j = JSONObject.fromObject(json);
            String nonce_str = UUID.randomUUID().toString().replaceAll("-","");
            String orderno = "";
            String ip = j.getString("ip");
            String fee = j.getString("fee");
            String userid = j.getString("userid");
            String time = j.getString("time");
            String device_id = j.getString("device_id");
            String paycode = j.getString("paycode");
            Map<String,Object> map = new HashMap<>();
            map.put("userid", userid);
            map.put("time", time);
            map.put("fee",fee);
            map.put("device_id", device_id);
            map.put("paycode", paycode);
            switch (Integer.parseInt(paycode)){
                case 1://支付宝
                    Map<String,String> params = OrderInfoUtil2_0.buildOrderParamMap(Consts.ALI_APPID,orderno,Double.parseDouble(fee));
                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                    String sign = OrderInfoUtil2_0.getSign(params,Consts.RSA_PRIVATE_KEY,true);
                    String orderinfo = orderParam+"&"+sign;
                    jResult.put("orderinfo",orderinfo);
                    parkService.pay(map);
                    return jResult;
                case 2://微信
                    Document document = DocumentHelper.createDocument();
                    Element xml = document.addElement("xml");
                    xml.addElement("appid").setText(Consts.WX_APPID);//应用id
                    xml.addElement("body").setText(Consts.BODY);//商品描述
                    xml.addElement("mch_id").setText(Consts.MCD_ID);//商户号
                    xml.addElement("nonce_str").setText(nonce_str);//随机字符串
                    xml.addElement("notify_url").setText(Consts.NOTIFY_URL);//接收回执的地址
                    xml.addElement("out_trade_no").setText(orderno);//商户订单号
                    xml.addElement("spbill_create_ip").setText("205.168.1.102");//终端ip
                    xml.addElement("total_fee").setText(fee);//总费用
                    xml.addElement("trade_type").setText("APP");//支付类型
                    Map<String,String> m = new HashMap<>();
                    m.put("nonce_str",nonce_str);
                    m.put("out_trade_no",orderno);
                    m.put("spbill_create_ip",ip);
                    m.put("fee",fee);
                    xml.addElement("sign").setText(CommonUtil.getSign(m));//签名
                    OutputFormat outputFormat = OutputFormat.createPrettyPrint();
                    outputFormat.setSuppressDeclaration(false);
                    outputFormat.setNewlines(false);
                    StringWriter stringWriter = new StringWriter();
                    XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
                    xmlWriter.write(document);
                    String wxxml = stringWriter.toString().substring(38);
                    System.out.println(wxxml);
                    String result = HttpRequest.sendPost(Consts.UNIFIEDORDER,wxxml);
                    System.out.println(result);
                    Document doc = DocumentHelper.parseText(result); // 将字符串转为XML
                    Element rootElt = doc.getRootElement(); // 获取根节点
                    System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
                    String return_code = rootElt.elementTextTrim("return_code");
                    String return_msg = rootElt.elementTextTrim("return_msg");
                    if(return_code.equals("FAIL")){
                        jResult.put("return_code",return_code);
                        jResult.put("return_msg",return_msg);
                        return jResult;
                    }
                    if(return_code.equals("SUCCESS")){
                        parkService.pay(map);
                        jResult.put("return_code",return_code);
                        jResult.put("return_msg",return_msg);
//                String appid = rootElt.elementTextTrim("appid");
//                String mch_id = rootElt.elementTextTrim("mch_id");
//                String nonce_str1 = rootElt.elementTextTrim("nonce_str");
//                String sign = rootElt.elementTextTrim("sign");
                        String result_code = rootElt.elementTextTrim("result_code");
                        if(result_code.equals("FAIL")){
                            String err_code = rootElt.elementTextTrim("err_code");
                            String err_code_des = rootElt.elementTextTrim("err_code_des");
                            jResult.put("result_code",result_code);
                            jResult.put("err_code",err_code);
                            jResult.put("err_code_des",err_code_des);
                            return jResult;
                        }
                        if(result_code.equals("SUCCESS")){
                            String trade_type = rootElt.elementTextTrim("trade_type");
                            String prepay_id = rootElt.elementTextTrim("prepay_id");
                            jResult.put("result_code",result_code);
                            jResult.put("trade_type",trade_type);
                            jResult.put("prepay_id",prepay_id);
                            return jResult;
                        }
                    }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            jResult.put("Exception",e.getStackTrace());
            return jResult;
        }
    }


    /**
     * 该链接是通过【统一下单API】中提交的参数notify_url设置，如果链接无法访问，商户将无法接收到微信通知。
     * 通知url必须为直接可访问的url，不能携带参数。示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay.action”
     *
     * 支付完成后，微信会把相关支付结果和用户信息发送给商户，商户需要接收处理，并返回应答。
     * 对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知，尽可能提高通知的成功率，但微信不保证通知最终能成功。
     * （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）
     * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
     * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
     * 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证，防止数据泄漏导致出现“假通知”，造成资金损失。
     *
     * @throws Exception
     *
     * @throws Exception
     *
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value="微信支付，统一下单")
    @RequestMapping(value = "PaySult",method= RequestMethod.POST)
    public String PaySult(HttpServletRequest request, HttpServletResponse response) throws Exception {


        String resXml = "";
        Map<String, String> backxml = new HashMap<String, String>();


        InputStream inStream;
        try {
            inStream = request.getInputStream();


            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            System.out.println("微信支付----付款成功----");
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
            System.out.println("微信支付----result----=" + result);
            Map<Object, Object> map = XmlConverUtil.xmltoMap(result);

            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                System.out.println("微信支付----返回成功");
                if (verifyWeixinNotify(map)) {
                    // 订单处理 操作 orderconroller 的回写操作?
                    System.out.println("微信支付----验证签名成功");
                    // backxml.put("return_code", "<![CDATA[SUCCESS]]>");
                    // backxml.put("return_msg", "<![CDATA[OK]]>");
                    // // 告诉微信服务器，我收到信息了，不要在调用回调action了
                    // strbackxml = pay.ArrayToXml(backxml);
                    // response.getWriter().write(strbackxml);
                    // logger.error("微信支付 ~~~~~~~~~~~~~~~~执行完毕？backxml=" +
                    // strbackxml);


                    // ====================================================================
                    // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";


                    // 处理业务 -修改订单支付状态
                    System.out.println("微信支付回调：修改的订单=" + map.get("out_trade_no"));
                }
                // ------------------------------
                // 处理业务完毕
                // ------------------------------
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("支付回调发布异常：" + e);
            e.printStackTrace();
        }
        return resXml;
    }


    /**
     * 验证签名
     *
     * @param map
     * @return
     */
    public boolean verifyWeixinNotify(Map<Object, Object> map) {
        SortedMap<String, String> parameterMap = new TreeMap<String, String>();
        String sign = (String) map.get("sign");
        for (Object keyValue : map.keySet()) {
            if (!keyValue.toString().equals("sign")) {
                parameterMap.put(keyValue.toString(), map.get(keyValue).toString());
            }
        }
        //生成sign标签
        String createSign = pay.getSign(parameterMap);
        if (createSign.equals(sign)) {
            return true;
        } else {
            System.out.println("微信支付  ~~~~~~~~~~~~~~~~验证签名失败");
            return false;
        }


    }
}
