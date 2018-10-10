package cn.hua.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import cn.hua.dao.SetDao;
import cn.hua.utils.JdbcUtils;

public class SetDaoImpl implements SetDao {
	QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
	public String getSound(String id) throws SQLException{	//获取当前用户的铃声
		String sql="select s.url from users u,sound s where u.id=? and u.sound=s.id";
		return runner.query(sql, new ResultSetHandler<String>(){
			@Override
			public String handle(ResultSet rs) throws SQLException {
				if(rs.next()){
					return rs.getString(1);
				}
				return null;
			}
			
		},id);
	}
	public int setSound(String id,String sid) throws SQLException{	//设置当前用户铃声
		String sql="update users set sound=? where id=?";
		Object[] param = {sid,id};
		return runner.update(sql,param);
	}	
	public String getPicture(String id) throws SQLException{	//获取当前用户背景
		String sql="select p.url from users u,picture p where u.id=? and u.picture=p.id";
		return runner.query(sql, new ResultSetHandler<String>(){
			@Override
			public String handle(ResultSet rs) throws SQLException {
				if(rs.next()){
					return rs.getString(1);
				}
				return null;
			}
		},id);
	}
	public int setPicture(String id,String pid) throws SQLException{//设置当前用户背景
		String sql="update users set picture=? where id=?";
		Object[] param = {pid,id};
		return runner.update(sql,param);
	}
}
