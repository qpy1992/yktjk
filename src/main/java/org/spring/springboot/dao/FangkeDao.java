package org.spring.springboot.dao;

import java.util.List;
import java.util.Map;

public interface FangkeDao {
    public void invite(Map<String,Object> map);
    public List<Map<String,Object>> FkRecord(Map<String,Object> map);
}
