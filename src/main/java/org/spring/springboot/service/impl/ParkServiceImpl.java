package org.spring.springboot.service.impl;

import org.spring.springboot.dao.ParkDao;
import org.spring.springboot.service.ParkService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ParkServiceImpl implements ParkService{
    @Resource
    private ParkDao parkDao;

    public int ParkIn(Map<String,Object> map){
        return parkDao.ParkIn(map);
    }
    public int selectplateNo(Map<String,Object> map){
        return parkDao.selectplateNo(map);
    }
    public int updateParkout(Map<String,Object> map){
        return parkDao.updateParkout(map);
    }
    public Map<String,Object> parkingRecordSearch(Map<String,Object> map){
        return parkDao.parkingRecordSearch(map);
    }
    @Override
    public void parkReserve(Map<String,Object> map){
        parkDao.parkReserve(map);
    }

    @Override
    public List<Map<String, Object>> payRecord(Map<String, Object> map) {
        return parkDao.payRecord(map);
    }

    @Override
    public void lock(Map<String,Object> map) {
        parkDao.lock(map);
    }

    @Override
    public List<String> searchPlate(String userid) {
        return parkDao.searchPlate(userid);
    }

    @Override
    public List<Map<String, Object>> searchStatus(List<String> list) {
        return parkDao.searchStatus(list);
    }

    @Override
    public void pay(Map<String, Object> map) {
        parkDao.pay(map);
    }
}
