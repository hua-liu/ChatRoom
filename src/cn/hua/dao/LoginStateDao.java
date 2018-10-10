package cn.hua.dao;

import java.sql.SQLException;

public interface LoginStateDao {

	int add(String id) throws SQLException;

	int del() throws SQLException;
	
	int find(String id) throws SQLException;
	
	int update(String id) throws SQLException;
}