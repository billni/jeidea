package com.antsirs.core.aop.audit;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;

public class ActionAudit {
	private static final Logger logger = Logger.getLogger(ActionAudit.class.getName());
	/**
	 * @author niyong
	 * log action
	 */
	private long elapsedTime = 0;
	
	public void beforeAudit(JoinPoint jp){
		 Method[] method = jp.getTarget().getClass().getMethods();  
         //获得切点jp这个类里的所有方法(Admin类里的方法)  		   
		elapsedTime  = System.currentTimeMillis();
		logger.fine(method[0].getName() + " start to run at once.");
	}
	
	public void afterAudit(JoinPoint jp){
		Method[] method = jp.getTarget().getClass().getMethods();  
         //获得切点jp这个类里的所有方法(Admin类里的方法)  		   
		elapsedTime = System.currentTimeMillis() - elapsedTime;
		logger.fine(method[0].getName() + " run end, finish time " + elapsedTime);		
	}
}
