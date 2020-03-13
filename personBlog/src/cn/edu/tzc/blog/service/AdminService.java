package cn.edu.tzc.blog.service;

import cn.edu.tzc.blog.domain.User;

public class AdminService {
	
	/**
	 * 判断用户是否为管理员
	 * @param user
	 * @return
	 */
	public boolean isAdmin(User user) {
		int role = user.getRole();
		if(role==0) {
			return true;
		}else {
			return false;
		}
	}

}
