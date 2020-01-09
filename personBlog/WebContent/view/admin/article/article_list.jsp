<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>文章管理</title>
	<meta name="description" content="这是一个 index 页面">
	<meta name="keywords" content="index">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/assets/i/favicon.png">
	<link rel="apple-touch-icon-precomposed"
		href="${pageContext.request.contextPath}/public/assets/i/app-icon72x72@2x.png">
	<meta name="apple-mobile-web-app-title" content="Amaze UI" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/amazeui.min.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/public/assets/css/amazeui.datatables.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/app.css">
	<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
	<style type="text/css">
		.color {
			color: #FFFFFF;
			text-decoration: none;
			font-weight: bold;
		}  /*链接设置*/
		.color:visited {
			color: #FFFFFF;
			text-decoration: none;
			font-weight: bold;
		}  /*访问过的链接设置*/
		.color:hover {
			color: #FFFFFF;
			text-decoration: none;
			font-weight: bold;
		} /*鼠标放上的链接设置*/
	</style>
	
	<script type="text/javascript">
		$(function(){
			
			//全选或全不选
			$("#checkID").click(function(){
				if(this.checked==true){
					#(".articleId").each(function(){
						this.checked=true;
					});
				}else{
					$(".articleId").each(function(){
						this.checked
					});
				}
			});
		});
	</script>
</head>

