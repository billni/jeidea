package com.bossteach.core.struts.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterEncoding implements Filter {
	protected String encoding; 
	protected FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		// read encoding from web.xml
		encoding = filterConfig.getInitParameter("encoding");
	}

	// doFilter
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	public void destroy() {

	}
}
