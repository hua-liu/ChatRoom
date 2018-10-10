package cn.hua.dao;

import java.sql.SQLException;
import java.util.List;

import cn.hua.domain.Friend;
import cn.hua.domain.Users;

public interface FriendDao {
	
	int add(String myId,String fId,String kind) throws SQLException;

	int del(String myId,String fId) throws SQLException;
	
	int update(String myId,String fId,String kind) throws SQLException;

	List<Friend> getFriends(String myId) throws SQLException;

	int totalFriends() throws SQLException;
	
	List getKind(String id) throws SQLException;
	
	int isExist(String id,String fid) throws SQLException;
}