<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>My_Blog</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="${pageContext.request.contextPath}/public/css/style.css" rel="stylesheet" type="text/css" />
	<!-- CuFon: Enables smooth pretty custom font rendering. 100% SEO friendly. To disable, remove this section -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/cufon-yui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/cuf_run.js"></script>
	<link href="${pageContext.request.contextPath}/public/css/style2.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/css/media.css" rel="stylesheet">
	<!-- CuFon ends -->
	<!-- <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/assets/i/favicon.png">
	<!-- <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/public/assets/i/app-icon72x72@2x.png">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/amazeui.min.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/public/assets/css/amazeui.datatables.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/app.css"> -->
	<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script> 
	
</head>

<body>
	<jsp:include page="layout/top_bar.jsp"></jsp:include>
		<div class="content">
			<div class="content_resize">
				<div class="mainbar">
					<div class="ibody">
						<article>
							<div class="index_about">
								<h2 class="c_titile">个人简介</h2>
								<ul class="infos">
									${introduction }
								</ul>
							</div>
						</article>
					</div>
					<br>
					<br>
					<br>
					<br>
				</div>
				<div class="sidebar">
			<!-- <div class="searchform">
				<form id="formsearch" name="formsearch" method="post" action="${pageContext.request.contextPath}/blog/home">
					<span>
						<input name="keywords" maxlength="80" value="${keyWords }" type="text" />
					</span> 
					<button id="search-button"></button>
				</form>
			</div> -->
			<div class="gadget">
				<h2 class="star"><span>Article</span> Type</h2>
				<div class="clr"></div>
				<ul class="sb_menu">
					<c:forEach items="${types }" var="type">
						<li>
							<a href="${pageContext.request.contextPath }/blog/home?tId=${type.id}&pageIndex=0" 
								<c:if test="${isType && type.id==tId }">style="color:red;"</c:if> >
								${type.name }
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="gadget">
				<h2 class="star"><span>Blog_</span> Menu</h2>
				<div class="clr"></div>
				<ul class="sb_menu">
					<jsp:include page="menu/header2.jsp"></jsp:include>
				</ul>
			</div>
			<div class="gadget">
				<h2 class="star"><span>Introduce</span></h2>
				<div class="clr"></div>
				<ul class="ex_menu">
					<jsp:include page="menu/professional_menu.jsp"></jsp:include>
				</ul>
			</div>
		</div>
		<div class="clr"></div>
		</div>
	</div>
</body>
</html>