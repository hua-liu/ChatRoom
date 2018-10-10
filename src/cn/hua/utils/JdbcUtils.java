package cn.hua.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
public class JdbcUtils {	//数据库连接
	private static Properties config = new Properties();
	private static DataSource ds;
	static{
		try {
			config.load(JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpConfig.properties"));
			ds = BasicDataSourceFactory.createDataSource(config);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	public static DataSource getDataSource(){
		return ds;
	}
	public static Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
