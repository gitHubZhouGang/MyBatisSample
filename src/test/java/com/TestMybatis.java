package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.Test;

import com.model.dao.WeightMapper;

/**
 * 用此类来了解Mybatis的源码
 * 
 * @author Cloop
 *
 */
public class TestMybatis {

	/**
	 * 了解Mybatis Configuration
	 * 
	 * @see Configuration
	 */
	@Test
	public void testConfiguration() {
		/**
		 * Mybatis配置文件案例 参见mybatis.xml 含有： properties 属性 settings 设置 typeAliases
		 * 类型别名 typeHandlers 类型处理器 objectFactory 对象工厂 plugins 插件 environments 环境
		 * environment 环境变量 transactionManager 事务管理器 dataSource 数据源
		 * databaseIdProvider 数据库厂商标识 mappers 映射器
		 * 每个节点的用途参见：http://www.mybatis.org/mybatis-3/zh/configuration.html
		 */

	}

	/**
	 * 了解Mybatis的基础概念 SqlSessionFactoryBuilder
	 * 
	 * @see SqlSessionFactoryBuilder
	 * @see XMLConfigBuilder
	 */
	public SqlSessionFactory getSqlSessionFactory() {
		InputStream in = null;
		try {
			/**
			 * SqlSessionFactoryBuilder 主要用于构建SqlSessionFactory
			 * 1,SqlSessionFactory调用build方法开始构建 我们可以传送Mybatis文件的输入流
			 * 2,通过XMLConfigBuilder 解析配置文件然后吧配置注入到Configuration对象中 3,最后通过 new
			 * DefaultSqlSessionFactory(config)来构建出SqlSessionFactory
			 */
			SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
			in = new FileInputStream(new File("mybatis.xml"));
			SqlSessionFactory sessionFactory = sessionFactoryBuilder.build(in);
			return sessionFactory;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 了解SqlSession
	 * 
	 * @see SqlSessionFactory
	 * @see SqlSession
	 * @see DefaultSqlSession
	 */
	@Test
	public void testSqlSession() {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		/**
		 * sqlSessionFactory 调用openSession构建SqlSession实例
		 * openSession()的主要调用还是在openSessionFromDataSource(ExecutorType execType,
		 * TransactionIsolationLevel level, boolean autoCommit) 然后new
		 * DefaultSqlSession(configuration, executor, autoCommit);
		 */
		SqlSession sqlSession = sqlSessionFactory.openSession();
		/**
		 * SqlSession 的工作方式主要分为两种 1,直接调用 SqlSession中定义的方法 直接传入sql语句
		 * 2,通过getMapper方法 结合Mapper接口定义和Mapper sql映射文件来执行sql
		 */
		sqlSessionWorkType1(sqlSession);//主要为了演示   先不做关闭
		sqlSessionWorkType2(sqlSession);
		sqlSession.close();
	}

	/**
	 * 直接执行Sql语句
	 * @param sqlSession
	 */
	public void sqlSessionWorkType1(SqlSession sqlSession) {
		List<Object> oList = sqlSession.selectList("select * from mttable");
	}

	public void sqlSessionWorkType2(SqlSession sqlSession) {
		WeightMapper wtMapper = sqlSession.getMapper(WeightMapper.class);
	}

}
