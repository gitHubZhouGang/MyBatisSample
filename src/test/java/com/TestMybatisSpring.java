package com;

import java.lang.reflect.Proxy;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @Description: TODO 了解mybatis-spring项目运行原理,运行步骤
 * @author Cloop
 *
 * @date 2017年9月11日 下午3:40:48
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="classpath:context.xml") 
public class TestMybatisSpring {

	@BeforeClass  
	public static void beforeClass() throws Exception{  
		ClassPathXmlApplicationContext app =new ClassPathXmlApplicationContext("classpath:jndi.xml");  
		DataSource ds =(DataSource) app.getBean("dataSource");  
		SimpleNamingContextBuilder builder =new SimpleNamingContextBuilder();  
		builder.bind("java:comp/env/local", ds);  
		builder.activate();  
	} 
	
	
	
	/**
	 * mybatis-spring 初始化
	 * 和正常的orm框架一样   第一 dataSource  第二 sessionFactory 第三 session
	 * @see DataSource
	 * @see SqlSessionFactoryBean
	 * @see SqlSessionFactory
	 * @see SqlSessionTemplate
	 * @see SqlSession
	 */
	@Test
	public void initMybatisSpring() {
		//dataSource 是标准接口
		/**
		 * SqlSessionFactoryBean 是mybatis-spring用来创建mybatis的SqlSessionFactory的
		 *  1,设置参数   mybatis配置文件路径  映射文件路径 或者直接在此Bean里面色画质Mybatis的参数
		 *  2, SqlSessionFactory实现了Spring的FactoryBean接口 则最终创建的是getObjecct()方法返回的值  SqlSessionFactory
		 *  3, 将SqlSessionFactory注入到SqlSessionTemplate 由Spring产生一个SqlSession
		 */
	}
	
	/**
	 * SqlSessionFactory 的创建
	 */
	@Test
	public void initSqlSessionFactory() {
		/**
		 * 1,在Spring初始化SqlSessionFactoryBean的时候会先把类属性先设置进去 eg. mybatis的外部配置文件  mapper.xml的sql配置文件 或者mubatis的配置属性
		 * 2,SqlSessionFactoryBean.getObject()
		 * 3,SqlSessionFactoryBean.afterPropertiesSet()
		 * 4,然后  SqlSessionFactoryBean.buildSqlSessionFactory()
		 *    buildSqlSessionFactory() 先通过configLocal属性找到Mybatis的外部配置文件 然后加载-->Configuration
		 *    然后逐一判断Configuration的属性是否设置 如果没有设置  但是Spring注入到了sqlSessionFactoryBean中,则覆盖设置
		 * 5,SqlSessionFactoryBuilder.build(configuration);
		 *      
		 */
	}
	
	/**
	 * SqlSessionTemplate 的创建
	 * 	@see SqlSessionFactory
	 *  @see ExecutorType
	 *  @see PersistenceExceptionTranslator
	 *  @see Proxy
	 */
	@Test
	public void initSqlSessionTemplate() {
		/**
		 * SqlSessionTemplate 有三个构造函数:
		 *  1,带SqlSessionFactory的构造函数
		 *  2,带SqlSessionFactory+ExecutorType的构造函数
		 *  3,带SqlSessionFactory+ExecutorType+PersistenceExceptionTranslator的构造函数
		 */
		/**
		 * 我们使用第一种构造函数:
		 *  1,在我们没有指定ExecutorType时 会直接从SqlSessionFactory.Configuration.DefaultExecutorType
		 *  2,调用第二构造函数 指定MyBatisExceptionTranslator
		 *  3,调用第三构造函数
		 *      使用动态代理方式产生SqlSession的代理对象newProxyInstance(SqlSessionFactory.class.getClassLoader(),new Class[] { SqlSession.class },new SqlSessionInterceptor());
		 *      (动态代理的原理 参见JavaSe项目 基础)
		 *  4,通过静态代理获得目标类之后我们就可以操作目标类  也就是SqlSession
		 */
		
		/**
		 * 在操作由代理实例化的时候  我看代理类到底为我我们做了什么呢?
		 * @see SqlSessionTemplate$SqlSessionInterceptor
		 * 
		 * 嘿嘿  这里就隐藏着Spring对事务管理的结点  细的就不说了,自己看源代码咯
		 * 
		 * 至此MybatisSpring项目源代码  以及原理就差不多了!   下面就看Mybatis源码还有Spring源码咯
		 */
		
	}
	
}
