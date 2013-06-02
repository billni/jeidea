package com.antsirs.train12306.job;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.antsirs.train12306.action.Crawl12306Action;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:applicationContext-*.xml"})
public class TicketTest{
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
	 * 测试证件和姓名相同	
	 * @throws Exception 
	 */
	@Test
	@Rollback(false)
	public void testRecordTicket() throws Exception{
    	Crawl12306Action job = new Crawl12306Action();
    	String html = job.getHtmlByUrl(job.initUrl().toString());
    	logger.info("Get html from Url:" + html);
    	JSONObject jsonObject = new JSONObject(html);
    	String result = jsonObject.get("datas").toString();
    	
    	List<Ticket> list = job.generateRecords(result);
    	for (Ticket ticket : list) {    		
    		trainTicketManagerService.createTicket(ticket);
		}

    	//0,<span id='id_24000000T50E' class='base_txtdiv' onmouseover=javascript:onStopHover('24000000T50E#BXP#LZZ') onmouseout='onStopOut()'>T5</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;15:45,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;16:41,24:56,--,--,--,--,--,8,5,--,<font color='darkgray'>无</font>,150,--,\n1,<span id='id_240000T18909' class='base_txtdiv' onmouseover=javascript:onStopHover('240000T18909#BXP#LZZ') onmouseout='onStopOut()'>T189</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;18:17,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;20:00,25:43,--,--,--,--,12,11,<font color='darkgray'>无</font>,--,9,10,--,\n2,<span id='id_240000K1570Q' class='base_txtdiv' onmouseover=javascript:onStopHover('240000K1570Q#BXP#LZZ') onmouseout='onStopOut()'>K157</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;18:26,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;01:18,30:52,--,--,--,--,--,11,<font color='darkgray'>无</font>,--,<font color='darkgray'>无</font>,193,--,
    	String time = jsonObject.get("time").toString();
    	
    	try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace(); 
        }
	}	
}
