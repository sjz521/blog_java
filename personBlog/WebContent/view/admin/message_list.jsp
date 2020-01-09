<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <title>后台管理界面</title>
	    <meta name="description" content="这是一个 index 页面">
	    <meta name="keywords" content="index">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="renderer" content="webkit">
	    <meta http-equiv="Cache-Control" content="no-siteapp" />
	    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/assets/i/favicon.png">
	    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/public/assets/i/app-icon72x72@2x.png">
	    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
	    <script src="http://cdn.bootcss.com/echarts/3.3.2/echarts.min.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/amazeui.min.css" />
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/amazeui.datatables.min.css" />
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
	
	<body data-type="index">
	    <script src="${pageContext.request.contextPath}/public/assets/js/theme.js"></script>
	    <div class="am-g tpl-g">
	        <!-- 头部 -->
	        <jsp:include page="layout/top_bar.jsp"></jsp:include>
	        <!-- 风格切换 -->
	        <div class="tpl-skiner">
	            <div class="tpl-skiner-toggle am-icon-cog" style="padding-top:10px;">
	            </div>
	            <div class="tpl-skiner-content">
	                <div class="tpl-skiner-content-title">
	                    选择主题
	                </div>
	                <div class="tpl-skiner-content-bar">
	                    <span class="skiner-color skiner-white" data-color="theme-white"></span>
	                    <span class="skiner-color skiner-black" data-color="theme-black"></span>
	                </div>
	            </div>
	        </div>
	        <!-- 侧边导航栏 -->
	        
	        <jsp:include page="layout/left_bar.jsp"></jsp:include>
	        
	        <!-- 内容区域 -->
			<div class="tpl-content-wrapper">
				<div class="row-content am-cf">
					<div class="row">
						<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
							<div class="widget am-cf">
								<div class="widget-head am-cf">
									<div class="widget-title  am-cf">留言列表
									</div>
								</div>
								<div class="widget-body  am-fr">
			
									<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
										<div class="am-form-group">
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<script type="text/javascript">
															function delAllMessage(){
																var con = confirm("是否确认全部删除！！！");
																if(con==true){
																	location.href = "${pageContext.request.contextPath}/admin/message/delete";
																}else{
																	return false;
																}
															}
															
															function delMessage(a){
					                                            var con = confirm("是否确认删除这条数据???");
					                                            if(con==true){
					                                                location.href = "${pageContext.request.contextPath}/admin/message/delete?id="+a;
					                                            }else{
					                                            	return false;
					                                            }
				                                        	}
															
															//删除勾选
															function delChecked(){
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
																	location.href = "${pageContext.request.contextPath }/admin/message/delete?id="+s;
																}
																else{
																	return false;
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
													<button type="button" onclick="delAllMessage();" class="am-btn am-btn-default am-btn-danger">
														<span class="am-icon-trash-o"></span> 删除全部
													</button>
													<button type="button" onclick="delChecked();" class="am-btn am-btn-default am-btn-secondary">
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
													<th>留言人用户名</th>
													<th>留言内容</th>
													<th>留言文章</th>
													<th>留言时间</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<!-- 循环开始 -->
												<c:forEach items="${page.list }" var="message">
													<tr class="gradeX">
														<td><input type="checkbox" class="all" name="all" value="${message.id}"></td>
														<td class="am-text-middle">${message.name }</td>
														<td class="am-text-middle">${message.content }</td>
														<td class="am-text-middle">${message.title }</td>
														<td class="am-text-middle">${message.created_at }</td>
														<td class="am-text-middle">
															<div class="tpl-table-black-operation"> 
																<a href="javascript:void(0);" onclick="delMessage(${message.id})" class="tpl-table-black-operation-del"> 
																	<i class="am-icon-trash"></i>删除 
																</a>
															</div>
														</td>
														</tr>
													
												</c:forEach>
												<!-- 循环结束  -->
											</tbody>
										</table>
									</div>
									<div class="am-u-lg-12 am-cf">
			
										<div class="am-fr">
											<ul class="am-pagination tpl-pagination">
												<li><a href="${pageContext.request.contextPath }/admin/message?pageIndex=0">首页</a></li>
												<c:forEach begin="${page.start }" end="${page.end }" var="i" step="1">
													<c:choose>
														<c:when test="${page.pageIndex==i-1 }">
															<li class="am-active"><a href="${pageContext.request.contextPath }/admin/message?pageIndex=${i-1}">${i }</a></li> 
														</c:when>
														<c:otherwise>
															<li><a href="${pageContext.request.contextPath }/admin/message?pageIndex=${i-1}">${i }</a></li> 
														</c:otherwise>
													</c:choose>
												</c:forEach>
												<li><a href="${pageContext.request.contextPath }/admin/message?pageIndex=${page.getLast() }">末页</a></li>
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