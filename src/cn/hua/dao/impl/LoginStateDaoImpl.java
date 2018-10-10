package cn.hua.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import cn.hua.dao.LoginStateDao;
import cn.hua.utils.JdbcUtils;

public class LoginStateDaoImpl implements LoginStateDao {
	QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
	public int add(String id) throws SQLException{		//增加登陆状态
		String sql = "INSERT INTO loginstate(id,time) values(?,SYSDATE)";
		return runner.update(sql, id);
	}
	public int del() throws SQLException{		//0.00045*8.65 == 39秒，，删除所有超时没更新状态用户
		String sql = "DELETE from loginstate where time<SYSDATE-0.00070";
		return runner.update(sql);
	}
	@Override
	public int find(String id) throws SQLException {	//查找登陆状态表里用户
		String sql = "SELECT count(*) from loginstate where id=?";
		return runner.query(sql, new ResultSetHandler<Integer>(){
			@Override
			public Integer handle(ResultSet rs) throws SQLException {
				if(rs.next()){
					return rs.getInt(1);
				}
				return 0;
			}
			
		},id);
	}
	public int update(String id) throws SQLException{	//更新登陆状态
		String sql = "update loginstate set time=SYSDATE where id=?";
		return runner.update(sql, id);
	}
}
