package cn.hua.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import cn.hua.dao.PhotoDao;
import cn.hua.domain.Photo;
import cn.hua.utils.JdbcUtils;

public class PhotoDaoImpl implements PhotoDao {
	private QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
	public int add(Photo p) throws SQLException{	//添加头像
		String sql = "insert into photo(id,name,url,issys) values(?,?,?,?)";
		Object[] params = {p.getId(),p.getName(),p.getUrl(),p.getIsSys()};
		return runner.update(sql, params);
	}
	public Photo find(String id) throws SQLException{	//查找头像
		String sql = "select id,name,url,issys from photo where id=?";
		return runner.query(sql, new BeanHandler<Photo>(Photo.class),id);
	}
	@Test
	public List<Photo> getSysPhoto() throws SQLException{	//获取头像
		String sql = "select id,name,url,issys from photo where issys='1'";
		return runner.query(sql, new BeanListHandler<Photo>(Photo.class));
	}
	public Photo isExist(String type,String value) throws SQLException{	//获取头像详细信息，应该没有用到此方法
		String sql = "select id,name,url,issys from photo where ?=?";
		Object[] params = {type,value};
		return runner.query(sql, new BeanHandler<Photo>(Photo.class),params);
	}
}
