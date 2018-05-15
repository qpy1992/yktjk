package org.spring.springboot.service.impl;

import java.util.List;
import java.util.Map;
import org.spring.springboot.domain.Plate;
import org.springframework.stereotype.Service;
import org.spring.springboot.dao.UserDao;
import org.spring.springboot.service.UserService;
import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;

	@Override
	public int countMobile(Map<String, Object> map)
	{
		return userDao.countMobile(map);
	}

	@Override
	public int insertProjectuser(Map<String, Object> map) {
		return userDao.insertProjectUser(map);
	}

	@Override
	public int countProjectUser(Map<String, Object> map) {
 		return userDao.countProjectUser(map);
	}



	public Map<String,Object> login(Map<String,Object> map)
	{
		return userDao.login(map);
	}

	public List<Map<String,Object>> serProjectList(Map<String,Object> map)
	{
		return userDao.serProjectList(map);
	}

	public List<Map<String,Object>> serProjectDetailList(Map<String,Object> map) {
		return userDao.serProjectDetailList(map);
	}

	public List<Map<String,Object>> serProjectListByTel(Map<String,Object> map) {
		return userDao.serProjectListByTel(map);
	}

	public int insertProjectuser1(Map<String, Object> map) {
		return userDao.insertProjectUser1(map);
	}

	public int updatePsw(Map<String,Object> map)
	{
		return userDao.updatePsw(map);
	}

	public Map<String,Object> serDetailInfo(Map<String,Object> map) {
		return userDao.serDetailInfo(map);
	}

	public List<Map<String,Object>> serProjectListByTel1(Map<String,Object> map) {
		return userDao.serProjectListByTel1(map);
	}

	@Override
	public void addplate(Map<String, Object> map) {
		userDao.addplate(map);
	}

	@Override
	public List<Plate> getplate(String id) {
		return userDao.getplate(id);
	}

	@Override
	public void cancelplate(Map<String,Object> map) {
		userDao.cancelplate(map);
	}

	@Override
	public void changeplate(Map<String,Object> map) {
		userDao.changeplate(map);
	}

	@Override
	public String getstatus(String id) {
		return userDao.getstatus(id);
	}

	@Override
	public void delplate(Map<String,Object> map) {
		userDao.delplate(map);
	}

	public List<Map<String,Object>> serGoodsList(Map<String,Object> map)
	{
		return userDao.serGoodsList(map);
	}

	@Override
	public Map<String, Object> goodsDetail(Map<String, Object> map) {
		return userDao.goodsDetail(map);
	}
}
