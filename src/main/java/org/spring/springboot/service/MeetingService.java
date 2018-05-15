package org.spring.springboot.service;

import java.util.List;
import java.util.Map;

public interface MeetingService {
	public List<Map<String,Object>> meetingSearch(Map<String,Object> map);
	public Map<String,Object> meetingDetail(Map<String,Object> map);
	public int insertMeetingRead(Map<String,Object> map);
}
