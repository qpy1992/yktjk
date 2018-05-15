package org.spring.springboot.controller;

import io.swagger.annotations.ApiImplicitParams;
import org.spring.springboot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.spring.springboot.service.MeetingService;
import org.spring.springboot.service.ParkService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class MeetingCtrl{

	@Autowired
	private MeetingService meetingService; 
	Map<String,Object> map = new HashMap<String, Object>();
	JSONObject jsonMessage = new JSONObject();
	int count = 0;

	/**
	 * 消息查询接口
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value="消息查询接口")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userid", value = "用户id", required = true, dataType = "String")
	})
	@RequestMapping(value = "meetingSearch",method= RequestMethod.POST)
	public JSON meetingSearch(@RequestParam String userid) {
		try{
			map.put("userid", userid);
			List<Map<String,Object>> meetingList = meetingService.meetingSearch(map);
			jsonMessage.put("jsonObject", meetingList);
		   	jsonMessage.put("message","查找成功");
		   	jsonMessage.put("code",0);
		   	return jsonMessage;
		}catch(Exception e){
			System.out.println("异常信息"+e.getMessage());
		   	jsonMessage.put("message","数据异常");
		   	jsonMessage.put("code",1);
		   	return jsonMessage;
		}
	}

	/**
	 * 消息详情
	 * @param userid
	 * @param fmeeting_id
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value="消息详情")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userid", value = "用户id", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "fmeeting_id", value = "消息id", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "fread", value = "是否已读", required = true, dataType = "String")
	})
	@RequestMapping(value = "MeetingDetail",method= RequestMethod.POST)
	public JSON MeetingDetail(@RequestParam String userid,@RequestParam String fmeeting_id,@RequestParam String fread) {
		try{
			map.put("userid", userid);
			map.put("fmeeting_id", fmeeting_id);
			if(fread.equals("0")){
				meetingService.insertMeetingRead(map);
			}
			Map<String,Object> maps = meetingService.meetingDetail(map);
			jsonMessage.put("detail",maps);
			jsonMessage.put("message","消息查阅成功");
			jsonMessage.put("code",1);
		   	return jsonMessage;
		}catch(Exception e){
			e.printStackTrace();
			jsonMessage.put("detail",e.getStackTrace());
		   	jsonMessage.put("message","数据异常");
		   	jsonMessage.put("code",2);
		   	return jsonMessage;
		}
	}
}
