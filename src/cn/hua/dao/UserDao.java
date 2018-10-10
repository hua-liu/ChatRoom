package cn.hua.dao;

import java.sql.SQLException;
import java.util.List;

import cn.hua.domain.Users;

public interface UserDao {

	int add(Users user) throws SQLException;

	Users find(String id) throws SQLException;
	
	int update(Users user) throws SQLException;

	List<Users> getAllUser() throws SQLException;

	Users isExist(String type, String value) throws SQLException;

	Users verifyUP(String username, String password) throws SQLException;

	int totalUsers() throws SQLException;
	
	public String getPS(String id) throws SQLException;
}