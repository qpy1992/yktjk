package org.spring.springboot.service.impl;

import org.spring.springboot.dao.FangkeDao;
import org.spring.springboot.service.FangkeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FangkeServiceImpl implements FangkeService{
    @Resource
    private FangkeDao fangkeDao;

    @Override
    public void invite(Map<String, Object> map) {
        fangkeDao.invite(map);
    }

    @Override
    public List<Map<String, Object>> FkRecord(Map<String, Object> map) {
        return fangkeDao.FkRecord(map);
    }
}
