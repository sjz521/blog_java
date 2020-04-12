package cn.edu.tzc.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.InflaterOutputStream;

import cn.edu.tzc.blog.domain.Message;
import cn.edu.tzc.blog.domain.MessageInfo;
import cn.edu.tzc.blog.util.DBUtil;

public class MessageDao {
	
	/**
	 * 获得所有留言
	 * @return
	 */
	public List<MessageInfo> getAllMessage(){
		List<MessageInfo> messages = new ArrayList<MessageInfo>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select m.*,u.name as name,a.title as title from message m left join user u on m.uid=u.id left join article a on m.aid=a.id");
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				MessageInfo message = new MessageInfo();
				message.setId(rs.getInt("id"));
				message.setuId(rs.getInt("uId"));
				message.setContent(rs.getString("content"));
				message.setaId(rs.getInt("aId"));
				message.setCreated_at(rs.getTimestamp("created_at"));
				message.setName(rs.getString("name"));
				message.setTitle(rs.getString("title"));
				messages.add(message);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return messages;
	}
	
	/**
	 * 查询文章下的留言
	 * @param aId
	 * @return
	 */
	public List<MessageInfo> getMessageByAId(int aId){
		List<MessageInfo> messages = new ArrayList<MessageInfo>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select m.*,u.name as name,a.title as title from message m left join user u on m.uid=u.id left join article a on m.aid=a.id where m.aId=?");
			prep.setInt(1, aId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				MessageInfo message = new MessageInfo();
				message.setId(rs.getInt("id"));
				message.setuId(rs.getInt("uId"));
				message.setContent(rs.getString("content"));
				message.setaId(rs.getInt("aId"));
				message.setCreated_at(rs.getTimestamp("created_at"));
				message.setName(rs.getString("name"));
				message.setTitle(rs.getString("title"));
				messages.add(message);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return messages;
	}
	
	/**
	 * 添加留言
	 * @param message
	 */
	public boolean addMessage(Message message) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into message(uId,content,aId,created_at) values(?,?,?,?)");
			prep.setInt(1, message.getuId());
			prep.setString(2, message.getContent());
			prep.setInt(3, message.getaId());
			prep.setTimestamp(4, new Timestamp(new Date().getTime()));
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
	 * 删除单条留言
	 * @param id
	 * @return
	 */
	public boolean deleteMessage(int id) {
		boolean result = true;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from message where id=?");
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
	 * 删除全部留言
	 * @return
	 */
	public boolean deleteAllMessage() {
		boolean result = true;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from message");
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
	 * 删除部分留言
	 * @param ids
	 * @return
	 */
	public boolean deleteMessages(String[] ids) {
		boolean result = false;
		
		//1.处理sql语句
		StringBuilder sb = new StringBuilder();
		sb.append("delete from message where id in (");
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
	 * 获得留言数
	 * @return
	 */
	public int getTotal() {
		Connection connection = DBUtil.getConnection();
		int num = 0;
		try {
			PreparedStatement prep = connection.prepareStatement("select count(*) from message");
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 获得文章下的留言总数
	 * @param aId
	 * @return
	 */
	public int getTotal(int aId) {
		Connection connection = DBUtil.getConnection();
		int num = 0;
		try {
			PreparedStatement prep = connection.prepareStatement("select count(*) from message where aId=?");
			prep.setInt(1, aId);
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 获得某页的留言内容
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<MessageInfo> getMessagesPage(int pageIndex,int pageSize){
		List<MessageInfo> messages = new ArrayList<MessageInfo>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select m.*,u.name as name,a.title as title from message m left join user u on m.uid=u.id left join article a on m.aid=a.id order by created_at desc limit ?,?");
			prep.setInt(1, pageIndex*pageSize);
			prep.setInt(2, pageSize);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				MessageInfo info = new MessageInfo();
				info.setId(rs.getInt("id"));
				info.setuId(rs.getInt("uId"));
				info.setContent(rs.getString("content"));
				info.setaId(rs.getInt("aId"));
				info.setName(rs.getString("name"));
				info.setTitle(rs.getString("title"));
				info.setCreated_at(rs.getTimestamp("created_at"));
				messages.add(info);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		
		return messages;
	}
	
	/**
	 * 获得文章下的留言分页内容
	 * @param pageIndex
	 * @param pageSize
	 * @param aId
	 * @return
	 */
	public List<MessageInfo> getMessagesPage(int pageIndex,int pageSize,int aId){
		List<MessageInfo> messages = new ArrayList<MessageInfo>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select m.*,u.name as name,a.title as title from message m left join user u on m.uid=u.id left join article a on m.aid=a.id where aId=? order by created_at desc limit ?,?");
			prep.setInt(1, aId);
			prep.setInt(2, pageIndex*pageSize);
			prep.setInt(3, pageSize);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				MessageInfo info = new MessageInfo();
				info.setId(rs.getInt("id"));
				info.setuId(rs.getInt("uId"));
				info.setContent(rs.getString("content"));
				info.setaId(rs.getInt("aId"));
				info.setName(rs.getString("name"));
				info.setTitle(rs.getString("title"));
				info.setCreated_at(rs.getTimestamp("created_at"));
				messages.add(info);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		
		return messages;
	}
}
