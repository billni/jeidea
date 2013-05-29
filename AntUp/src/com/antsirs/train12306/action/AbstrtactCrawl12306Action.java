package com.antsirs.train12306.action;

import org.springframework.beans.factory.annotation.Autowired;
import com.antsirs.core.struts.actionsuppport.BaseActionSupport;
import com.antsirs.train12306.service.TrainTicketManagerService;

public abstract class AbstrtactCrawl12306Action extends BaseActionSupport{
	
	@Autowired
	public TrainTicketManagerService trainTicketManagerService ;
	
	
}
