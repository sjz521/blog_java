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
								<div class="widget-title  am-cf">用户列表
								</div>
							</div>
							<div class="widget-body  am-fr">
		
								<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
									<div class="am-form-group">
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<script type="text/javascript">
														function delAllTypes(){
															var con = confirm("是否确认全部删除！！！删除后全部文章也会被删除");
															if(con==true){
																location.href = "${pageContext.request.contextPath}/admin/type?method=deleteAll";
															}else{
																return false;
															}	
														}
														
														function delType(a){
				                                            var con = confirm("是否确认删除这条标签???删除后相应的文章也会删除");
				                                            if(con==true){
				                                                location.href = "${pageContext.request.contextPath}/admin/type?method=delete&id="+a;
				                                            }else{
				                                            	return false;
				                                            }
			                                        	}
														
														//删除勾选
														function delCheck(){
															var flag = confirm("确认要删除全部勾选的文章吗？");
															if(flag){
																var s='';
																$('input[name="all"]:checked').each(function(){
																	s+=$(this).val()+',';//遍历得到所有的checkbox的value
																});
																if(s.length > 0){
																	//删除多出来的，
																	s = s.substring(0,s.length-1);
																}
																//生成连接
																location.href = "${pageContext.request.contextPath }/admin/type?method=delete&id="+s;
															}
															
														}
														
														//全选全不选
														$(function(){
															$("#all").click(function(){
																if(this.checked==true){
																	$(".all").each(function(){
																		this.checked=true;
																	});
																}else{
																	$(".all").each(function(){
																		this.checked=false;
																	});
																}
															});
														});
														
												</script>
												<button type="button" class="am-btn am-btn-default am-btn-success">
		                                            <span class="am-icon-plus"></span> 
		                                            <a href="${pageContext.request.contextPath}/admin/type?method=add" class="color">新增</a>
		                                        </button>
												<button type="button" onclick="delAllTypes();" class="am-btn am-btn-default am-btn-danger">
													<span class="am-icon-trash-o"></span> 删除全部
												</button>
												<button type="button" onclick="delCheck();" class="am-btn am-btn-default am-btn-secondary">
													<span class="am-icon-trash-o"></span> 删除勾选
												</button>
											</div>
										</div>
									</div>
								</div>
		
								<div class="am-u-sm-12">
									<table width="100%"
										class="am-table am-table-compact am-table-striped tpl-table-black ">
										<thead>
											<tr>
												<th><input type="checkbox" id="all"/></th>
												<th>标签名</th>
												<th>创建时间</th>
												<th>文章数目</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${page.list }" var="type">
												<tr>
													<input type="checkbox" class="all" name="all" value="${type.id}">
												</tr>
												<tr class="gradeX">
														<td class="am-text-middle">${type.name }</td>
														<td class="am-text-middle">${type.created_at }</td>
														<td class="am-text-middle">&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/admin/article?tid=${type.id}">${type.articleCount }</a></td>
														<td class="am-text-middle">
															<div class="tpl-table-black-operation"> 
																<a href="javascript:void(0);" onclick="delType(${type.id})" class="tpl-table-black-operation-del">
																	<i class="am-icon-trash"></i>删除 
																</a>
															</div>
														</td>
													</tr>
												
											</c:forEach>
												
										</tbody>
									</table>
								</div>
								<div class="am-u-lg-12 am-cf">
		
									<div class="am-fr">
										<ul class="am-pagination tpl-pagination">
											<li><a href="${pageContext.request.contextPath}/admin/type?method=show&pageIndex=0">首页</a></li>
												<c:forEach begin="${page.start }" end="${page.end }" var="i" step="1">
													<c:choose>
														<c:when test="${page.pageIndex==i-1 }">
															<li class="am-active"><a href="${pageContext.request.contextPath}/admin/type?method=show&pageIndex=${i-1}">${i }</a></li>
														</c:when>
														<c:otherwise>
															<li><a href="${pageContext.request.contextPath}/admin/type?method=show&pageIndex=${i-1}">${i }</a></li>
														</c:otherwise>
													</c:choose>
													
												</c:forEach>	
											<li><a href="${pageContext.request.contextPath}/admin/type?method=show&pageIndex=${page.getLast()-1 }">末页</a></li>
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
    <script src="${pageContext.request.contextPath}/public/assets/js/amazeui.datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/assets/js/dataTables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/assets/js/app.js"></script>

</body>
</html>