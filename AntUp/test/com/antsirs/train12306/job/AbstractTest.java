package com.antsirs.train12306.job;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:applicationContext-*.xml"})
public class AbstractTest {
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());   
	      
	@Before  
	public void setUp() {
		helper.setEnvAppId("AntSirs");
		helper.setEnvVersionId("1");
		helper.setEnvAuthDomain("gmail.com");
	    helper.setUp();   
	}   
	      
	@After 
	public void tearDown() {   
	     helper.tearDown();   
	} 
}
