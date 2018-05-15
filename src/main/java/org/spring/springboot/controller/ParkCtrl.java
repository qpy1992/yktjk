package org.spring.springboot.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.spring.springboot.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
