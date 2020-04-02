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
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/assets/i/favicon.png">
	<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/public/assets/i/app-icon72x72@2x.png">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/amazeui.min.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/public/assets/css/amazeui.datatables.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/app.css">
	<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/ckeditor/ckeditor.js"></script>
	
	<style type="text/css">
		.table-a table{
			border:1px solid #000000
		}
		.table-a table td,th{
			border:1px solid #000000
		}
		
		.editormd-text{
			width : 90%
		}
	</style>
	
	<!-- Editor css... -->
	<link rel="stylesheet" href="${pageContext.request.contextPath }/editor/css/editormd.preview.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/editor/css/editormd.css">
    
    <!-- Editor js... -->
    <script src="${pageContext.request.contextPath }/editor/js/editormd.min.js"></script>
    <script src="${pageContext.request.contextPath }/editor/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/editor/lib/marked.min.js"></script>
    <script src="${pageContext.request.contextPath }/editor/lib/prettify.min.js"></script>
    
   	<script type="text/javascript">
		function validate(){
			var name = document.getElementById("m_name");
			var text = document.getElementById("m_text");
			var verification = document.getElementById("verification");
			if(username.value==""){
		//		document.getElementById("UN_status").innerHTML = "账号未填写！";
				alert("ID未填写！");
				m_name.focus();
				return false;
			}
			if(password.value==""){
		//		document.getElementById("PW_status").innerHTML = "密码未填写！";
				alert("内容未填写！");
				m_text.focus();
				return false;
			}
			return true;
		}
		
		$.get('${pageContext.request.contextPath }/md/${articleInfo.content }').success(function(elem){
			$("#mdText").text(elem);
			editormd.markdownToHTML("content",{
				htmlDecode		: "style,script,iframe",
				emoji			: true,
				taskList		: true,
				tex				: true,
				flowChart		: true,
				sequenceDiagram : true
			});
		});
		
	</script>
	
	<!-- Editor markdown转html -->
	<!-- <script>
		editormd.markdownToHTML("content",{emoji:true})
	</script> -->
	
</head>

<body>
	<jsp:include page="layout/top_bar.jsp"></jsp:include>
		<div class="content">
			<div class="content_resize">
				<div class="mainbar">
					<p style="color:red; font-weight:900">${Articleerror }</p>
					<p style="color:red; font-weight:900">${Typeerror2}</p>
					<div class="ibody">
						<article>
							<div class="index_about">
								<h2 class="c_titile">${articleInfo.title }</h2>
								<p class="box_c">
									<span class="d_time">发布时间：${articleInfo.created_at }</span>
									<span>编辑：${articleInfo.author }<br></span>
									<span>类型：${articleInfo.typeName }</span>
								</p>
								<ul class="infos table-a">
									<div class="editormd-preview-theme" id="content">
										<!-- ${articleInfo.content}  -->
										<textarea style="display:none;" id="mdText"></textarea>
									</div> 
								</ul>
							</div>
						</article>
					</div>
					<br>
					<br>
					<br>
					<br>
					<hr>
					<!-- 留言板 -->
					<div class="article">
						<div class="article">
							<div class="clr" align="center">
								<h2>
									留言板
								</h2>
							</div>
							<form action="${pageContext.request.contextPath}/blog/message" method="post" id="sendemail" onsubmit="return validate();">
								<ol>
									<!-- <li><label for="name"><span style="color: red">*</span>你的用户名</label>
										<input id="m_name" name="m_name" class="text" rows="3"/></li> -->
										<li><input type="hidden" name="aId" value="${articleInfo.id }"/></li>
									<li><label for="message"><span style="color: red">*</span>我要留言</label>
										<textarea id="m_text" name="content" rows="7" cols="30"></textarea>
									</li>
									<li><!-- <input type="image" name="imageField" id="imageField"
										src="images/submit.gif" class="send" /> -->
										<button type="submit" class="btn btn-primary mb-2" style="margin-top:10px; ">提交</button>
										<div class="clr"></div></li>
								</ol>
							</form>
						</div>

						<br>
						<h2>
							<span>最近的留言</span> 
						</h2>
						
							<c:forEach items="${page.list }" var="message">
								<div class="comment">
									<p style="font-size: 13px; margin-bottom: 0px;">
										<a href="#">${message.name }</a> &nbsp;留言时间:<br />${message.created_at }
									</p>
									<p style="font-size: 16px;">${message.content }</p>
								</div>
							</c:forEach>
					</div>
					<div class="am-u-lg-12 am-cf">
			            <div class="am-fr">
			                <ul class="am-pagination tpl-pagination">
			                	
			                	<c:if test="${page.start>1 }">
			                		<li><a href="${pageContext.request.contextPath }/blog/article?pageIndex=${page.pageIndex-1}">«</a></li>
			                	</c:if>
			                    
			                    	<c:forEach begin="${page.start }" end="${page.end }" var="i" step="1">
			                    		<c:choose>
			                    			<c:when test="${page.pageIndex==i-1 }">
			                    				<li><a class="active" href="${pageContext.request.contextPath }/blog/article?pageIndex=${i-1}">${i }</a></li>
			                    			</c:when>
			                    			<c:otherwise>
			                    				<li><a class="active" href="${pageContext.request.contextPath }/blog/article?pageIndex=${i-1}">${i }</a></li>
			                    			</c:otherwise>
			                    		</c:choose>
			                    	</c:forEach>
		                        <c:if test="${page.end<page.totalPage }">
		                        	<li><a href="${pageContext.request.contextPath }/blog/article?pageIndex=${page.pageIndex+1}">»</a></li>
		                        </c:if>
			                    
			                </ul>
			            </div>
			        </div>
					
				</div>
				<jsp:include page="layout/right_bar.jsp"></jsp:include>
			</div>
		</div>
	</div>
</body>
</html>