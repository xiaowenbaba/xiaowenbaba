package com.fdw.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdw.dao.FilmDAO;
import com.fdw.dao.UserLogin;
import com.fdw.entity.Customer;
import com.fdw.entity.Film;
import com.fdw.util.PageUtil;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginSystemServlet extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String map  = request.getParameter("action");
		if(map.equals("login")){
			this.Login(request, response);
		}
	}
	protected void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name =request.getParameter("name");
		UserLogin  userLogin  = new UserLogin();
		Customer  customer  = new Customer();
		request.setAttribute("name",name);
		customer.setFirstName(name);
		try {
			List<Customer> list =userLogin.login(customer);
			if(list.size()>0&&!list.isEmpty()){
				request.getRequestDispatcher("/jsp/loginSystem/seccessLogin.jsp").forward(request, response);
			}else{
				 String string   ="登陆失败!请重新输入Name";
				 request.setAttribute("msg", string);
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
