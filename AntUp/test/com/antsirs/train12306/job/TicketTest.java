package com.antsirs.train12306.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.antsirs.train12306.action.Crawl12306Action;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:applicationContext-*.xml"})
public class TicketTest {
	private Log logger = LogFactory.getLog(TicketTest.class);
	@Autowired
	private TrainTicketManagerService trainTicketManagerService;
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(   
	          new LocalDatastoreServiceTestConfig());   
	      
	@Before  
	public void setUp() {   
	      helper.setUp();   
	}   
	      
	@After 
	public void tearDown() {   
	     helper.tearDown();   
	} 
		
	/**
	 * 
	 * @throws Exception 
	 */
	@Test
//	@Rollback(false)
	public void testCrawlTrainTicket() throws Exception{
    	Crawl12306Action job = new Crawl12306Action();
    	logger.debug("Test start");
    	job.execute();
    	logger.debug("Test finish");
	}	
}
