package cn.hua.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.hua.domain.Friend;
import cn.hua.domain.Message;
import cn.hua.domain.Photo;
import cn.hua.domain.Users;

public interface BusinessService {

	boolean addPhoto(Photo p);

	Photo findPhoto(String id);

	List<Photo> getSysPhoto();
	
	boolean addUser(Users user);
	
	boolean updateUser(Users user);

	Users findUser(String id);

	List<Users> getAllUser();

	Users isExistUser(String type, String value);

	Users verifyUP(String username, String password);

	int totalUsers();
	
	String getPS(String id);
	
	boolean addFriend(String myId,String fId,String kind);

	boolean delFriend(String myId,String fId);
	
	int updateFriend(String myId,String fId,String kind);
	
	boolean isExistFriend(String id,String fid);

	List<Friend> getFriends(String myId);

	int totalFriends() throws SQLException;
	
	List getKind(String id);
	
	boolean addLoginState(String id);

	boolean delLoginState();
	
	boolean findLoginState(String id);
	
	boolean updateLoginState(String id);
	
	boolean addMessage(String id, String fid, String content);

	List<Message> getMessage(String id, String fid);

	Map<String, Object> getAllMessage(String id);
	
	String getSound(String id);

	boolean setSound(String id, String sid);

	String getPicture(String id);

	boolean setPicture(String id, String pid);
}