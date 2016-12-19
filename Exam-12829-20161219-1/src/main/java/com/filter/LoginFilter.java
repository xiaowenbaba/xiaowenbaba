package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoginFilter implements Filter{
	public void init(FilterConfig filterconfig) throws ServletException {}
	
	public void destroy() {
		
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("权限验证过滤器");
		HttpServletRequest req = (HttpServletRequest)request;
		Object values = req.getSession().getAttribute("name");
		if(values!=null){
			chain.doFilter(request, response);
		}else{
			request.setAttribute("msg", "请先登录!");
			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		
	}

}
