package cn.hua.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.hua.dao.UserDao;
import cn.hua.domain.Users;
import cn.hua.utils.JdbcUtils;

public class UserDaoImpl implements UserDao {
	private QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
	public int add(Users user) throws SQLException{	//增加用户
		String sql = "insert into users(id,username,password,nickname,photoid) values(?,?,?,?,?)";
		Object[] params = {user.getId(),user.getUsername(),user.getPassword(),user.getNickname(),user.getPhotoid()};
		return runner.update(sql, params);
	}
	public int update(Users user) throws SQLException{	//更新用户
		String sql = "update users set nickname=?,description=? where id=?";
		Object[] params={user.getNickname(),user.getDescription(),user.getId()};
		return runner.update(sql, params);
	}
	public Users find(String id) throws SQLException{	//查找用户
		String sql = "select id,username,nickname,photoid,description,sound,picture from users where id=?";
		Users user = runner.query(sql, new BeanHandler<Users>(Users.class),id);
		return getSP(user);
	}
	public List<Users> getAllUser() throws SQLException{	//获取所有用户集合
		String sql = "select id,username,nickname,photoid,description from users";
		return runner.query(sql, new BeanListHandler<Users>(Users.class));
	}
	public Users isExist(String type,String value) throws SQLException{	//查询指定类型用户是否存在
		String sql = "select id,username,nickname,photoid,description from users where "+type+"=?";
		return runner.query(sql, new BeanHandler<Users>(Users.class),value);
	}
	public Users verifyUP(String username,String password) throws SQLException{	//登陆验证
		String sql = "select id,username,nickname,photoid,description,sound,picture from users where username=? and password=?";
		Object[] params = {username,password};
		return runner.query(sql, new BeanHandler<Users>(Users.class),params);
		//sreturn getSP(user);
	}
	public int totalUsers() throws SQLException{		//获取总用户数
		String sql = "select count(*) from users";
		return runner.query(sql, new ResultSetHandler<Integer>(){	//返回结果为一个整数
			@Override
			public Integer handle(ResultSet rs) throws SQLException {
				rs.next();
				return rs.getInt(1);
			}
			
		});
	}
	public String getPS(String id) throws SQLException{		//获取当前用户密码
		String sql = "select password from users where id=?";
		return runner.query(sql, new ResultSetHandler<String>(){	
			@Override
			public String handle(ResultSet rs) throws SQLException {
				if(rs.next())
				return rs.getString("password");
				return null;
			}
			
		},id);
	}
	public Users getSP(Users user) throws SQLException{		
		String sql = "select * from sound where id=?";//获取当前用户铃声名字
		String sname = runner.query(sql, new ResultSetHandler<String>(){

			@Override
			public String handle(ResultSet rs) throws SQLException {
				if(rs.next()){
					return rs.getString("name");
				}
				return null;
			}
			
		},user.getSound());
		sql = "select * from picture where id=?";//获取当前背景铃声名字
		String pname = runner.query(sql, new ResultSetHandler<String>(){

			@Override
			public String handle(ResultSet rs) throws SQLException {
				if(rs.next()){
					return rs.getString("name");
				}
				return null;
			}
			
		},user.getPicture());
		user.setSname(sname);
		user.setPname(pname);
		return user;
	}
}
