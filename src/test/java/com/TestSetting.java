package com;

import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model.dao.WeightMapper;
import com.model.pojo.Weight;
import com.model.pojo.WeightExample;

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
	WeightMapper wDao;
	
	@Test
	public void testConntect(){
		List<Weight> wtList = wDao.selectByExample(new WeightExample());
		System.out.println("查询出来的长度:"+wtList.size());
	}
}
