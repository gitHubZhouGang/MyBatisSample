package com;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model.dao.TbGkeyMapper;
import com.model.dao.WeightMapper;
import com.model.pojo.TbGkey;
import com.model.pojo.Weight;
import com.model.pojo.WeightExample;

/**
 * @Description: TODO  用来了解mybatis-spring 项目的配置
 * @author Cloop
 *
 * @date 2017年9月11日 下午3:37:43
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="classpath:context.xml") 
public class TestSetting {

	@BeforeClass  
	public static void beforeClass() throws Exception{  
		ClassPathXmlApplicationContext app =new ClassPathXmlApplicationContext("classpath:jndi.xml");  
		DataSource ds =(DataSource) app.getBean("dataSource");  
		SimpleNamingContextBuilder builder =new SimpleNamingContextBuilder();  
		builder.bind("java:comp/env/local", ds);  
		builder.activate();  
	} 
	
	@Autowired
	SqlSessionTemplate sqlSession;
	@Autowired
	WeightMapper wDao;
	@Autowired
	TbGkeyMapper genKeyDao;
	
	/**
	 * defaultFetchSize 查询返回深度
	 *   Mysql不支持FetchSize 所以此值设置无效!
	 */
	@Test
	public void testFetchSize(){
		List<Weight> wtList = wDao.selectByExample(new WeightExample());
		System.out.println("查询出来的长度:"+wtList.size());
	}
	
	/**
	 * 通过获得config来验证Spring-Mybatis设置是否生效
	 *    如果我们使用的mybatis配置文件 则以配置文件为主   如果没有则默认设置!
	 *    
	 *    <property name="configLocation" value="classpath:mybatis.xml"></property>
	 *    <property name="mapperLocations" value="classpath:com/model/mapper/*" />
	 */
	@Test
	public void testGenKey() {
		try {
			Configuration config = sqlSession.getConfiguration();
			System.out.println(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
