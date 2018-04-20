package com.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {

	private volatile static SqlSessionFactory sessionFactory;

	private MyBatisUtil() {}

	public static SqlSessionFactory getSessionFactory() throws IOException {
		if (sessionFactory == null) {
			synchronized (MyBatisUtil.class) {
				if (sessionFactory == null) {
					String resource = "mybatis-config.xml";
					Reader reader = Resources.getResourceAsReader(resource);
					sessionFactory = new SqlSessionFactoryBuilder().build(reader);
				}
			}
		}
		return sessionFactory;
	}
	
	public static SqlSession openSession() throws IOException {
		return getSessionFactory().openSession();
	}
	
}
