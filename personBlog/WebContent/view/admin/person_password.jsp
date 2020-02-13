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
            
            function validate(){
            	var password = $("#oldPassword").val();
            	if(password == ""){
            		$("#tishi1").html("原始密码不能为空");
            		$("#tishi1").css("color","red");
            		$("#submit").attr("disabled","disabled");
            	}
            	var pwd = $("#newPassword").val();
            	var pwd1 = $("#confirmPassword").val();
            	if(pwd == pwd1){
            		$("#tishi").html("两次密码相同");
            		$("#tishi").css("color","green");
            		$("#submit").removeAttr("disabled");
            	}
            	else{
            		$("#tishi").html("两次密码不相同");
            		$("#tishi").css("color","red");
            		$("#submit").attr("disabled","disabled");
            	}
            }
            
            function checkOldPassword(obj){
            	var password = $("#oldPassword").val();
            	if(password==""){
            		$("#tishi1").html("原始密码不能为空");
            		$("#tishi1").css("color","red");
            		$("#submit").attr("disabled","disabled");
            	}else{
            		if(password != obj){
            			$("#tishi1").html("原始密码错误");
                		$("#tishi1").css("color","red");
                		$("#submit").attr("disabled","disabled");
            		}else{
            			$("#tishi1").html("原始密码正确");
                		$("#tishi1").css("color","green");
                		$("#submit").removeAttr("disabled");
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
			                        action="${pageContext.request.contextPath}/admin/person?method=password" method="post">
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
			                                <label for="user-phone" class="am-u-sm-3 am-form-label">请输入原始密码 <span class="tpl-form-line-small-title"></span></label>
			                                <div class="am-u-sm-9">
			                                     <input type="password" class="tpl-form-input" id="oldPassword" value="" name="oldPassword" placeholder="密码" onkeyup="checkOldPassword(${user.password})">
			                                     <span id="tishi1"></span>
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
			                                     <input type="password" class="tpl-form-input" id="confirmPassword" value="" name="confirmPassword" placeholder="确认密码" onkeyup="validate()">
			                                     <span id="tishi"></span>
			                                </div>
			                            </div>
			
			                            <div class="am-form-group">
			                                <div class="am-u-sm-9 am-u-sm-push-3">
			                                    <input type="submit" value="修改" class="am-btn am-btn-primary tpl-btn-bg-color-success" id="submit">
			                                    <button type="button" class="am-btn am-btn-primary tpl-btn-bg-color-success"
													onclick="location.href='${pageContext.request.contextPath }/admin/person';">
													<span></span> 
													<a href="${pageContext.request.contextPath }/admin/person" class="color"></a>
													取消
												</button>
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