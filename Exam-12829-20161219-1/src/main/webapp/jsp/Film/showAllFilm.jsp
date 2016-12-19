<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function() {
		$(".deleteId").click(function() {
			var username = $(this).parents("tr").find("td:eq(0)").text();
			var flag = confirm("您真的要删除" + username + "吗?");
			if (flag) {
				return true;
			}
			return false;
		});
		
		$("#new").click(function() {
			window.location.href = "${ctp}" + "/users/toCreate";
			return false;
		});
	});
</script>
</head>
<body>
	<form action="#">
		<div class="page_title">Film表 	 <a class="deleteId"href="<%=request.getContextPath()%>/FilmServlet?action=getLanguage" >增加</a> </div>
	
		<div>
			<table class="query_form_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<td>film_id</td>
					<td>title</td>
					<td>description</td>
					<td>language</td>
					<td>operator</td>
				</tr>
				<c:forEach items="${list}" var="users">
					<tr>
						<td>${users.film_id}</td>
						<td>${users.title}</td>
						<td>${users.description }</td>
						<td>${users.name}</td>
						<th><a class="deleteId"href="<%=request.getContextPath()%>/FilmServlet?action=deleteById&film_id=${users.film_id}">删除</a><br/>
						<a href="<%=request.getContextPath()%>/FilmServlet?action=selectById&film_id=${users.film_id}">修改</a>
						</th>
					</tr>
				</c:forEach>
			</table>
			
		</div>
	</form>
</body>
</html>