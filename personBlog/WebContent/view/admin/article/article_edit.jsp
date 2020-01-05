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
    <script type="text/javascript" src="${pageContext.request.contextPath }/ckeditor/ckeditor.js"></script>
    
    <script type="text/javascript">
    	window.onload = function(){
    		CKEDITOR.replace('content');
    	}
    	
    </script>
    
    

</head>

<body data-type="index">
    <script src="${pageContext.request.contextPath}/public/assets/js/theme.js"></script>
    <div class="am-g tpl-g">
        <!-- 头部 -->
        <jsp:include page="../layout/top_bar.jsp"></jsp:include>
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
        
        <jsp:include page="../layout/left_bar.jsp"></jsp:include>
        <!-- 内容区域 -->
		<div class="tpl-content-wrapper">
		    <div class="row-content am-cf">
		        <div class="row">
		            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
		                <div class="widget am-cf">
		                    <div class="widget-head am-cf">
		                        <div class="widget-title am-fl">发布文章</div>
		                        <div class="widget-function am-fr">
		                            <a href="javascript:;" class="am-icon-cog"></a>
		                        </div>
		                    </div>
		
		                    <div class="widget-body am-fr">
		                        <form class="am-form tpl-form-border-form tpl-form-border-br" action="${pageContext.request.contextPath}/admin/article/edit" method="post" enctype="multipart/form-data">
		                        	<input type="hidden" value="${article.id }" name="id">
		                        
		                            <div class="am-form-group">
		                                <label for="user-name" class="am-u-sm-3 am-form-label">标题 
		                                    <span class="tpl-form-line-small-title"></span>
		                                </label>
		                                <div class="am-u-sm-9">
		                                    <input type="text" class="tpl-form-input" id="user-name" value="${article.title }" name="title" placeholder="请输入标题文字">
		                                    <small>请填写标题文字10-20字左右。</small>
		                                </div>
		                            </div>
		
									<div class="am-form-group">
		                                <label for="user-phone" class="am-u-sm-3 am-form-label">作者 <span class="tpl-form-line-small-title"></span></label>
		                                <div class="am-u-sm-9">
		                                	<input type="hidden" name="uId" value="${user.id }" />
		                                     <input type="text" class="tpl-form-input" id="user-name" value="${user.name }" name="author" placeholder="作者" readonly="true" >
		                                    <small>作者不可变</small>
		                                </div>
		                            </div>
		
		                            <div class="am-form-group">
		                                <label for="user-weibo" class="am-u-sm-3 am-form-label">封面图 <span class="tpl-form-line-small-title"></span></label>
		                                <div class="am-u-sm-9">
		                                    <div class="am-form-group am-form-file">
		                                        <div class="tpl-form-file-img">
		                                            <img src="${pageContext.request.contextPath}/public/images/articleimg/${article.photo }" alt="" width="100px" height="100px" id="show">
		                                        </div>
		                                        <button type="button" class="am-btn am-btn-danger am-btn-sm">
		                                            <i class="am-icon-cloud-upload"></i> 添加封面图片
		                                        </button>
		                                        <input id="doc-form-file" type="file" name="photo" onchange="changepic(this)" value="${article.photo }">
		                                    </div>		
		                                </div>
		                            </div>
		                            
									<div class="am-form-group">
		                                <label for="user-phone" class="am-u-sm-3 am-form-label">文章简介 <span class="tpl-form-line-small-title"></span></label>
		                                <div class="am-u-sm-9">
		                                     <input type="text" class="tpl-form-input" id="user-name" value="${article.introduction }" name="introduction" placeholder="文章简介" >
		                                    <small>文章简介为必填</small>
		                                </div>
		                            </div>
		
		                            <div class="am-form-group">
		                                <label for="user-phone" class="am-u-sm-3 am-form-label">标签 <span class="tpl-form-line-small-title"></span></label>
		                                <div class="am-u-sm-9">
		                                        <select id="typeId" onchange="fuzhi()">
		                                        	<c:forEach items="${types }" var="type">
		                                        		<option value="${type.id }" <c:if test="${type.id==article.tid }">selected="selected"</c:if> >${type.name }</option>
		                                        	</c:forEach>
		                                        </select>
		                                    <small>标签为必填</small>
		                                </div>
		                                <input type="hidden" name="select_value" id="select_value" value="${article.tid }"></input>
		                            </div>
		
		                            <div class="am-form-group">
		                                <label for="user-intro" class="am-u-sm-3 am-form-label">文章内容</label>
		                                <div class="am-u-sm-9">
		                                    <textarea class="ckeditor" id="content" placeholder="请输入文章内容" name="content">${article.content }</textarea>                         
		                                </div>
		                            </div>
		
		                            <div class="am-form-group">
		                                <div class="am-u-sm-9 am-u-sm-push-3">
		                                    <input type="submit" value="提交" class="am-btn am-btn-primary tpl-btn-bg-color-success ">
		                                </div>
		                            </div>
		                        </form>
		
						        <script type="text/javascript">
						            function changepic(obj){
						                var newsrc = getObjectURL(obj.files[0]);
						                document.getElementById('show').src = newsrc;
						            }
						
						            function getObjectURL(file){
						                var url = null;
						                if(window.createObjectURL!=undefined){
						                    url = window.createObjectURL(file);
						                }else if(window.URL!=undefined){//mozilla(firefox)
						                    url = window.URL.createObjectURL(file);
						                }else if(window.webkitURL!=undefined){//webkit or chrome
						                    url = window.webkitURL.createObjectURL(file);
						                }
						                return url;
						            }
						
						            function fuzhi(){
						                document.getElementById("select_value").value=document.getElementById("typeId").value;
						            }
						            
						            
						        </script>
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
        