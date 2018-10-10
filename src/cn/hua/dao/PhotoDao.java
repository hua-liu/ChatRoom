package cn.hua.dao;

import java.sql.SQLException;
import java.util.List;

import cn.hua.domain.Photo;

public interface PhotoDao {

	int add(Photo p) throws SQLException;

	Photo find(String id) throws SQLException;

	List<Photo> getSysPhoto() throws SQLException;
}