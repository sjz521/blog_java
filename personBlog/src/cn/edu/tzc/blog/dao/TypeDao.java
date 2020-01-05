package cn.edu.tzc.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.taglibs.standard.lang.jstl.Coercions;

import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.TypeInfo;
import cn.edu.tzc.blog.util.DBUtil;

public class TypeDao {
	/**
	 * 返回所有分类
	 * @return
	 */
	public List<Type> showAllTypes(){
		List<Type> types = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from type");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				Type type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
				type.setCreated_at(rs.getTimestamp("created_at"));
				
				types.add(type);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return types;
	}
	
	/**
	 * 获得分类总数
	 * @return
	 */
	public int getCount() {
		int count = 0;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select count(*) from type");
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return count;
	}
	
	
	/**
	 * 得到部分分类
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public List<TypeInfo> getTypeInfos(int pageSize,int pageIndex){
		List<TypeInfo> types = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select t.*,ifnull(a.num,0) as num from type t left join (select count(*) as num,tid from article group by tid order by num) a on a.tid = t.id limit ?,?");
			prep.setInt(1, pageIndex*pageSize);
			prep.setInt(2, pageSize);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				TypeInfo info = new TypeInfo();
				info.setId(rs.getInt("id"));
				info.setName(rs.getString("name"));
				info.setArticleCount(rs.getInt("num"));
				info.setCreated_at(rs.getTimestamp("created_at"));
				types.add(info);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return types;
	}
	
	/**
	 * 添加分类
	 * @param type
	 * @return
	 */
	public boolean addType(Type type) {
		boolean result = false;
		
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into type(name,created_at) values(?,?)");
			prep.setString(1, type.getName());
			prep.setTimestamp(2, new Timestamp(new Date().getTime()));
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
	 * 删除单个分类
	 * @param id
	 * @return
	 */
	public boolean deleteType(int id) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from type where id=?");
			prep.setInt(1, id);
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
	 * 删除所有分类
	 * @return
	 */
	public boolean deleteAll() {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from type");
			result = prep.execute();
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
	 * 根据id查找分类
	 * @param id
	 * @return
	 */
	public Type getTypeById(int id) {
		Type type = null;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from type where id=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
				type.setCreated_at(rs.getTimestamp("created_at"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBUtil.close(connection);
		}
		
		return type;
	}
}
