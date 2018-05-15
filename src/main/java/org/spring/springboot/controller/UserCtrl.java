package org.spring.springboot.controller;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.google.gson.JsonObject;

import io.swagger.annotations.ApiImplicitParams;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.spring.springboot.domain.Depart;
import org.spring.springboot.domain.Plate;
import org.spring.springboot.domain.User;
import org.spring.springboot.domain.UserDetail;
import org.spring.springboot.util.Consts;
import org.spring.springboot.util.PasswordUtil;
import org.spring.springboot.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.spring.springboot.service.UserService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/")
public class UserCtrl{

	@Autowired
	private UserService userService;
	Map<String,Object> map = new HashMap<String, Object>();
	
	@ResponseBody
	@ApiOperation(value="验证码短信发送")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "mobile", value = "用户手机号", required = true, dataType = "String")
 	})
	@RequestMapping(value = "sendMsg",method= RequestMethod.GET)
	public JSON sendMsg(@RequestParam String mobile){
		String code = String.valueOf((int)((Math.random()*9+1)*100000));
		JSONObject JsonObject = new JSONObject();
		try {
			SmsSingleSender sender = new   SmsSingleSender(Consts.APPID, Consts.APPKEY);
			SmsSingleSenderResult result = sender.send(0, "86", mobile, "【克立司帝】"+code+"为您的登录验证码，请于1分钟内填写。如非本人操作，请忽略本短信。", "", "123");
			System.out.print(result);
			JsonObject.put("validateCode", code);
			JsonObject.put( "valid",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonObject;
	}
	
	
	/**
	 * 注册
	 * @param jsonPost
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value="{telephone:\"137888888888\",password:\"123456\",username:\"张三\"}", notes="注册")
	@RequestMapping(value = "register",method= RequestMethod.POST)
	public JSONObject register(@RequestBody String jsonPost,HttpServletRequest request,HttpServletResponse response) {
		try {
			  net.sf.json.JSONObject objPost=net.sf.json.JSONObject.fromObject(jsonPost);
			    HashMap<String, Object> map = new HashMap<>();
			    String telephone  = objPost.getString("telephone");
			    String password  = objPost.getString("password");
			    String username = objPost.getString("username");
			    map.put("telephone", telephone);
				int total = userService.countMobile(map);
				if(total>0) {
					Map<String, String> mapCF = new HashMap<String, String>();
					mapCF.put("result", "3");
					mapCF.put("message", "手机号不能重复注册");
					JSONObject jsonObject = JSONObject.fromObject(mapCF);
					return jsonObject;
				}
				Map<String, Object> map1 = new HashMap<String, Object>();
			    map1.put("telephone", telephone);
			    map1.put("password", password);
			    map1.put("username",username);
			int flag = userService.insertProjectuser(map1);
		    System.out.println("flag="+flag);
			Map<String, Object> mapCZ = new HashMap<String, Object>();
			if(flag>0) {
				mapCZ.put("result", "2");
				mapCZ.put("message", "用户注册成功");
			}
			else {
				mapCZ.put("result", "0");
				mapCZ.put("message", "用户注册失败");
			}
			JSONObject jsonObject = JSONObject.fromObject(mapCZ);
			return jsonObject;
		} catch (Exception e) {
			System.out.println("登入异常" + e.getMessage());
			Map<String, String> mapEX = new HashMap<String, String>();
			mapEX.put("result", "0");
			mapEX.put("message", "系统异常");
			JSONObject jsonObject = JSONObject.fromObject(mapEX);
			return jsonObject;
		}	
	}		

	
	/**
	 * 登录验证
	 * @param telephone
	 * @param password
	 * @return
	 */ 
	@ResponseBody
	@ApiOperation(value="登录验证", notes="登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "telephone", value = "手机号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "password", value = "密码", required = true, dataType = "String")
	})
	@RequestMapping(value = "login",method= RequestMethod.POST)
	public JSONObject Login(@RequestParam String telephone,@RequestParam String password) {
       try {
			HashMap<String, Object> map = new HashMap<>();
			map.put("password", password);
			map.put("telephone", telephone);
			int total_mobile = userService.countProjectUser(map);
		    if(total_mobile==0) {
				Map<String, String> mapCF = new HashMap<String, String>();
				mapCF.put("result", "3");
				mapCF.put("message", "手机号不存在");
				JSONObject jsonObject = JSONObject.fromObject(mapCF);
				return jsonObject;
			}
		    Map<String,Object> userinfo = userService.login(map);
		    if(userinfo==null) {
				Map<String, String> mapCF = new HashMap<String, String>();
				mapCF.put("result", "4");
				mapCF.put("message", "用户密码不对");
				JSONObject jsonObject = JSONObject.fromObject(mapCF);
				return jsonObject;
			}
		    String fstatus = userinfo.get("fstatus").toString();
		    String username = userinfo.get("user_name").toString();
		    String id = userinfo.get("id").toString();
			Map<String, Object> mapCZ = new HashMap<String, Object>();
			mapCZ.put("result", "2");
			mapCZ.put("message", "用户登陆成功");
		    mapCZ.put("fstatus", fstatus);
			mapCZ.put("telephone", telephone);
			mapCZ.put("username", username);
			mapCZ.put("userid",id);
			JSONObject jsonObject = JSONObject.fromObject(mapCZ);
			return jsonObject;
		} catch (Exception e) {
			System.out.println("出现异常" + e.getMessage());
			Map<String, String> mapEX = new HashMap<String, String>();
			mapEX.put("result", "0");
			mapEX.put("message", "系统异常");
			JSONObject jsonObject = JSONObject.fromObject(mapEX);
			return jsonObject;
		}
	}
 
	
	
	
	
	/**
	 * 选择项目   （默认传1 ）  和  项目资料
	 * @param id
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value="选择项目   （默认传1 ）  和  项目资料")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "id", value = "项目id", required = true, dataType = "String")
	})
	@RequestMapping(value = "project",method= RequestMethod.GET)
	public JSON project(@RequestParam String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject JsonObject = new JSONObject();
		if(id.equals("1")){
			map.put("id",1);
			List<Map<String,Object>>  list = userService.serProjectList(map);
			JsonObject.put("projectlist", list);
		}else{
			map.put("id",id);
			List<Map<String,Object>>  list = userService.serProjectDetailList(map);
			JsonObject.put("projectdetalilist", list);
		}
		return JsonObject;
	}
 
	
	
	/**
	 * 选择该用户手机号注册的项目   和  项目资料
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value="选择该用户手机号注册的项目    和  项目资料")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userid", value = "用户id", required = true, dataType = "String")
	})
	@RequestMapping(value = "projectByTel",method= RequestMethod.GET)
	public JSON projectByTel(@RequestParam String userid) {
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject JsonObject = new JSONObject();
	    map.put("userid",userid);
	    List<Map<String,Object>>  list = userService.serProjectListByTel(map);
	    JsonObject.put("projectlist", list);
		return JsonObject;
	}
 
	
	
	/**
	 * 认证信息
	 * @param json
	 * @return
	 */ 
	@ResponseBody
    @ApiOperation(value="{userid:\"1\",project_id:\"1111\",project_detail_id:\"2222\",relation:\"本人\",faddress:\"三栋1号楼\",id_pic:\"aa.pig\"}", notes="认证信息")
	@RequestMapping(value = "identify",method= RequestMethod.POST)
	public JSONObject identify(@RequestBody String json,HttpServletRequest request,HttpServletResponse response) {
		JSONObject js = JSONObject.fromObject(json);
		HashMap<String, Object> map = new HashMap<>();
		String userid = js.getString("userid");
		String project_id = js.getString("project_id");
		String project_detail_id = js.getString("project_detail_id");
		String relation = js.getString("relation");
		String faddress = js.getString("faddress");
		String id_pic = js.getString("id_pic");
		map.put("userid", userid);
		map.put("project_id", project_id);
		map.put("project_detail_id", project_detail_id);
		map.put("relation", relation);
		map.put("faddress", faddress);
		map.put("id_pic", id_pic);
		Map<String, Object> mapCZ = new HashMap<String, Object>();
		int flag1 = 0;
			flag1 = userService.insertProjectuser1(map);
			if(flag1>0) {
				mapCZ.put("result", "2");
				mapCZ.put("message", "认证成功");
			}
			else {
				mapCZ.put("result", "0");
				mapCZ.put("message", "认证失败");
			}
			js = JSONObject.fromObject(mapCZ);
		return js;
	}

	/**
	 * 修改密码
	 * @param new_psw
	 * @param telephone
	 * @return
	 */
	@ResponseBody
    @ApiOperation(value="修改密码", notes="修改密码")
	@ApiImplicitParams
	({
		@ApiImplicitParam(paramType="query", name = "telephone", value = "用户手机号", required = true, dataType = "String"),
	    @ApiImplicitParam(paramType="query", name = "new_psw", value = "新密码", required = true, dataType = "String")
				})
	@RequestMapping(value = "modifyPsw",method= RequestMethod.POST)
	public JSONObject modifyPsw(@RequestParam String new_psw,@RequestParam String telephone){
		try { 
				HashMap<String, Object> map = new HashMap<>();
				map.put("telephone", telephone);
				map.put("new_psw", new_psw);
				userService.updatePsw(map);
				Map<String, Object> mapCZ = new HashMap<String, Object>();
				mapCZ.put("result", "2");
				mapCZ.put("message", "用户密码更新成功");
				mapCZ.put("telephone", telephone);
				JSONObject jsonObject = JSONObject.fromObject(mapCZ);
				return jsonObject;
				} catch (Exception e) {
					System.out.println("出现异常" + e.getMessage());
					Map<String, String> mapEX = new HashMap<String, String>();
					mapEX.put("result", "0");
					mapEX.put("message", "系统异常");
					JSONObject jsonObject = JSONObject.fromObject(mapEX);
					return jsonObject;
				}
			}
				
			 
	  
	 /**
	 * 个人资料获取
	 * @param userid
	 * @return
	 */ 
	@ResponseBody
	@ApiOperation(value="个人资料获取")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "userid", value = "用户id", required = true, dataType = "int")
	})
	@RequestMapping(value = "serDetailInfo",method= RequestMethod.GET)
	public JSON serDetailInfo(@RequestParam int userid) {
		JSONObject JsonObject = new JSONObject();
		map.put("userid", userid);
		Map<String,Object>   detail = userService.serDetailInfo(map);
		List<Map<String,Object>>  listProject = userService.serProjectListByTel1(map);
		detail.put("listProject", listProject);
		JsonObject.put("arr", detail);
		return  JsonObject;
	}
 
 


    /**
	 * 添加车牌
	 * @param platejson
	 * @return
	 */ 
	@ResponseBody
    @ApiOperation(value="{user_id:\"1\",plateno:\"1111\",model:\"2222\",color:\"红色\"}", notes="添加车牌")
	@RequestMapping(value = "addplate",method= RequestMethod.POST)
	public int addplate(@RequestBody String platejson) {
		try {
			JSONObject json = JSONObject.fromObject(platejson);
			String user_id = json.getString("user_id");
			String plateno = json.getString("plateno");
			String model = json.getString("model");
			String color = json.getString("color");
			map.put("id", user_id);
			map.put("plateno", plateno);
			map.put("model", model);
			map.put("color",color);
			userService.addplate(map);
			return 1;
		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}


	 /**
	 * 获取车牌
	 * @param userid
	 * @return
	 */ 
	@ResponseBody
	@ApiOperation(value="获取车牌")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "userid", value = "用户id", required = true, dataType = "String")
	})
	@RequestMapping(value = "getplate",method= RequestMethod.GET)
	public List<Plate> getplate(@RequestParam String userid) {
		try {
			return userService.getplate(userid);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}


 /**
	 * 更改默认车牌
	 * @param id
	 * @param userid
	 * @return
	 */ 
	@ResponseBody
	@ApiOperation(value="更改默认车牌")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "id", value = "车牌id", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "userid", value = "用户id", required = true, dataType = "String")
	})
	@RequestMapping(value = "changeplate",method= RequestMethod.POST)
	public int changeplate(@RequestParam String id,@RequestParam String userid) {
		try {
			map.put("id",id);
			map.put("userid",userid);
			userService.cancelplate(map);
			userService.changeplate(map);
			return 1;
		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}


	 /**
	 * 删除车牌
	 * @param id
	 * @return
	 */ 
	@ResponseBody
	@ApiOperation(value="删除车牌")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "id", value = "车牌id", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "userid", value = "用户id", required = true, dataType = "int")
	})
	@RequestMapping(value = "delplate",method= RequestMethod.POST)
	public int delplate(@RequestParam String id,@RequestParam int userid) {
		try {
			map.put("id",id);
			map.put("userid",userid);
			String fisdefault = userService.getstatus(id);
			if(fisdefault.equals("Y")){
				return 2;
			}else{
				userService.delplate(map);
				return 1;
			}
		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 菜品列表
	 * @param project_id
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value="菜品列表", notes="")
	@ApiImplicitParams({
		 @ApiImplicitParam(paramType="query", name = "project_id", value = "项目id", required = true, dataType = "String") 
		  
	})
	@RequestMapping(value = "serGoodsList",method= RequestMethod.GET)
	public JSON maincommentList(@RequestParam String project_id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("project_id", project_id);
		JSONObject JsonObject = new JSONObject();
		List<Map<String,Object>>  list = userService.serGoodsList(map);
		JsonObject.put("arr", list);
		return JsonObject;
	}

	/**
	 * 菜品详情
	 * @param goodsId
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value="菜品详情", notes="")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "goodsId", value = "菜品id", required = true, dataType = "String")

	})
	@RequestMapping(value = "GoodsDetail",method= RequestMethod.GET)
	public Map<String,Object> goodsDetail(@RequestParam String goodsId) {
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("goodsId", goodsId);
			return userService.goodsDetail(map);
		}catch (Exception e){
			e.printStackTrace();
			map.put("exception",e.getStackTrace());
			return map;
		}
	}
}
