package org.spring.springboot.dao;

import org.spring.springboot.domain.EgDetail;

import java.util.List;
import java.util.Map;

public interface MenjinDao {
    public List<EgDetail> EgDetail(Map<String,Object> map);
}
