<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Login Page</title>
    <meta name="description" content="这是一个 index 页面">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/public/assets/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/public/assets/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/amazeui.datatables.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/css/app.css">
    <script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
    <script src="http://cdn.bootcss.com/amazeui/2.7.2/js/amazeui.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/assets/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/public/assets/js/md5_2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/public/assets/js/jquery.min.js"></script>
    <script type="text/javascript">
    	function _change(){
    		var imgEle = document.getElementById("verifyImg");
    		imgEle.src = "/personBlog/VerifyCodeServlet?a=" + new Date().getTime();
    	}
    	
    	function encrytPassword(){
    		var password = document.getElementById("password").value;
    		//alert(password);
    		var pwd = hex_md5(password);
    		document.getElementById("password").value = pwd;
    	}
    	
    	/*$(document).ready(function(){
    		$("#password").blur(function(){
        		var password = $(this).val();
        		var pwd = $.md5(password);
        		alert(pwd);
        		$(this).val(pwd);
        	});
    	});*/
    	
    </script>
    
</head>

<body data-type="login">
    <script src="${pageContext.request.contextPath}/public/assets/js/theme.js"></script>
    <div class="am-g tpl-g">
        <!-- 风格切换 -->
        <div class="tpl-skiner">
            <div class="tpl-skiner-toggle am-icon-cog">
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
        <div class="tpl-login">
            <div class="tpl-login-content">
                <div class="tpl-login-logo">
                </div>
                <p style="color:red; font-weight:900"> ${msg }</p>
                
                <form class="am-form tpl-form-line-form" action="${pageContext.request.contextPath}/login" method="POST">
                    <div class="am-form-group">
                        <input type="email" class="tpl-form-input" id="user-name" name="email" value="${cookie.email.value }" placeholder="请输入邮箱">
                    </div>

                    <div class="am-form-group">
                        <input type="password" class="tpl-form-input" id="password" name="password" 
                        	value="${cookie.password.value }" placeholder="请输入密码" onblur="encrytPassword();"><!--  onblur="encrytPassword(this);" -->
                    </div>
                    
                    <div class="am-form-group">
                    	<input type="text" placeholder="验证码" name="verifyCode" size="3"  style="display:inline-block;width:50%;" />
                    	<img id="verifyImg" src="/personBlog/VerifyCodeServlet" style="display:inline-block;"/>
                    	<a href="javascript:_change()" style="vertical-align:bottom;">换一张</a>
                    </div>
                    	
                    
                    <div class="am-form-group tpl-login-remember-me">
                    	<input type="checkbox" name="remember" value="true" <c:if test="${cookie.remember.value  == true }">checked</c:if>>
                        <label for="remember-me"> 记住密码</label>

                    </div>
                    <div class="am-form-group">
                        <button type="submit" class="am-btn am-btn-primary  am-btn-block tpl-btn-bg-color-success  tpl-login-btn">登录</button>
                    </div>                    
                </form>
                <form action="${pageContext.request.contextPath}/register" method="GET">
                    <div class="am-form-group">
                        <button type="submit" class="am-btn am-btn-primary  am-btn-block tpl-btn-bg-color-success  tpl-login-btn">注册</button>

                    </div>
                </form>
            </div>
        </div>
    </div>
</body>

</html>