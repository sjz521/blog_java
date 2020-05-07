<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<div class="main">
		<div class="header">
		    <div class="header_resize">
		      <div class="menu_nav">
		        <ul>
		          <li <c:if test="${isArticle }">class="active"</c:if>><a href="${pageContext.request.contextPath}/blog/home">博客首页</a></li>
		          <li <c:if test="${isPhoto }">class="active"</c:if>><a href="${pageContext.request.contextPath}/blog/photo">相册</a></li>
		          <li <c:if test="${isAbout }">class="active"</c:if>><a href="${pageContext.request.contextPath}/blog/aboutMe">关于我</a></li>
		     	  <li><a href="${pageContext.request.contextPath}/login">${title }</a></li>
		        </ul>
		      </div>
		      <div class="clr"></div>
		      <div class="logo" style="height: 220px;"></div>
		      <div class="clr"></div>
		    </div>
		</div>
	<br>
	<br>
	<br>
	<br>
</head>
<body>

</body>
</html>