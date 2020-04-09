<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<div class="sidebar">
			<div class="searchform">
				<form id="formsearch" name="formsearch" method="post" action="${pageContext.request.contextPath}/blog/home">
					<span>
						<input name="keywords" maxlength="80" value="${keyWords }" type="text" />
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
					<jsp:include page="../menu/header2.jsp"></jsp:include>
				</ul>
			</div>
			<div class="gadget">
				<h2 class="star"><span>Introduce</span></h2>
				<div class="clr"></div>
				<ul class="ex_menu">
					<jsp:include page="../menu/professional_menu.jsp"></jsp:include>
				</ul>
			</div>
		</div>
		<div class="clr"></div>