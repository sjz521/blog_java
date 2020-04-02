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

import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.domain.Photo;
import cn.edu.tzc.blog.domain.PhotoInfo;
import cn.edu.tzc.blog.util.DBUtil;

public class PhotoDao {
	
	/**
	 * 获得全部图片
	 * @param uId
	 * @return
	 */
	public List<Photo> getAll(int uId){
		List<Photo> photos = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		
		try {
			PreparedStatement prep = connection.prepareStatement("select *from photo where uid=?");
			prep.setInt(1, uId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				Photo photo = new Photo();
				photo.setId(rs.getInt("id"));
				photo.setName(rs.getString("name"));
				photo.setCreated_at(rs.getTimestamp("created_at"));
				photo.setuId(uId);
				
				photos.add(photo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		
		return photos;
	}
	
	/**
	 * 获得相册全部信息
	 * @param uId
	 * @return
	 */
	public List<PhotoInfo> getAllInfo(int uId){
		List<PhotoInfo> photos = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select p.*,u.name as author from photo p left join user u on u.id=p.uId where p.uId=?");
			prep.setInt(1, uId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				PhotoInfo photo = new PhotoInfo();
				photo.setId(rs.getInt("id"));
				photo.setName(rs.getString("name"));
				photo.setCreated_at(rs.getTimestamp("created_at"));
				photo.setuId(rs.getInt("uId"));
				photo.setUserName(rs.getString("author"));
				photos.add(photo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		
		return photos;
	}
	
	/**
	 * 根据id找到图片
	 * @param id
	 * @return
	 */
	public Photo getPhotoById(int id) {
		Photo photo = new Photo();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from photo where id=?");
			prep.setInt(1, id);
			ResultSet set = prep.executeQuery();
			if(set.next()) {
				photo.setId(set.getInt("id"));
				photo.setName(set.getString("name"));
				photo.setuId(set.getInt("uId"));
				photo.setCreated_at(set.getTimestamp("created_at"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return photo;
	}
	
	/**
	 * 添加图片
	 * @param photo
	 */
	public boolean addPhoto(Photo photo) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into photo(name,created_at,uid) values(?,?,?)");
			prep.setString(1, photo.getName());
			prep.setTimestamp(2, new Timestamp(new Date().getTime()));
			prep.setInt(3, photo.getuId());
			prep.execute();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBUtil.close(connection);
		}
		return result;
	}
	
	/**
	 * 删除单张图片
	 * @param id
	 */
	public boolean deletePhoto(int id) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from photo where id=?");
			prep.setInt(1, id);
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
	 * 删除全部图片
	 * @param uId
	 */
	public boolean deleteAllPhoto(int uId) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from photo where uid=?");
			prep.setInt(1, uId);
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
	 * 删除多张图片
	 * @param ids
	 * @return
	 */
	public boolean deletePhotos(String[] ids) {
		//1.根据id数量组成SQL语句
		StringBuilder sb = new StringBuilder();
		sb.append("delete from photo where id in (");
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
		//2.运行sql语句
		boolean result = false;
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
	 * 获得相册数量
	 * @param uId
	 * @return
	 */
	public int getToal(int uId) {
		int num = 0;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select count(*) from photo where uId=?");
			prep.setInt(1, uId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
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
	
	/**
	 * 显示部分图片
	 * @param pageIndex
	 * @param pageSize
	 * @param uId
	 * @return
	 */
	public List<PhotoInfo> getPhotoPage(int pageIndex,int pageSize,int uId){
		List<PhotoInfo> photos = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select p.*,u.name as author from photo p left join user u on u.id=p.uId where p.uId=? limit ?,?");
			prep.setInt(1, uId);
			prep.setInt(2, pageIndex*pageSize);
			prep.setInt(3, pageSize);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				PhotoInfo photo = new PhotoInfo();
				photo.setId(rs.getInt("id"));
				photo.setName(rs.getString("name"));
				photo.setCreated_at(rs.getTimestamp("created_at"));
				photo.setuId(rs.getInt("uId"));
				photo.setUserName(rs.getString("author"));
				photos.add(photo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return photos;
	}
	
	/**
	 * 获得最新5张图片
	 * @param uId
	 * @return
	 */
	public List<PhotoInfo> get5Photo(int uId){
		List<PhotoInfo> photos = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select p.*,u.name as author from photo p left join user u on u.id=p.uId where p.uId=? order by created_at desc limit 0,5");
			prep.setInt(1, uId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				PhotoInfo photo = new PhotoInfo();
				photo.setId(rs.getInt("id"));
				photo.setName(rs.getString("name"));
				photo.setuId(rs.getInt("uId"));
				photo.setUserName(rs.getString("author"));
				photo.setCreated_at(rs.getTimestamp("created_at"));
				photos.add(photo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return photos;
	}
}
