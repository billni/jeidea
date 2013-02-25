package com.bossteach.core.util.ci;

import com.bossteach.core.spring.applicationsupport.ApplicationContextSupport;

public class ComponentInterfaceFactory extends ApplicationContextSupport {
	
	public static Object getComponentInteface(String intefaceName){	
		return getBean(intefaceName);		
	}

}
