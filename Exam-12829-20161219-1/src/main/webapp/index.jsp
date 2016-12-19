<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01Transitional//EN">

<html>

  <head>

  </head>

  

  <body>
	<a href="<%=request.getContextPath()%>/loginServlet?action=entryLogin">进入登陆界面!</a><br/>
	<a href="<%=request.getContextPath()%>/FilmServlet?action=showAllFilm">显示film所有数据!</a>
	
	
  </body>

</html>
