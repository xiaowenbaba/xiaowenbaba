<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<a href="<%=request.getContextPath()%>">返回首页</a>
<form action="<%=path%>/FilmServlet" method="get">
	<input name="action" value="modify" style="display: none;">
	<input name="film_id" value="${film.film_id}" style="display: none;">
	<table>
		<tr>
			<td>title:</td>
			<td><input name="title" type="text" value="${film.title}"></td>
		</tr>
		<tr>
			<td>description:</td>
			<td><input name="description" type="text" value="${film.description}"></td>
		</tr>
		<tr>
			<td>language:</td>
			<td>
				<select name="language">
					<c:forEach items="${list}" var="item">
						<c:choose>
							<c:when test="${film.name == item.language_id}">
								<option value="${item.language_id}" selected="selected">${item.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${item.language_id}">${item.name}</option>
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td><button type="submit">提交修改</button></td>
			<td><button type="reset">重置</button></td>
		</tr>
	</table>
</form>
</body>
</html>