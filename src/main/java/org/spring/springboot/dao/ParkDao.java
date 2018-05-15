package org.spring.springboot.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ParkDao {
    public int ParkIn(Map<String,Object> map);
    public int selectplateNo(Map<String,Object> map);
    public int updateParkout(Map<String,Object> map);
    public Map<String,Object> parkingRecordSearch(Map<String,Object> map);

    public void parkReserve(Map<String,Object> map);

    public List<Map<String,Object>> payRecord(Map<String,Object> map);

    public void lock(Map<String,Object> map);

    public List<String> searchPlate(String userid);

    public List<Map<String,Object>> searchStatus(List<String> list);

    public void pay(Map<String,Object> map);
}
