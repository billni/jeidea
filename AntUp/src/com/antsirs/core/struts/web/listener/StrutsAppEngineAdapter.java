package com.antsirs.core.struts.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import ognl.OgnlRuntime;

public class StrutsAppEngineAdapter implements ServletContextListener{
	public void contextInitialized(ServletContextEvent servletContextEvent) {
			OgnlRuntime.setSecurityManager(null);
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent){
		
	}
}