<body data-type="widgets">
	<script src="${pageContext.request.contextPath}/public/assets/js/theme.js"></script>
	<div class="am-g tpl-g">
		<!-- 头部 --> 
		<jsp:include page="../layout/top_bar.jsp"></jsp:include>
		<!-- 风格切换 -->
		<div class="tpl-skiner">
			<div class="tpl-skiner-toggle am-icon-cog">
			</div>
			<div class="tpl-skiner-content">
				<div class="tpl-skiner-content-title">选择主题
				</div>
				<div class="tpl-skiner-content-bar">
					<span class="skiner-color skiner-white" data-color="theme-white"></span> 
					<span class="skiner-color skiner-black" data-color="theme-black"></span>
				</div>
			</div>
		</div>
		<!-- 侧边导航栏 --> 
		<jsp:include page="../layout/left_bar.jsp"></jsp:include>
		
		<!-- 内容区域 -->
		<div class="tpl-content-wrapper">
			<div class="row-content am-cf">
				<div class="row">
					<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
						<div class="widget am-cf">
							<div class="widget-head am-cf">
								<div class="widget-title  am-cf">文章列表
								</div>
							</div>
							<div class="widget-body  am-fr">
		
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<script type="text/javascript">
													//删除全部文章
													function delAllArticles(){
															var con = confirm("是否确认全部删除！！！");
															if(con==true){
																location.href = "${pageContext.request.contextPath }/admin/article/delete";
															}else{
																return false;
															}
														}
													//删除单篇文章
													function delArticle(a){
					                                    var con = confirm("是否确认删除这条数据???");
					                                    if(con==true){
					                                        location.href = "${pageContext.request.contextPath }/admin/article/delete?id="+a;
					                                    }else{
					                                    	return false;
					                                    }
					                                }
													
													//删除选择的文章
													function delCheck(){
														var flag = confirm("确认要删除全部勾选的文章吗？");
														if(flag){
															var s='';
															$('input[name="articleId"]:checked').each(function(){
																s+=$(this).val()+',';//遍历得到所有的checkbox的value
															});
															if(s.length > 0){
																//删除多出来的，
																s = s.substring(0,s.length-1);
															}
															//生成连接
															location.href = "${pageContext.request.contextPath }/admin/article/delete?id="+s;
														}
													}
													
													//全选/全不选操作
													$(function(){
														$("#checkID").click(function(){
															if(this.checked==true){
																$(".articleId").each(function(){
																	this.checked=true;
																});
															}else{
																$(".articleId").each(function(){
																	this.checked=false;
																});
															}
														});
													});
													
												</script>
												
												<button type="button" class="am-btn am-btn-default am-btn-success"
													onclick="location.href='${pageContext.request.contextPath }/admin/article/edit';">
													<span class="am-icon-plus"></span> 
													<a href="${pageContext.request.contextPath }/admin/article/edit" class="color">新增</a>
												</button>
												
												<button type="button" onclick="delAllArticles();" class="am-btn am-btn-default am-btn-danger">
													<span class="am-icon-trash-o"></span> 删除全部
												</button>
												
												<button type="button" onclick="delCheck();" class="am-btn am-btn-default am-btn-secondary">
													<span class="am-icon-trash-o"></span> 删除勾选
												</button>
											</div>
										</div>
									</div>
								</div>
								<!--搜索按钮-->
								<form action="${pageContext.request.contextPath }/admin/article" method="get">
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
										<div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
											<input type="hidden" name="tId" value="${tId }">
											<input type="text" class="am-form-field" name="keywords"> 
											<span class="am-input-group-btn">
												<button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search"
													type="submit">
												</button>
											</span>
										</div>
									</div>
								</form>
		
								<div class="am-u-sm-12">
									<table width="100%"
										class="am-table am-table-compact am-table-striped tpl-table-black ">
										<thead>
											<tr>
												<th><input type="checkbox" id="checkID"></th>
												<th>文章缩略图</th>
												<th>文章标题</th>
												<th>作者</th>
												<th>分类</th>
												<th>时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<form id="formID" action="${pageContext.request.contextPath }/admin/article" method="post" >
												<input type="hidden" name="method" value="delCheck">
												<c:forEach items="${page.list }" var="article">
													<tr class="gradeX">
															<td><input class="articleId" name="articleId" type="checkbox" value="${article.id }"></td>
															<td><img src="${pageContext.request.contextPath }/public/images/articleimg/${article.photo }" class="tpl-table-line-img"
																alt=""></td>
															<td class="am-text-middle">${article.title }</td>
															<td class="am-text-middle">${article.author }</td>
															<td class="am-text-middle">${article.typeName }</td>
															<td class="am-text-middle">${article.created_at }</td>
															<td class="am-text-middle">
																<div class="tpl-table-black-operation">
																	<a href="${pageContext.request.contextPath }/admin/article/edit?id=${article.id }"> 
																		<i class="am-icon-pencil"></i>编辑  
																	</a> 
																	<a href="javascript:void(0);" onclick="delArticle(${article.id });" class="tpl-table-black-operation-del">
																		<i class="am-icon-trash"></i> 删除
																	</a>
																</div>
															</td>
														</tr>
												</c:forEach> 
											</form>		
										</tbody>
									</table>
								</div>
								<div class="am-u-lg-12 am-cf">
									<div class="am-fr">
										<ul class="am-pagination tpl-pagination">
											<li><a href="${pageContext.request.contextPath }/admin/article?pageIndex=0">首页</a></li>
												<!-- 页数循环 -->
												<c:forEach begin="${page.start }" end="${page.end }" var="i" step="1">
													<c:choose>
		                                    			<c:when test="${page.pageIndex==i-1 }">
		                                    				<li class="am-active"><a href="${pageContext.request.contextPath }/admin/article?pageIndex=${i-1 }">${i }</a></li>
		                                    			</c:when>
		                                    			<c:otherwise>
		                                    				<li><a href="${pageContext.request.contextPath }/admin/article?pageIndex=${i-1 }">${i }</a></li>
		                                    			</c:otherwise>
		                                    		</c:choose>
												</c:forEach>
												
											<!-- 页数循环结束 -->
											<li><a href="${pageContext.request.contextPath }/admin/article?pageIndex=${page.getLast()-1 }">末页</a></li>
										</ul>
									</div>
								</div>
							</div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
	</div>
    <script src="http://cdn.bootcss.com/amazeui/2.7.2/js/amazeui.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/assets/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/public/assets/js/amazeui.datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/assets/js/dataTables.responsive.min.js"></script>

</body>

</html>