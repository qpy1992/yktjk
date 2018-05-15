package org.spring.springboot.dao;

import org.spring.springboot.domain.Plate;
import java.util.List;
import java.util.Map;

public interface UserDao {
	public int countMobile(Map<String, Object> map);

	public int insertProjectUser(Map<String,Object> map);

	public int countProjectUser(Map<String, Object> map);

	public Map<String,Object> login(Map<String,Object> map);

	public List<Map<String,Object>> serProjectList(Map<String,Object> map);

	public List<Map<String,Object>> serProjectDetailList(Map<String,Object> map);

	public List<Map<String,Object>> serProjectListByTel(Map<String,Object> map);

	public int insertProjectUser1(Map<String,Object> map);

	public int updatePsw(Map<String,Object> map);

	public  Map<String,Object>  serDetailInfo(Map<String,Object> map);

	public List<Map<String,Object>> serProjectListByTel1(Map<String,Object> map);

	public void addplate(Map<String,Object> map);

	public List<Plate> getplate(String id);

	public void cancelplate(Map<String,Object> map);

	public void changeplate(Map<String,Object> map);

	public String getstatus(String id);

	public void delplate(Map<String,Object> map);

	public List<Map<String,Object>> serGoodsList(Map<String,Object> map);

	public Map<String,Object> goodsDetail(Map<String,Object> map);
}
