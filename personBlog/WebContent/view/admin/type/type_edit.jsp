<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		                        <div class="widget-title am-fl">发布标签</div>
		                        <div class="widget-function am-fr">
		                            <a href="javascript:;" class="am-icon-cog"></a>
		                        </div>
		                    </div>
		
		                    <div class="widget-body am-fr">
		
		                        <form class="am-form tpl-form-border-form tpl-form-border-br" action="${pageContext.request.contextPath}/admin/type" method="post">
		
		                            <div class="am-form-group">
		                                <label for="user-phone" class="am-u-sm-3 am-form-label">标签名称 <span class="tpl-form-line-small-title"></span></label>
		                                <div class="am-u-sm-9">
		                                     <input type="text" class="tpl-form-input" id="user-name" name="name" placeholder="标签名称">
		                                </div>
		                            </div>
		
		                            <div class="am-form-group">
		                                <div class="am-u-sm-9 am-u-sm-push-3">
		                                    <input type="submit" value="提交" class="am-btn am-btn-primary tpl-btn-bg-color-success ">
		                                </div>
		                            </div>
		                        </form>
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