package cn.edu.tzc.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.IntPredicate;

import javax.management.Query;
import javax.naming.spi.DirStateFactory.Result;

import cn.edu.tzc.blog.domain.Article;
import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.util.DBUtil;

public class ArticleDao {
	/**
	 * 全部文章信息
	 * @param uid
	 * @return
	 */
	public List<ArticleInfo> getAll(int uid){
		List<ArticleInfo> articles = new ArrayList<>();
		Connection connection =  DBUtil.getConnection();
		try {
			//查询文章视图
			PreparedStatement prep = connection.prepareStatement("select *from articleInfo where uid=? order by created_at desc");
			prep.setInt(1, uid);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				ArticleInfo article = new ArticleInfo();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setIntroduction(rs.getString("introduction"));
				article.setContent(rs.getString("content"));
				article.setCreated_at(rs.getTimestamp("created_at"));
				article.setPhoto(rs.getString("photo"));
				article.setUid(rs.getInt("uid"));
				article.setTid(rs.getInt("tid"));
				article.setAuthor(rs.getString("author"));
				article.setTypeName(rs.getString("typeName"));
				
				articles.add(article);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return articles;
	}
	
	/**
	 * 查找单独的文章
	 * @param id
	 * @return
	 */
	public ArticleInfo findById(int id) {
		Connection connection = DBUtil.getConnection();
		ArticleInfo articleInfo = new ArticleInfo();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from articleInfo where id=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				articleInfo.setId(rs.getInt("id"));
				articleInfo.setTitle(rs.getString("title"));
				articleInfo.setPhoto(rs.getString("photo"));
				articleInfo.setIntroduction(rs.getString("introduction"));
				articleInfo.setContent(rs.getString("content"));
				articleInfo.setCreated_at(rs.getTimestamp("created_at"));
				articleInfo.setUid(rs.getInt("uid"));
				articleInfo.setTid(rs.getInt("tid"));
				articleInfo.setAuthor(rs.getString("author"));
				articleInfo.setTypeName(rs.getString("typeName"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return articleInfo;
	}
	
	/**
	 * 查找分类下的文章
	 * @param tId
	 * @param uId
	 * @return
	 */
	public List<ArticleInfo> getArticlesByTid(int tId,int uId){
		List<ArticleInfo> articles = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select * from article where uid=? and tid=?");
			prep.setInt(1, uId);
			prep.setInt(2, tId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				ArticleInfo article = new ArticleInfo();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setIntroduction(rs.getString("introduction"));
				article.setContent(rs.getString("content"));
				article.setCreated_at(rs.getTimestamp("created_at"));
				article.setPhoto(rs.getString("photo"));
				article.setUid(rs.getInt("uid"));
				article.setTid(rs.getInt("tid"));
				article.setAuthor(rs.getString("author"));
				article.setTypeName(rs.getString("typeName"));
				
				articles.add(article);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return articles;
	}
	
	/**
	 * 根据tid获得该分类下所有文章id
	 * @param tId
	 * @param uId
	 * @return
	 */
	public List<Integer> getArticleIdsByTid(int tId,int uId){
		List<Integer> ids = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select id from article where uid=? and tid=?");
			prep.setInt(1, uId);
			prep.setInt(2, tId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				ids.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return ids;
	}
	
	
	/**
	 * 获得全部文章数
	 * @param uId
	 * @return
	 */
	public int getToal(int uId) {
		int num = 0;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select count(*) from articleInfo where uId=?");
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
	 * 显示部分文章
	 * @param pageIndex
	 * @param pageSize
	 * @param uId
	 * @return
	 */
	public List<ArticleInfo> getArticlePage(int pageIndex,int pageSize,int uId){
		List<ArticleInfo> articles = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from articleInfo where uId=? order by created_at desc limit ?,?");
			prep.setInt(1, uId);
			prep.setInt(2, pageIndex*pageSize);
			prep.setInt(3, pageSize);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				ArticleInfo articleInfo = new ArticleInfo();
				articleInfo.setId(rs.getInt("id"));
				articleInfo.setTitle(rs.getString("title"));
				articleInfo.setPhoto(rs.getString("photo"));
				articleInfo.setIntroduction(rs.getString("introduction"));
				articleInfo.setContent(rs.getString("content"));
				articleInfo.setCreated_at(rs.getTimestamp("created_at"));
				articleInfo.setUid(rs.getInt("uid"));
				articleInfo.setTid(rs.getInt("tid"));
				articleInfo.setAuthor(rs.getString("author"));
				articleInfo.setTypeName(rs.getString("typeName"));
				articles.add(articleInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return articles;
	}
	
	/**
	 * 添加文章
	 * @param article
	 */
	public boolean addArticle(Article article) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into article(title,introduction,content,created_at,photo,uid,tid) values(?,?,?,?,?,?,?)");
			prep.setString(1, article.getTitle());
			prep.setString(2, article.getIntroduction());
			prep.setString(3, article.getContent());
			prep.setTimestamp(4, new Timestamp(new Date().getTime()));
			prep.setString(5, article.getPhoto());
			prep.setInt(6, article.getUid());
			prep.setInt(7, article.getTid());
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
	 * 修改文章
	 * @param article
	 */
	public boolean updateArticle(Article article) {
		boolean result = false;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("update article set title=?,introduction=?,content=?,photo=?,tid=? where id=?");
			prep.setString(1, article.getTitle());
			prep.setString(2, article.getIntroduction());
			prep.setString(3, article.getContent());
			prep.setString(4, article.getPhoto());
			prep.setInt(5, article.getTid());
			prep.setInt(6, article.getId());
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
	 * 删除单篇文章
	 * @param id
	 * @return
	 */
	public boolean deleteArticle(int id) {
		boolean result = true;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from article where id=?");
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
	 * 删除全部文章
	 * @param uId
	 * @return
	 */
	public boolean deleteAll(int uId) {
		Connection connection = DBUtil.getConnection();
		boolean result = true;
		try {
			PreparedStatement prep = connection.prepareStatement("delete from article where uId=?");
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
	 * 删除多篇文章
	 * @param ids
	 * @return
	 */
	public boolean deleteArticles(String[] ids) {
		int[] params = new int[ids.length];
		//根据id数量组成SQL语句
		StringBuilder sb = new StringBuilder();
		sb.append("delete from article where id in (");
		for (int i=0;i<ids.length;i++) {
			params[i] = Integer.parseInt(ids[i]);
			if(i == ids.length-1) {
				sb.append("?");
			}else {
				sb.append("?,");
			}
		}
		sb.append(")");
		
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
	 * 获得分类下的文章个数
	 * @param tid
	 * @return
	 */
	public int getTotalByTid(int uid,int tid) {
		int num = 0;
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select count(*) from article where uid=? and tid=?");
			prep.setInt(1, uid);
			prep.setInt(2, tid);
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
	
	/**
	 * 获得分类下的部门文章
	 * @param pageIndex
	 * @param pageSize
	 * @param uId
	 * @param tId
	 * @return
	 */
	public List<ArticleInfo> getArticleByTid(int pageIndex,int pageSize,int uId,int tId){
		List<ArticleInfo> articles = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from articleInfo where uId=? and tid=? order by created_at desc limit ?,?");
			prep.setInt(1, uId);
			prep.setInt(2, tId);
			prep.setInt(3, pageIndex*pageSize);
			prep.setInt(4, pageSize);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				ArticleInfo articleInfo = new ArticleInfo();
				articleInfo.setId(rs.getInt("id"));
				articleInfo.setTitle(rs.getString("title"));
				articleInfo.setPhoto(rs.getString("photo"));
				articleInfo.setIntroduction(rs.getString("introduction"));
				articleInfo.setContent(rs.getString("content"));
				articleInfo.setCreated_at(rs.getTimestamp("created_at"));
				articleInfo.setUid(rs.getInt("uid"));
				articleInfo.setTid(rs.getInt("tid"));
				articleInfo.setAuthor(rs.getString("author"));
				articleInfo.setTypeName(rs.getString("typeName"));
				articles.add(articleInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return articles;
	}
	
	/**
	 * 获得最新发布的5篇文章
	 * @param uId
	 * @return
	 */
	public List<ArticleInfo> get5Article(int uId){
		List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from articleInfo where uId=? order by created_at desc limit 0,5");
			prep.setInt(1, uId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				ArticleInfo article = new ArticleInfo();
				article.setId(rs.getInt("id"));
				article.setAuthor(rs.getString("author"));
				article.setContent(rs.getString("content"));
				article.setTitle(rs.getString("title"));
				article.setTid(rs.getInt("tId"));
				article.setIntroduction(rs.getString("introduction"));
				article.setUid(rs.getInt("uId"));
				article.setTypeName(rs.getString("typeName"));
				article.setCreated_at(rs.getTimestamp("created_at"));
				article.setPhoto(rs.getString("photo"));
				articles.add(article);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
		}
		return articles;
	}
	
	/**
	 * 查找标题或简介包括关键词的文章
	 * @param key
	 * @param uId
	 * @return
	 */
	public List<ArticleInfo> searchArticleByKey(String key,int uId){
		List<ArticleInfo> list = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from articleInfo where uId=? and locate(?,concat(title,introduction)) order by created_at desc");
			prep.setInt(1, uId);
			prep.setString(2, key);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				ArticleInfo article = new ArticleInfo();
				article.setId(rs.getInt("id"));
				article.setAuthor(rs.getString("author"));
				article.setContent(rs.getString("content"));
				article.setTitle(rs.getString("title"));
				article.setTid(rs.getInt("tId"));
				article.setIntroduction(rs.getString("introduction"));
				article.setUid(rs.getInt("uId"));
				article.setTypeName(rs.getString("typeName"));
				article.setCreated_at(rs.getTimestamp("created_at"));
				article.setPhoto(rs.getString("photo"));
				list.add(article);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBUtil.close(connection);
		}
		return list;
	}
	
	/**
	 * 查找标题或简介包括关键词在某一分类下的文章
	 * @param key
	 * @param uId
	 * @param tId
	 * @return
	 */
	public List<ArticleInfo> searchArticleByKey(String key,int uId,int tId){
		List<ArticleInfo> list = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement prep = connection.prepareStatement("select *from articleInfo where uId=? and tId=? and locate(?,concat(title,introduction)) order by created_at desc");
			prep.setInt(1, uId);
			prep.setInt(2, tId);
			prep.setString(3, key);
			ResultSet rs = prep.executeQuery();
			while(rs.next()) {
				ArticleInfo article = new ArticleInfo();
				article.setId(rs.getInt("id"));
				article.setAuthor(rs.getString("author"));
				article.setContent(rs.getString("content"));
				article.setTitle(rs.getString("title"));
				article.setTid(rs.getInt("tId"));
				article.setIntroduction(rs.getString("introduction"));
				article.setUid(rs.getInt("uId"));
				article.setTypeName(rs.getString("typeName"));
				article.setCreated_at(rs.getTimestamp("created_at"));
				article.setPhoto(rs.getString("photo"));
				list.add(article);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBUtil.close(connection);
		}
		return list;
	}
	
}
