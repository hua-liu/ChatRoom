package cn.hua.dao;

import java.sql.SQLException;

public interface SetDao {

	String getSound(String id) throws SQLException;

	int setSound(String id, String sid) throws SQLException;

	String getPicture(String id) throws SQLException;

	int setPicture(String id, String pid) throws SQLException;

}