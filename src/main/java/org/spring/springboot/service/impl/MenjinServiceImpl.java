package org.spring.springboot.service.impl;

import org.spring.springboot.dao.MenjinDao;
import org.spring.springboot.domain.EgDetail;
import org.spring.springboot.service.MenjinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MenjinServiceImpl implements MenjinService {
    @Resource
    private MenjinDao menjinDao;

    @Override
    public List<EgDetail> EgDetail(Map<String, Object> map) {
        return menjinDao.EgDetail(map);
    }
}
