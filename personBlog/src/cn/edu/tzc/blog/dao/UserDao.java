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
	public void AddUser(String email,String name,String password) {
		Connection connection = DBUtil.getConnection();
		int result = 0;
		try {
			PreparedStatement prep = connection.prepareStatement("insert into user(email,name,password,role) values(?,?,?,1)");
			prep.setString(1, email);
			prep.setString(2, name);
			prep.setString(3, password);
			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
	}
	
	/**
	 * 修改用户最近登录时间
	 * @param userId
	 */
	public void UpdateUserLoginTime(int userId) {
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("update user set lasttime=? where id=?");
			prep.setTimestamp(1, new Timestamp(new Date().getTime()));
			prep.setInt(2, userId);
			prep.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
	}
	
	/**
	 * 用户更新
	 * @param user
	 */
	public void UpdateUser(User user) {
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("update user set name=?,password=?,photo=? where id=?");
			prep.setString(1, user.getName());
			prep.setString(2, user.getPassword());
			prep.setString(3, user.getPhoto());
			prep.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
	}
	
	/**
	 * 获得除博主之外的其他用户
	 * @return
	 */
	public List<User> GetAllUser(){
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
	public int GetTotal() {
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
	
	public List<User> GetUsersPage(int pageIndex,int pageSize){
		List<User> users = new ArrayList<User>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select * from user where role=1 limit ?,?");
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
