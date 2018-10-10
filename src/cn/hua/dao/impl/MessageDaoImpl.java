package cn.hua.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import cn.hua.dao.MessageDao;
import cn.hua.domain.Message;
import cn.hua.utils.JdbcUtils;

public class MessageDaoImpl implements MessageDao {
	QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
	public int add(String id,String fid,String content) throws SQLException{	//向数据库添加消息
		String sql = "insert into messages(sender,receiver,time,content) values(?,?,SYSDATE,?)";
		Object[] params = {id,fid,content};
		return runner.update(sql,params);
	}
	public List<Message> getMessage(String id,String fid) throws SQLException{	//向数据库取指定用户消息
		String sql = "select sender,receiver,content,time from messages where state='0' and sender=? and receiver=? order by time";
		Object[] params = {fid,id};
		Connection conn = null;
		List<Message> list = null;
		try{
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);		//开启事务，当消息取走时同时更新消息状态为已读
			list = runner.query(conn,sql, new BeanListHandler<Message>(Message.class) ,params);
			if(list==null){
				return null;
			}
			if(list.size()<1){
				return null;
			}
			int i = update(conn,id,fid);
			if(list.size()<i){		//如果读取到的数据小于状态置为已读的
				conn.rollback();
				return null;
			}
			conn.commit();
		}finally{
			if(conn!=null)
			conn.close();
		}
		return list;
	}
	//更新消息状态为已读
	public int update(Connection conn,String id,String fid) throws SQLException{
		String sql = "update messages set state='1' where sender=? and receiver=? and state='0'";
		Object[] params = {fid,id};
		del();
		return runner.update(sql, params);
	}
	//获取所有消息集合
	public Map<String, Object> getAllMessage(String id) throws SQLException{
		String sql = "select sender,count(*) from MESSAGES where state='0' and RECEIVER=? group by SENDER";
		return runner.query(sql, new MapHandler(){
			Map<String,Object> map = new HashMap<String,Object>();
			@Override
			public Map<String, Object> handle(ResultSet rs) throws SQLException {
				while(rs.next()){
					String key = rs.getString(1);
					Object value = rs.getString(2);
					map.put(key, value);
				}
				return map;
			}
		},id);
	}
	//删除超过一小时且已读的消息
	public void del(){
		String sql = "delete from messages where state='1' and time>SYSDATE+numtodsinterval(1,'hour')";
		try {
			runner.update(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);		
		}
	}
}
