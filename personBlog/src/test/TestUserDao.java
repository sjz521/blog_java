package test;

import cn.edu.tzc.blog.dao.UserDao;

public class TestUserDao {
	public static void main(String[] args) {
		UserDao userDao = new UserDao();
		int id = userDao.findAdminId();
		System.out.println(id);
	}
}
