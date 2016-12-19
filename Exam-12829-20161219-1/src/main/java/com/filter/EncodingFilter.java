package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class EncodingFilter implements Filter{

	public void init(FilterConfig filterconfig) throws ServletException {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		String encoding = "utf-8";
		servletRequest.setCharacterEncoding(encoding);
		servletResponse.setContentType("text/html;charset="+encoding);
		filterChain.doFilter(servletRequest, servletResponse);
	}
	public void destroy() {}
	
}
