<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>

  <head>

  <title>登陆界面</title>

  </head>
	<form action="<%=request.getContextPath()%>/loginSystemServlet">
	 请输入name:<input type="text" name="name">	</br>
	 <input type="hidden" value="login" name="action">
	 <input type="submit" value="点击登陆">
	</form>
	<%if(request.getAttribute("msg")!=null){out.println(request.getAttribute("msg"));}%>
  <body>

   <form action="">
   
   
   
   </form>

  </body>

</html>
