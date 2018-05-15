package org.spring.springboot.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.spring.springboot.service.FangkeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/")
public class FangkeCtrl {
    @Autowired
    FangkeService fangkeService;

    //map
    Map<String,Object> map = new HashMap<String, Object>();

    /**
     * 访客邀请
     * @param json
     * @return
     */
    @ResponseBody
    @ApiOperation(value="{userid:\"3\",fname:\"张三\",fmobile:\"18036215618\",fdate:\"2018-08-05 00:00:00\",flength:\"0.5小时\",freason:\"来访事由\",project_detail_id:\"1a2d4g5yf3gdgf\"}",notes = "访客邀请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "json", value = "json数据", required = true, dataType = "String")
    })
    @RequestMapping(value = "invite",method= RequestMethod.POST)
    public String invite(@RequestBody String json){
        JSONObject j = JSONObject.fromObject(json);
        String userid = j.getString("userid");
        String fname = j.getString("fname");
        String fmobile = j.getString("fmobile");
        String fdate = j.getString("fdate");
        String flength = j.getString("flength");
        String freason = j.getString("freason");
        String projectdetail_id = j.getString("project_detail_id");
        map.put("userid",userid);
        map.put("fname",fname);
        map.put("fmobile",fmobile);
        map.put("fdate",fdate);
        map.put("flength",flength);
        map.put("freason",freason);
        map.put("projectdetail_id",projectdetail_id);
        fangkeService.invite(map);
        String code = "测试二维码";
        return code;
    }

    /**
     * 访客记录
     * @param userid
     * @param starttime
     * @param endtime
     * @return
     */
    @ResponseBody
    @ApiOperation(value="访客记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userid", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "starttime", value = "开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "endtime", value = "结束时间", required = true, dataType = "String")
    })
    @RequestMapping(value = "FkRecord",method= RequestMethod.GET)
    public JSONObject FkRecord(@RequestParam String userid,@RequestParam String starttime,@RequestParam String endtime){
        map.put("userid",userid);
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        List<Map<String,Object>> maplist = fangkeService.FkRecord(map);
        JSONObject json = new JSONObject();
        json.put("list",maplist);
        return json;
    }
}
