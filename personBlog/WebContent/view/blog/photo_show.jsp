<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My_Blog</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/public/css/style.css" rel="stylesheet" type="text/css" />
<!-- CuFon: Enables smooth pretty custom font rendering. 100% SEO friendly. To disable, remove this section -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/cufon-yui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/cuf_run.js"></script>

<!-- CuFon ends -->

<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/page.css" />

</head>
<body>

<div class="main">
	<jsp:include page="layout/top_bar.jsp"></jsp:include>
	<div class="content">
		<div class="content_resize">
			<div class="mainbar">
				<div class="article">
					<h2>
						<span>相册</span> 
					</h2>
					<% int i = 0; %>
						<c:forEach items="${page.list }" var="photo">
							<div style="width: 185px; height: 212px; float:left;">
								<img src="${pageContext.request.contextPath}/public/images/photos/${photo['name'] }" width="180" height="180" alt="pix" />
								<p style="text-align: center; line-height: 10px">
									${photo['realName'] }
								</p>
							</div>
							<%if(i%3==0 && i!=0){ %>
								<br>
							<%i++; } %>
							
						</c:forEach>
					<div class="clr"></div>
					<p></p>										
				</div>

				<div class="am-u-lg-12 am-cf">
		            <div class="am-fr">
		                <ul class="pagination">
		                	<li><a href="${pageContext.request.contextPath }/blog/photo?pageIndex=0">首页</a></li>
		                    <c:if test="${page.start>1}">
		                		<li><a href="${pageContext.request.contextPath }/blog/photo?pageIndex=${page.pageIndex-1}">«</a></li>
		                	</c:if>
		                	
		                    <c:forEach begin="${page.start }" end="${page.end }" var="i" step="1">
		                    	<c:choose>
		                    		<c:when test="${page.pageIndex==i-1 }">
		                    			<li>
				                        	<a class="active"
				                        	href="${pageContext.request.contextPath }/blog/photo?pageIndex=${i-1}">
				                        		${i }
				                        	</a>
				                        </li>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<li>
				                        	<a href="${pageContext.request.contextPath }/blog/photo?pageIndex=${i-1}">
				                        		${i }
				                        	</a>
				                        </li>
		                    		</c:otherwise>
		                    	</c:choose>
		                    </c:forEach>
		                    
		                    <c:if test="${page.end<page.totalPage }">
		                    	<li><a href="${pageContext.request.contextPath }/blog/photo?pageIndex=${page.pageIndex+1}">»</a></li>
		                    </c:if>
		                    <li><a href="${pageContext.request.contextPath }/blog/photo?pageIndex=${page.getLast()-1 }">末页</a></li>
		                </ul>
		            </div>
		        </div>
		    
			</div>
			<jsp:include page="layout/right_bar.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>