package cn.hua.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.hua.dao.FriendDao;
import cn.hua.domain.Friend;
import cn.hua.domain.Users;
import cn.hua.utils.JdbcUtils;

public class FriendDaoImpl implements FriendDao{
	QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
	@Override	//添加好好
	public int add(String myId, String fId, String kind) throws SQLException {
		String sql = "insert into friend(id,friend_id,kind) values(?,?,?)";
		Object[] params = {myId,fId,kind};
		return runner.update(sql, params);
	}

	@Override	//删除好友
	public int del(String myId, String fId) throws SQLException {
		String sql = "delete from friend where id=? and friend_id=?";
		Object[] params = {myId,fId};
		return runner.update(sql, params);
	}

	@Override	//更新好友分组
	public int update(String myId, String fId, String kind) throws SQLException {
		String sql = "updata friend set kind=? where id=? and friend_id=?";
		Object[] params = {kind,myId,fId};
		return runner.update(sql, params);
	}

	@Override	//获取好友集合
	public List<Friend> getFriends(String myId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Friend> list;
		try{	//查询自己id,用户名，昵称，头像ID，描述，好友ID，分组
			String sql = "select u.id,u.username,u.nickname,u.photoid,u.description,f.friend_id,f.kind from friend f,users u where f.id=? and u.id=f.friend_id order by f.kind";
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, myId);
			rs = ps.executeQuery();
			MyHandler mh = new MyHandler(Friend.class);	//使用自定义处理器处理结果
			list = mh.handle(rs);//使用自定义处理器处理结果
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
		}
		return list;
	}

	@Override	//获取总好友数
	public int totalFriends() throws SQLException {
		String sql = "select count(*) count from friend";
		runner.query(sql, new ResultSetHandler<Integer>(){
			@Override
			public Integer handle(ResultSet rs) throws SQLException {
				if(rs.next()){
					return rs.getInt("count");
				}
				return 0;
			}
		});
		return 0;
	}	//获取所有分组
	public List<String> getKind(String id) throws SQLException{
		String sql = "select kind from friend where id=? group by kind";
		return runner.query(sql, new ResultSetHandler<List<String>>(){
			@Override
			public List<String> handle(ResultSet rs) throws SQLException {
				List<String> list = new LinkedList<String>();
				while(rs.next()){
					String kind = rs.getString("kind");
					list.add(kind);
				}
				if(list.size()<1){
					return null;
				}
				return list;
			}
			
		},id);
	}
	//查询好友是否已存在
	public int isExist(String id,String fid) throws SQLException{
		String sql="select count(*) from friend where id=? and friend_id=?";
		Object[] param = {id,fid};
		return runner.query(sql, new ResultSetHandler<Integer>(){
			@Override
			public Integer handle(ResultSet rs) throws SQLException {
				if(rs.next()){
					return rs.getInt(1);
				}
				return 0;
			}
			
		},param);
	}
}
	//自定义处理结果类
class MyHandler extends BeanListHandler<Friend>{
	public MyHandler(Class<Friend> type) {
		super(type);
	}

	@Override
	public List<Friend> handle(ResultSet rs) throws SQLException {
		String kind="";
		List<Friend> listF = null;
		List<Users> list=null;
		Friend friend=null;
		Users user = null;
		if(rs.next()){		//如果有数据则创建对象
			list = new ArrayList<Users>();
			friend = new Friend();
			user = new Users();
			listF = new ArrayList<Friend>();
			rs.beforeFirst();
		}
		while(rs.next()){
			if(rs.getString("kind").equals(kind)){	//如果分类等于当前分类，则继续读取向list里添加user
				user.setId(rs.getString("id"));
				user.setUsername(rs.getString("username"));
				user.setNickname(rs.getString("nickname"));
				user.setPhotoid(rs.getString("photoid"));
				user.setDescription(rs.getString("description"));
				list.add(user);
				user = new Users();
			}else{		//如果分类不等于当前分类，
				if(list.size()>0){		//则判断list是否大于0，大于则将
					friend.setList(list);		//list加入friend对象
					listF.add(friend);		//再将friend加入listF集合
					list= new ArrayList<Users>();
					friend=new Friend();
				}
				kind = rs.getString("kind");	//将读取到的分类设置为当前分类
				friend.setKind(kind);		//将当前分类加入friend
				user.setId(rs.getString("id"));
				user.setUsername(rs.getString("username"));
				user.setNickname(rs.getString("nickname"));
				user.setPhotoid(rs.getString("photoid"));
				user.setDescription(rs.getString("description"));
				list.add(user);
				user = new Users();
			}
			
		}
		if(list==null){
			return null;
		}
		if(list.size()>0){		//则判断list是否大于0，大于则将
			friend.setList(list);		//list加入friend对象
			listF.add(friend);		//再将friend加入listF集合
			list= null;
			friend=null;
		}
		return listF;
	}
}
