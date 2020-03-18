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

public class UserService extends FileUtil {
	//把具体的实现类的创建，隐藏到工厂
	//private UserDao userDao = new UserDao();
	private UserDao userDao = new UserDao();
	
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
	public void register(String email,String username,String password) throws UserException {
		//1.使用email邮箱去查询
		User user = userDao.findByEmail(email);
		if(user != null) throw new UserException("邮箱"+email+"已被注册过了!");
		//2.用户注册
		userDao.AddUser(email, username, password);
	}
	
	/**
	 * 用户登录
	 * @param email
	 * @param password
	 * @return
	 * @throws UserException
	 */
	public User login(String email,String password) throws UserException {
		User user = userDao.findByEmail(email);
		if(!user.getPassword().equals(password)) {
			throw new UserException("密码错误！！！");
		}
		userDao.UpdateUserLoginTime(user.getId());
		return user;
	}
	
	/**
	 * 用户更新信息
	 * @param user
	 */
	public String updateUser(User user) {
		//User oldUser =  userDao.findByEmail(user.getEmail());
		boolean result = userDao.UpdateUser(user);
		if(result) {
			return "修改成功";
		}else {
			return "修改失败";
		}
	}
	
	/**
	 * 根据email查找用户
	 * @param email
	 * @return
	 */
	public User GetUserByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	/**
	 * 获得除博主外的所有用户
	 * @return
	 */
	public List<User> GetAllUser(){
		return userDao.GetAllUser();
	}
	
	/**
	 * 获得除博主外的用户个数
	 * @return
	 */
	public int GetTotal() {
		return userDao.GetTotal();
	}
	
	/**
	 * 获得某页的用户
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<User> GetUserPage(int pageIndex,int pageSize){
		return userDao.GetUsersPage(pageIndex, pageSize);
	}
	
	/**
	 * 获得分页下的用户信息
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<User> findUserWithPage(int pageIndex,int pageSize){
		int totalRecord = userDao.GetTotal();
		Page<User> page = new Page<>(pageIndex, pageSize, totalRecord);
		List<User> list = userDao.GetUsersPage(pageIndex, pageSize);
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
