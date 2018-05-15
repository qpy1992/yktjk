package org.spring.springboot.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.spring.springboot.dao.MeetingDao;
import org.spring.springboot.service.MeetingService;
import javax.annotation.Resource;


@Service
public class MeetingServiceImpl implements MeetingService{
	@Resource
	private MeetingDao meetingDao;

	public List<Map<String,Object>> meetingSearch(Map<String,Object> map)
	{
		return meetingDao.meetingSearch(map);
	}

	@Override
	public Map<String, Object> meetingDetail(Map<String, Object> map) {
		return meetingDao.meetingDetail(map);
	}

	public int insertMeetingRead(Map<String,Object> map)
	{
		return meetingDao.insertMeetingRead(map);
	}
}
