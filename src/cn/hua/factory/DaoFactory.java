package cn.hua.factory;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
	private static Properties config = new Properties();
	private DaoFactory(){
		try {
			config.load(DaoFactory.class.getClassLoader().getResourceAsStream("dao.properties"));
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	};
	private static DaoFactory dao = new DaoFactory();
	public static DaoFactory instance(){
		return dao;
	}
	public static <T> T createDao(Class<T> clazz){
		String name = clazz.getSimpleName();
		String value = config.getProperty(name);
		try {
			return (T) Class.forName(value).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
