<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<header>
         <!-- logo -->
         <div class="am-fl tpl-header-logo">
             <!--<a href="javascript:;"><img src="/assets/img/logo.png" alt=""></a>-->
                 <div class="row" style="margin-top: 10px">
                     <div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
                         <div class="page-header-heading">
                             <span class="am-icon-home page-header-heading-icon"></span> 个人博客
                         </div>
                     </div>
                     <!--<div class="am-u-lg-3 tpl-index-settings-button">
                         <button type="button" class="page-header-button"><span class="am-icon-paint-brush"></span> 设置</button>
                     </div>-->
                 </div>
         </div>
         <!-- 右侧内容 -->
         <div class="tpl-header-fluid">
             <!-- 侧边切换 -->
             <div class="am-fl tpl-header-switch-button am-icon-list" style="padding-top:15px;">
                 <span></span>
             </div>
             <!-- 搜索 -->
             <div class="am-fl tpl-header-search">
                 <form class="tpl-header-search-form" action="javascript:;">
                     <button class="tpl-header-search-btn am-icon-search"></button>
                     <input class="tpl-header-search-box" type="text" placeholder="搜索内容...">
                 </form>
             </div>
             <!-- 其它功能-->
             <div class="am-fr tpl-header-navbar">
                 <ul>
                     <!-- 欢迎语 -->
                     <li class="am-text-sm tpl-header-navbar-welcome">
                         <a href="javascript:;">欢迎你, <span>${user.name }</span> </a>
                     </li>
                     <!-- 最近登录时间 -->
                     <li class="am-text-sm tpl-header-navbar-welcome">
                         <a href="javascript:;">最近登录时间： <span>${user.lasttime }</span> </a>
                     </li>
                     <!-- 退出 -->
                     <li class="am-text-sm">
                         <a href="${pageContext.request.contextPath }/Login">
                             <span class="am-icon-sign-out"></span> 退出
                         </a>
                     </li>
                 </ul>
             </div>
         </div>

     </header>
<body>

</body>
</html>