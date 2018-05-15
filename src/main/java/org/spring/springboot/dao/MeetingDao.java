package org.spring.springboot.dao;

import java.util.List;
import java.util.Map;

public interface MeetingDao {

	public List<Map<String,Object>> meetingSearch(Map<String,Object> map);
	public Map<String,Object> meetingDetail(Map<String,Object> map);
	public int insertMeetingRead(Map<String,Object> map);
}
