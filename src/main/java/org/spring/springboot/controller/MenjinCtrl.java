package org.spring.springboot.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.beans.binding.ObjectBinding;
import net.sf.json.JSONObject;
import org.spring.springboot.domain.EgDetail;
import org.spring.springboot.service.MenjinService;
import org.spring.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/")
public class MenjinCtrl {
    @Autowired
    private MenjinService menjinService;
    //map
    Map<String,Object> map = new HashMap<String, Object>();
    //UUID
    String uuid = UUID.randomUUID().toString().replaceAll("-", "");

    /**
     * 生成二维码
     * @param id
     * @param type 0_固定用户,1_访客用户,2_访客凭证
     * @return
     */
    @ResponseBody
    @ApiOperation(value="生成二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "用户类型", required = true, dataType = "int")
    })
    @RequestMapping(value = "qrcode",method= RequestMethod.GET)
    public String qrcode(@RequestParam String id,@RequestParam String type){
        String desstr = "";
        String start = "";
        String end = "";
        String x1 = "";
        String qrcode = CommonUtil.getHexString(Long.parseLong(id),1)+  //用户id
                type+ //用户类型
                desstr+ //加密字符串
                start+ //开始日期
                end+ //结束日期
                x1 //门一
                ;
        Map<String,Object> maps = new HashMap<>();
        maps.put("result","1");
        maps.put("message","获取成功");
        maps.put("qrcode","12312312312");
        JSONObject j = JSONObject.fromObject(maps);
        return j.toString();
    }


    /**
     * 门禁数据查询
     * @param id
     * @param starttime
     * @param endtime
     * @return
     */
    @ResponseBody
    @ApiOperation(value="门禁数据查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "starttime", value = "开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "endtime", value = "结束时间", required = true, dataType = "String")
    })
    @RequestMapping(value = "EgDetail",method= RequestMethod.GET)
    public List<EgDetail> EgDetail(@RequestParam String id, @RequestParam String starttime, @RequestParam String endtime){
        map.put("id",id);
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        return menjinService.EgDetail(map);
    }
}
