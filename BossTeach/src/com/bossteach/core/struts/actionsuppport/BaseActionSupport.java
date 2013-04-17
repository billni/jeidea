package com.bossteach.core.struts.actionsuppport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.bossteach.core.spring.daosupport.Pagination;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author niyong
 * @since 2013-1-23
 * @version $Revision:  $, $Date:  $
 */
public abstract class BaseActionSupport extends ActionSupport implements ServletContextAware,ServletResponseAware,ServletRequestAware,SessionAware{
	public JSONObject resultObj;
	
	public JSONObject getResultObj() {
		return resultObj;
	}


	/**
	 * all  action must extend AbstractAction, it provide  Log,Session,ServletContext,
	 * HttpServletRequest,HttpServletResponse Object for others action .
	 */	
	protected DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
	
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
    
	public void setResultObj(List list) {
		  Map<String, Object> jsonMap = new HashMap<String, Object>();	     
	      jsonMap.put("total", list.toArray().length); 
	      jsonMap.put("rows", list);	      
	      resultObj = new JSONObject(jsonMap);
	}
	
	
	public Pagination pagination = new Pagination();
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public Pagination getPagination() {
		    int page = 1;
		    int rows = 0;
		    if (request.getParameter("page")!=null){
		    	page = Integer.parseInt(request.getParameter("page"));
		    }
		    if (request.getParameter("rows")!=null){
		    	rows = Integer.parseInt(request.getParameter("rows"));
		    }
			pagination.setFirstResult(page * rows - rows);
			pagination.setMaxResults(rows);	
			
		return this.pagination;
	}
   
}
