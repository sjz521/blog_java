package cn.edu.tzc.blog.dao;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.FastHashMap;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;

import com.mysql.jdbc.interceptors.ResultSetScannerInterceptor;

import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.util.DBUtil;

public class UserDao {
	
	/**
	 * 根据身份查找博主id
	 * @return
	 */
	public int findAdminId() {
		int id = 0;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select id from user where role=0");
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				id = rs.getInt("id");
			}else {
				id = 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return id;
	}
	
	/**
	 * 按邮箱查找用户
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		Connection connection = DBUtil.getConnection();
		User user = null;
		try {
			PreparedStatement prep = connection.prepareStatement("select *from user where email=?");
			prep.setString(1, email);
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getInt("role"));
				user.setPhoto(rs.getString("photo"));
				user.setIntroduction(rs.getString("introduction"));
				user.setLasttime(rs.getTimestamp("lasttime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBUtil.close(connection);
		}
		return user;
		
	}
	
	/**
	 * 按照id查找用户
	 * @param id
	 * @return
	 */
	public User findById(int id) {
		User user = null;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from user where id=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setIntroduction(rs.getString("introduction"));
				user.setCreated_at(rs.getTimestamp("created_at"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		
		return user;
	}
	
	
	/**
	 * 添加用户
	 * @param email
	 * @param name
	 * @param password
	 */
	public boolean addUser(String email,String name,String password) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into user(email,name,password,role) values(?,?,?,1)");
			prep.setString(1, email);
			prep.setString(2, name);
			prep.setString(3, password);
			prep.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return result;
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public boolean deleteUser(int id) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from user where id=?");
			prep.setInt(1, id);
			prep.execute();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除多个用户
	 * @param ids
	 * @return
	 */
	public boolean deleteUsers(String[] ids) {
		boolean result = false;
		
		//1.处理sql语句
		StringBuilder sb = new StringBuilder();
		sb.append("delete from user where id in (");
		int[] params = new int[ids.length];
		for (int i=0;i<ids.length;i++) {
			params[i] = Integer.parseInt(ids[i]);
			if(i == ids.length-1) {
				sb.append("?");
			}else {
				sb.append("?,");
			}
		}
		sb.append(")");
		//2.运行语句
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement(sb.toString());
			for(int i=0;i<params.length;i++) {
				prep.setInt(i+1, params[i]);
			}
			int n = prep.executeUpdate();
			if(n>0) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		
		return result;
	}
	
	/**
	 * 删除除博主以外的所有用户
	 * @return
	 */
	public boolean deleteAllUser() {
		boolean result = true;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from user where role=1");
			result = prep.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return result;	
	}
	
	
	/**
	 * 修改用户最近登录时间
	 * @param userId
	 */
	public boolean updateUserLoginTime(int userId) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("update user set lasttime=? where id=?");
			prep.setTimestamp(1, new Timestamp(new Date().getTime()));
			prep.setInt(2, userId);
			prep.execute();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return result;
	}
	
	/**
	 * 用户更新
	 * @param user
	 */
	public boolean updateUser(User user) {
		Connection connection = DBUtil.getConnection();
		boolean result = true;
		try {
			PreparedStatement prep = connection.prepareStatement("update user set name=?,password=?,photo=?,introduction=? where id=?");
			prep.setString(1, user.getName());
			prep.setString(2, user.getPassword());
			prep.setString(3, user.getPhoto());
			prep.setString(4, user.getIntroduction());
			prep.setInt(5, user.getId());
			prep.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);	
		}
		return result;
	}
	
	/**
	 * 获得除博主之外的其他用户
	 * @return
	 */
	public List<User> getAllUser(){
		List<User> users = new ArrayList<User>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from user where role=1");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("emaile"));
				user.setName(rs.getString("name"));
				user.setPhoto(rs.getString("photo"));
				user.setIntroduction(rs.getString("introduction"));
				user.setCreated_at(rs.getTimestamp("created_at"));
				user.setLasttime(rs.getTimestamp("lasttime"));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return users;	
	}
	
	/**
	 * 获得留言个数
	 * @return
	 */
	public int getTotal() {
		int num = 0;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select count(*) from user where role=1");
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return num;	
	}
	
	public List<User> getUsersPage(int pageIndex,int pageSize){
		List<User> users = new ArrayList<User>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select * from user where role=1 order by created_at desc limit ?,?");
			prep.setInt(1, pageIndex*pageSize);
			prep.setInt(2, pageSize);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPhoto(rs.getString("photo"));
				user.setIntroduction(rs.getString("introduction"));
				user.setCreated_at(rs.getTimestamp("created_at"));
				user.setLasttime(rs.getTimestamp("lasttime"));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		
		return users;
	}
}
