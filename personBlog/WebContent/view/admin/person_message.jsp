<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            
            function check(){
            	var password1 = document.getElemontById("newPassword").value;
            	var password2 = document.getElemontById("newPassword2").value;
            	var oldPassword = document.form1.oldPassword.value;
            	if(oldPassword!=""){
            		if(password1!=password2){
                		alert("两次密码输入不一致，请确认后重新输入");
                		document.getElementById("submit").disabled=true;
                		return false;
                	}else{
                		document.getElementById("submit").disabled=false;
                		return true;
                	}
            	}
            }
            
            function checkOldPassword(obj){
            	var password = document.getElemontById("oldPassword").value;
            	if(password!=""){
            		if(password != obj){
            			alert("输入的原始密码不对，请确认后重新输入");
            		}
            	}
            }
        </script>
	
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
			                        <div class="widget-title am-fl">个人信息管理</div>
			                        <div class="widget-function am-fr">
			                            <a href="javascript:;" class="am-icon-cog"></a>
			                        </div>
			                    </div>
			
			                    <div class="widget-body am-fr">
									<p style="color:red; font-weight:900"> ${message }</p>
			                        <form class="am-form tpl-form-border-form tpl-form-border-br" name="from1"
			                        action="${pageContext.request.contextPath}/admin/person" method="post" enctype="multipart/form-data">
			                        <input type="hidden" value="${user.id }" name="id">
			
			                            <div class="am-form-group">
			                                <label for="user-name" class="am-u-sm-3 am-form-label">邮箱 
			                                    <span class="tpl-form-line-small-title"></span>
			                                </label>
			                                <div class="am-u-sm-9">
			                                    <input type="email" class="tpl-form-input" id="user-name" value="${user.email }" name="email" placeholder="" readonly="true">
			                                </div>
			                            </div>
			                        
			                            <div class="am-form-group">
			                                <label for="user-name" class="am-u-sm-3 am-form-label">用户名 
			                                    <span class="tpl-form-line-small-title"></span>
			                                </label>
			                                <div class="am-u-sm-9">
			                                    <input type="text" class="tpl-form-input" id="user-name" value="${user.name }" name="name" placeholder="请输入用户名">
			                                </div>
			                            </div>
			                            
			                            <div class="am-form-group">
			                                <label for="user-phone" class="am-u-sm-3 am-form-label">请输入原始密码 <span class="tpl-form-line-small-title"></span></label>
			                                <div class="am-u-sm-9">
			                                     <input type="password" class="tpl-form-input" id="oldPassword" value="" name="oldPassword" placeholder="密码" onblur="checkOldPassword(${user.password})">
			                                </div>
			                            </div>
			                            
										<div class="am-form-group">
			                                <label for="user-phone" class="am-u-sm-3 am-form-label">请输入新的密码 <span class="tpl-form-line-small-title"></span></label>
			                                <div class="am-u-sm-9">
			                                     <input type="password" class="tpl-form-input" id="newPassword" value="" name="newPassword" placeholder="密码">
			                                </div>
			                            </div>
			
			                            <div class="am-form-group">
			                                <label for="user-phone" class="am-u-sm-3 am-form-label">确认密码 <span class="tpl-form-line-small-title"></span></label>
			                                <div class="am-u-sm-9">
			                                     <input type="password" class="tpl-form-input" id="newPassword2" value="" name="newPassword2" placeholder="确认密码" onblur=" return check()" >
			                                </div>
			                            </div>
			
			
			                            <div class="am-form-group">
			                                <label for="user-weibo" class="am-u-sm-3 am-form-label">头像 <span class="tpl-form-line-small-title"></span></label>
			                                <div class="am-u-sm-9">
			                                    <div class="am-form-group am-form-file">
			                                        <div class="tpl-form-file-img">
			                                            <img src="../public/images/headimg/${user.photo }" alt="" width="100px" height="100px" id="show">
			                                        </div>
			                                        <button type="button" class="am-btn am-btn-danger am-btn-sm">
			                                            <i class="am-icon-cloud-upload"></i> 选择图片
			                                        </button>
			                                        <input id="doc-form-file" type="file" name="photo" onchange="changepic(this)">
			                                    </div>
			
			                                </div>
			                            </div>
			                            
			                            
										<div class="am-form-group">
			                                <label for="user-phone" class="am-u-sm-3 am-form-label">个人简介 <span class="tpl-form-line-small-title"></span></label>
			                                <div class="am-u-sm-9">
			                                     <textarea class="" rows="10" id="user-intro"placeholder="请输入文章内容"  name="introduce">${user.introduction }</textarea>
			                                </div>
			                            </div>
			
			                            <div class="am-form-group">
			                                <div class="am-u-sm-9 am-u-sm-push-3">
			                                    <input type="submit" value="修改" class="am-btn am-btn-primary tpl-btn-bg-color-success" id="submit">
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