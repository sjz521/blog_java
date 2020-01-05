<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My_Blog</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/public/css/style.css" rel="stylesheet" type="text/css" />
<!-- CuFon: Enables smooth pretty custom font rendering. 100% SEO friendly. To disable, remove this section -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/cufon-yui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/cuf_run.js"></script>
<link href="${pageContext.request.contextPath}/public/css/index.css" rel="stylesheet">
<!-- CuFon ends -->

<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/page.css" />

</head>
<body>
	<div class="main">
		<div class="header">
		    <div class="header_resize">
		      <div class="menu_nav">
		        <ul>
		          <li class="active"><a href="${pageContext.request.contextPath}/blog/home">博客首页</a></li>
		          <li><a href="${pageContext.request.contextPath}/blog/photo">相册</a></li>
		          <li><a href="${pageContext.request.contextPath}/blog/aboutMe">关于我</a></li>
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
	<div class="content">
		<div class="content_resize">
			<div class="mainbar">
				<div class="article">
				
				<!-- 文章循环开始 -->		
					<c:forEach items="${page.list }" var="article">								
					<div class="blogs">
						<h3>
							<a href="${pageContext.request.contextPath}/blog/article?id=${article['id']}" target="_blank">
								${article.title }
							</a>
						</h3>
						<figure> 
							<img src="${pageContext.request.contextPath}/public/images/articleimg/${article['photo'] }" width="100" height="100">
						</figure>
						<!-- 文章简介 -->	
						<a href="${pageContext.request.contextPath}/blog/article?id=${article['id']}" target="_blank"class="readmore">阅读全文&gt;&gt;</a>
						<p class="autor">
							<span>作者：${article.author }</span>
							<span>类型：${article.typeName }</span>
						</p>
						
						<div class="dateview" align="center" style="left:-143px;">
							<!-- 文章创建时间 -->	
							${article.created_at }
						</div>
					</div>
					</c:forEach>
					<!-- 文章循环结束 -->						
				</div>
				<div class="am-u-lg-12 am-cf">
		            <div class="am-fr">
		                <ul class="pagination">
		                	<!-- 分页处理 -->
		                	<c:choose>
		                		<c:when test="${isType==true }">
		                			<li><a href="${pageContext.request.contextPath }/blog/home?pageIndex=0&tId=${tId}">首页</a></li>
		                			<c:if test="${page.start>1 }">
				                		<li><a href="${pageContext.request.contextPath }/blog/home?pageIndex=${page.pageIndex-1}&tId=${tId}">«</a></li>
				                	</c:if>
				                	
				                	<c:forEach begin="${page.start }" end="${page.end }" var="i" step="1">
				                    <li>
				                    	<c:if test="${page.pageIndex==i-1 }">
				                    		<a class="active" href="${pageContext.request.contextPath }/blog/home?pageIndex=${i-1}&tId=${tId}">${i }</a>
				                    	</c:if>
				                    	<c:if test="${page.pageIndex!=i-1 }">
				                    		<a href="${pageContext.request.contextPath }/blog/home?pageIndex=${i-1}&tId=${tId}">${i }</a>
				                    	</c:if>
			                    	</li>
				                    </c:forEach>
				                    <c:if test="${page.end<page.totalPage }">
				                    	<li><a href="${pageContext.request.contextPath }/blog/home?pageIndex=${page.pageIndex+1}&tId=${tId}">»</a></li>
				                    </c:if> 
				                    <li><a href="${pageContext.request.contextPath }/blog/home?pageIndex=${page.getLast() }&tId=${tId}">末页</a></li>
		                		</c:when>
		                		
		                		<c:otherwise>
		                			<li><a href="${pageContext.request.contextPath }/blog/home?pageIndex=0">首页</a></li>
		                			<c:if test="${page.start>1 }">
				                		<li><a href="${pageContext.request.contextPath }/blog/home?pageIndex=${page.pageIndex-1}">«</a></li>
				                	</c:if>
				                	<!-- 页数循环 -->
				                	<c:forEach begin="${page.start }" end="${page.end }" var="i" step="1">
				                    <li>
				                    	<c:if test="${page.pageIndex==i-1 }">
				                    		<a class="active" href="${pageContext.request.contextPath }/blog/home?pageIndex=${i-1}">${i }</a>
				                    	</c:if>
				                    	<c:if test="${page.pageIndex!=i-1 }">
				                    		<a href="${pageContext.request.contextPath }/blog/home?pageIndex=${i-1}">${i }</a>
				                    	</c:if>
			                    	</li>
				                    </c:forEach>
				                    <c:if test="${page.end<page.totalPage}">
				                    	<li><a href="${pageContext.request.contextPath }/blog/home?pageIndex=${page.pageIndex+1}">»</a></li>
				                    </c:if> 
				                    <li><a href="${pageContext.request.contextPath }/blog/home?pageIndex=${page.getLast()-1 }">末页</a></li>
		                		</c:otherwise>
		                	</c:choose>
		                </ul>
		            </div>
		        </div>
			</div>

			<div class="sidebar">
				<div class="searchform">
				<form id="formsearch" name="formsearch" method="post" action="${pageContext.request.contextPath}/blog/home">
					<span>
						<input name="keywords"   maxlength="80" value="" type="text" />
					</span> 
					<button id="search-button"></button>
				</form>
				</div>
				<div class="gadget">
					<h2 class="star"><span>Article</span> Type</h2>
					<div class="clr"></div>
					<ul class="sb_menu">
						<c:forEach items="${types }" var="type">
							<li>
								<a href="${pageContext.request.contextPath }/blog/home?tId=${type.id}&pageIndex=0">${type.name }</a>
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