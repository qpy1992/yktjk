package org.spring.springboot.service;

import org.spring.springboot.domain.EgDetail;

import java.util.List;
import java.util.Map;

public interface MenjinService {
    public List<EgDetail> EgDetail(Map<String,Object> map);
}
