<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="left-sidebar">
            <!-- 用户信息 -->
            <div class="tpl-sidebar-user-panel">
                <div class="tpl-user-panel-slide-toggleable">
                    <div class="tpl-user-panel-profile-picture">
                        <img src="${pageContext.request.contextPath }/public/images/headimg/${user.photo }" alt="">
                    </div>
                    <span class="user-panel-logged-in-text">
		              <i class="am-icon-circle-o am-text-success tpl-user-panel-status-icon"></i>
		                ${user.name }
		          	</span>
                </div>
            </div>

            <!-- 菜单 -->
            <ul class="sidebar-nav">
                <li class="sidebar-nav-link">
                    <a href="${pageContext.request.contextPath }/admin/home" ${homeClass }>
                        <i class="am-icon-home sidebar-nav-link-logo"></i> 首页
                    </a>
                </li>
                <li class="sidebar-nav-link">
                    <a href="${pageContext.request.contextPath }/admin/person" ${personClass } >
                        <i class="am-icon-pencil sidebar-nav-link-logo"></i> 个人信息管理
                    </a>
                </li>
                <li class="sidebar-nav-link">
                    <a href="${pageContext.request.contextPath }/admin/type?method=show" ${typeClass } >
                        <i class="am-icon-tag sidebar-nav-link-logo"></i>标签管理
                    </a>
                </li>
                <li class="sidebar-nav-link">
                    <a href="javascript:;" class="sidebar-nav-sub-title ${articleClass }"><!--href="/admin/article/article_list.php"-->
                        <i class="am-icon-file-text sidebar-nav-link-logo"></i> 文章管理
                        <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico"></span>
                    </a>
                    <ul class="sidebar-nav sidebar-nav-sub">
                        <li class="sidebar-nav-link">
                            <a href="${pageContext.request.contextPath }/admin/article?pageIndex=0">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 文章列表
                            </a>
                        </li>

                        <li class="sidebar-nav-link">
                            <a href="${pageContext.request.contextPath }/admin/article/edit">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 增加文章
                            </a>
                        </li>
                    </ul>
                </li>
                 <li class="sidebar-nav-link">
                    <a href="javascript:;" class="sidebar-nav-sub-title ${photoClass }"><!--href="/admin/photo/photo_list.php"-->
                        <i class="am-icon-photo sidebar-nav-link-logo"></i> 相册管理
                        <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico">
                    </a>
                    <ul class="sidebar-nav sidebar-nav-sub">
                        <li class="sidebar-nav-link">
                            <a href="${pageContext.request.contextPath }/admin/photo">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 相册列表
                            </a>
                        </li>

                        <li class="sidebar-nav-link">
                            <a href="${pageContext.request.contextPath }/admin/photo/edit">
                                <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 增加图片
                            </a>
                        </li>
                    </ul>
                </li>
                
               <li class="sidebar-nav-link">
                    <a href="${pageContext.request.contextPath }/admin/message" ${messageClass }>
                        <i class="am-icon-comment sidebar-nav-link-logo"></i> 留言管理
                    </a>
                </li>

                <li class="sidebar-nav-link">
                    <a href="${pageContext.request.contextPath }/admin/user" ${userClass }>
                        <i class="am-icon-user sidebar-nav-link-logo"></i> 用户管理
                    </a>
                </li>
                
                <li class="sidebar-nav-link">
                    <a href="${pageContext.request.contextPath }/blog/home" target="_blank">
                        <i class="am-icon-home sidebar-nav-link-logo"></i>博客首页
                    </a>
                </li>
            </ul>
        </div>