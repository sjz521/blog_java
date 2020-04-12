package cn.edu.tzc.blog.service;

import java.io.DataOutput;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.edu.tzc.blog.dao.UserDao;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.exception.UserException;
import cn.edu.tzc.blog.util.FileUtil;
import cn.edu.tzc.blog.util.MD5Util;

public class UserService extends FileUtil {
	//把具体的实现类的创建，隐藏到工厂
	//private UserDao userDao = new UserDao();
	private UserDao userDao = new UserDao();
	private MD5Util md5Util = new MD5Util();
	
	/**
	 * 按邮箱查找用户
	 * @return
	 */
	public int findAdminId() {
		int id = userDao.findAdminId();
		return id;
	}
	
	public User findUserById(int id) {
		User user = userDao.findById(id);
		return user;
	}
	
	/**
	 * 用户注册
	 * @param email
	 * @param username
	 * @param password
	 * @throws UserException
	 */
	public String register(String email,String username,String password) throws UserException {
		if(email == null ||"".equals(email)) {
			throw new UserException("邮箱不能为空！！！");
		}
		if(password == null ||"".equals(password)) {
			throw new UserException("密码不能为空！！！");
		}
		if(username == null ||"".equals(username)) {
			throw new UserException("用户名不能为空！！！");
		}
		
		//1.使用email邮箱去查询
		User user = userDao.findByEmail(email);
		if(user != null) throw new UserException("邮箱"+email+"已被注册过了!");
		//2.用户注册
		boolean result = userDao.addUser(email, username, password);
		if(result) {
			return "注册成功";
		}
		else {
			return "注册失败";
		}
	}
	
	/**
	 * 用户登录
	 * @param email
	 * @param password
	 * @return
	 * @throws UserException
	 */
	public User login(String email,String password) throws UserException {
		if(email == null ||"".equals(email)) {
			throw new UserException("未输入邮箱！！！");
		}
		if(password == null ||"".equals(password)) {
			throw new UserException("未输入密码！！！");
		}
		User user = userDao.findByEmail(email);
		if(user == null) {
			throw new UserException("该邮箱未注册！！！请确认后输入");
		}
		user.setPassword(md5Util.getMD5String(user.getPassword()));
		if(!user.getPassword().equals(password)) {
			 throw new UserException("密码错误！！！");
		}
		userDao.updateUserLoginTime(user.getId());
		return user;
	}
	
	/**
	 * 用户更新信息
	 * @param user
	 */
	public String updateUser(User user) {
		//User oldUser =  userDao.findByEmail(user.getEmail());
		boolean result = userDao.updateUser(user);
		if(result) {
			return "修改成功";
		}else {
			return "修改失败";
		}
	}
	
	/**
	 * 删除单个用户
	 * @param id
	 * @return
	 */
	public String deleteUser(int id) {
		boolean result = userDao.deleteUser(id);
		if(result) {
			return "用户删除成功";
		}else {
			return "用户删除失败";
		}
	}
	
	/**
	 * 删除多个用户
	 * @param id
	 * @return
	 */
	public String deleteUsers(String id) {
		String[] ids = id.split(",");
		boolean result = userDao.deleteUsers(ids);
		if(result) {
			return "用户删除成功";
		}else {
			return "用户删除失败";
		}
	}
	
	/**
	 * 删除除博主以外的所有用户
	 * @return
	 */
	public String deleteAllUser() {
		boolean result = userDao.deleteAllUser();
		if(result) {
			return "用户删除成功";
		}else {
			return "用户删除失败";
		}
	}
	
	
	/**
	 * 根据email查找用户
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	/**
	 * 获得除博主外的所有用户
	 * @return
	 */
	public List<User> getAllUser(){
		return userDao.getAllUser();
	}
	
	/**
	 * 获得除博主外的用户个数
	 * @return
	 */
	public int getTotal() {
		return userDao.getTotal();
	}
	
	/**
	 * 获得某页的用户
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<User> getUserPage(int pageIndex,int pageSize){
		return userDao.getUsersPage(pageIndex, pageSize);
	}
	
	/**
	 * 获得分页下的用户信息
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<User> findUserWithPage(int pageIndex,int pageSize){
		int totalRecord = userDao.getTotal();
		Page<User> page = new Page<>(pageIndex, pageSize, totalRecord);
		List<User> list = userDao.getUsersPage(pageIndex, pageSize);
		page.setList(list);
		return page;
	}
	
	/**
	 * 对str进行md5加密
	 * @param str
	 * @return
	 */
	public String getMd5String(String str) {
		try {
			//生成MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			//计算md5函数
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
}
