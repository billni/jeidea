package com.antsirs.core.util.ci;

import com.antsirs.core.spring.applicationsupport.ApplicationContextSupport;

public class ComponentInterfaceFactory extends ApplicationContextSupport {
	
	public static Object getComponentInteface(String intefaceName){	
		return getBean(intefaceName);		
	}

}
