package com.bossteach.core.struts.actionsuppport;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author niyong
 * @since 2013-1-23
 * @version $Revision:  $, $Date:  $
 */
public abstract class BaseActionSupport extends ActionSupport implements ServletContextAware,ServletResponseAware,ServletRequestAware,SessionAware{

	/**
	 * all  action must extend AbstractAction, it provide  Log,Session,ServletContext,
	 * HttpServletRequest,HttpServletResponse Object for others action .
	 */	
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	 
    protected Map<?, ?> session ;
    
    public void setSession(Map<String,Object> session){
    	
    	this.session=session;
    }
    
    
    protected ServletContext context;

    public void setServletContext(ServletContext context){
    	this.context=context;
    };
    
    
    protected HttpServletRequest request;
    
    public void setServletRequest(HttpServletRequest request){
    	this.request=request;
    };
    
    protected HttpServletResponse response;
    public void setServletResponse(HttpServletResponse response){
    	this.response=response;
    }
	public HttpServletRequest getServletRequest() {
		return request;
	};
    
    
   
}
