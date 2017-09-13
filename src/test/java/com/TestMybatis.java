package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.Test;

import com.model.dao.WeightMapper;
import com.model.pojo.Weight;

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
			 * 2,通过XMLConfigBuilder 解析配置文件然后吧配置注入到Configuration对象中 
			 * 3,最后通过 new DefaultSqlSessionFactory(config)来构建出SqlSessionFactory
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
	 * 直接执行Sql语句 老版的
	 * @param sqlSession
	 * @see SqlSession
	 * @see DefaultSqlSession
	 * @see Configuration
	 * @see MapperRegistry
	 */
	public void sqlSessionWorkType1(SqlSession sqlSession) {
		/**
		 * 这里就不去解释怎么去执行这个sql的了   原理会在第二工作方式里面一起说
		 */
		List<Object> oList = sqlSession.selectList("select * from mttable");
	}

	/**
	 *  为了顺应面向对象  Mybatis采取抽象Mapper接口和Mapper.xml 的sql配置文件结合来工作
	 * @see MapperProxyFactory
	 * @param sqlSession
	 */
	public void sqlSessionWorkType2(SqlSession sqlSession) {
		WeightMapper wtMapper = sqlSession.getMapper(WeightMapper.class);
		Weight myWT = wtMapper.selectByPrimaryKey("test");
		
		/**
		 * getMapper  动态获得Mapper具体实现类
		 * 
		 * 1,SqlSession的getMapper(Class<T> clazz)主要是我为了从Configration种获得Mapper接口的实现类
		 *   	configuration.<T>getMapper(type, this);
		 * 2,在Configuration中getMapper(Class<T> type, SqlSession sqlSession)实现是:
		 *  	mapperRegistry.getMapper(type, sqlSession)
		 * 3,MapperRegistry是什么从哪来的?用来干嘛的呢?
		 * 		>1在开始构建Configuration的时候我们解析了配置文件,这个MapperRegistry是和配置文件的Mapper结点有关的
		 *      >2在XMLConfigBuilder中mapperElement(XNode parent)是用来解读mappers结点的,mappers结点类型分为 package和url  resource类型'
		 *      >3在解析到配置mapper的结点值之后 configuration.addMapper(mapperInterface);  
		 *      >4在Configuration中默认构建了MapperRegistry mapperRegistry = new MapperRegistry(this);
		 *      >5MapperRegistry addMapper(Class<T> type)-->knownMappers.put(type, new MapperProxyFactory<T>(type));
		 *      >6MapperProxyFactory 是用来动态代理的代理类;
		 *      >7mapperRegistry.getMapper(type, sqlSession);返回的是 MapperProxyFactory<T>mapperProxyFactory.newInstance(sqlSession);
		 *      >8然后根据接口动态代理生成Mapper具体实现类对象
		 */
		
	}

}
