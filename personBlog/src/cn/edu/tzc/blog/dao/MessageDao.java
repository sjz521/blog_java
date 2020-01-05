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
	public List<MessageInfo> GetAllReply(){
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
	public List<MessageInfo> GetArticleReply(int aId){
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
	public boolean AddReply(Message message) {
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
	public boolean DeleteMessage(int id) {
		boolean result = true;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from message where id=?");
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
	 * 删除全部留言
	 * @return
	 */
	public boolean DeleteAllMessage() {
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
	 * 获得留言数
	 * @return
	 */
	public int GetTotal() {
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
	public int GetTotal(int aId) {
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
	public List<MessageInfo> GetMessagesPage(int pageIndex,int pageSize){
		List<MessageInfo> messages = new ArrayList<MessageInfo>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select m.*,u.name as name,a.title as title from message m left join user u on m.uid=u.id left join article a on m.aid=a.id limit ?,?");
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
	public List<MessageInfo> GetMessagesPage(int pageIndex,int pageSize,int aId){
		List<MessageInfo> messages = new ArrayList<MessageInfo>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select m.*,u.name as name,a.title as title from message m left join user u on m.uid=u.id left join article a on m.aid=a.id where aId=? limit ?,?");
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
