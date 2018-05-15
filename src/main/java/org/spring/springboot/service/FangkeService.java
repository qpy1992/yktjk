package org.spring.springboot.service;

import java.util.List;
import java.util.Map;

public interface FangkeService {
    public void invite(Map<String,Object> map);
    public List<Map<String,Object>> FkRecord(Map<String,Object> map);
}
