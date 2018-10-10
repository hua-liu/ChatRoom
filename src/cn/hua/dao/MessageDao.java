package cn.hua.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.hua.domain.Message;

public interface MessageDao {

	int add(String id, String fid, String content) throws SQLException;

	List<Message> getMessage(String id, String fid) throws SQLException;

	int update(Connection conn, String id, String fid) throws SQLException;
	
	Map<String, Object> getAllMessage(String id) throws SQLException;

}