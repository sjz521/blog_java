package cn.edu.tzc.blog.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaSize.ISO;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

import cn.edu.tzc.blog.dao.ArticleDao;
import cn.edu.tzc.blog.dao.UserDao;
import cn.edu.tzc.blog.domain.Article;
import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.domain.FileHelper;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.service.exception.ArticleException;

public class ArticleService extends FileHelper {
	private ArticleDao articleDao = new ArticleDao();
	
	/**
	 * 获得全部文章
	 * @param uid
	 * @return
	 */
	public List<ArticleInfo> showAllArticles(int uid){
		return articleDao.GetAll(uid);
	}
	
	/**
	 * 查找文章
	 * @param id
	 * @return
	 * @throws ArticleException
	 */
	public ArticleInfo findById(int id) throws ArticleException {
		ArticleInfo articleInfo =  articleDao.FindById(id);
		if(articleInfo == null) {
			throw new ArticleException("找不到该文章！！");
		}
		return articleInfo;
	}
	
	/**
	 * 获得分页下的文章数
	 * @param uId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws ArticleException
	 */
	public List<ArticleInfo> getArticlesPage(int uId,int pageIndex,int pageSize){
		List<ArticleInfo> articles = articleDao.GetArticlePage(pageIndex, pageSize, uId);
		return articles;
	}
	
	public List<ArticleInfo> getArticlePage(int uId,int pageIndex,int pageSize,int tId){
		List<ArticleInfo> articles = articleDao.GetArticleByTid(pageIndex, pageSize, uId, tId);
		return articles;
	}
	
	/**
	 * 获得文章数
	 * @param uId
	 * @return
	 */
	public int getArticleCount(int uId) {
		return articleDao.GetToal(uId);
	}
	
	/**
	 * 获得分类下的文章数
	 * @param uId
	 * @param tId
	 * @return
	 */
	public int getArticleCount(int uId,int tId) {
		return articleDao.getTotalByTid(uId, tId);
	}
	
	/**
	 * 添加文章
	 * @param article
	 */
	public void addArticle(Article article) {
		articleDao.AddArticle(article);
	}
	
	/**
	 * 更新文章
	 * @param article
	 */
	public void updateArticle(Article article,String path,String publicPath) {
		Article oldInfo = articleDao.FindById(article.getId());
		if(article.getPhoto() != oldInfo.getPhoto()) {
			delFile(path, oldInfo.getPhoto());
			delFile(publicPath, oldInfo.getPhoto());
		}
		articleDao.UpdateArticle(article);
	}
	
	/**
	 * 删除文章
	 * @param id
	 * @return
	 * @throws ArticleException
	 */
	public String DeleteArticle(int id,String path) {
		Article article = articleDao.FindById(id);
		boolean fileResult = delFile(path, article.getPhoto());
		if(!fileResult) {
			return "文章相关图片删除失败";
		}
		boolean result = articleDao.Delete(id);
		if(result) {
			return "删除成功";
		}else {
			return "删除失败";
		}
	}
	
	/**
	 * 删除全部文章
	 * @param uId
	 * @return
	 * @throws ArticleException
	 */
	public String DeleteAllArticle(int uId,String path) throws ArticleException {
		List<ArticleInfo> articles = articleDao.GetAll(uId);
		for (ArticleInfo articleInfo : articles) {
			delFile(path, articleInfo.getPhoto());
		}
		boolean result = articleDao.DeleteAll(uId);
		if(result) {
			return "删除成功";
		}else {
			return "删除失败";
		}
	}
	
	/**
	 * 获得文章的分页
	 * @param pageIndex
	 * @param pageSize
	 * @param uId
	 * @return
	 */
	public Page<ArticleInfo> findArticlesWithPage(int pageIndex,int pageSize,int uId){
		int totalRecord = articleDao.GetToal(uId);
		Page<ArticleInfo> page = new Page<>(pageIndex, pageSize,totalRecord);
		List<ArticleInfo> articleInfos = articleDao.GetArticlePage(pageIndex, pageSize, uId);
		page.setList(articleInfos);
		
		return page;
	}
	
	/**
	 * 获得分类下文章的分页
	 * @param pageIndex
	 * @param pageSize
	 * @param uId
	 * @param tId
	 * @return
	 */
	public Page<ArticleInfo> findArticleWithPageByTid(int pageIndex,int pageSize,int uId,int tId){
		int totalRecord = articleDao.getTotalByTid(uId, tId);
		Page<ArticleInfo> page = new Page<>(pageIndex, pageSize,totalRecord);
		List<ArticleInfo> articleInfos = articleDao.GetArticleByTid(pageIndex, pageSize, uId, tId);
		page.setList(articleInfos);
		return page;
	}
	
	/**
	 * 获得最新5篇文章
	 * @param uId
	 * @return
	 */
	public List<ArticleInfo> get5Article(int uId){
		return articleDao.get5Article(uId);
	}
	
	/**
	 * 根据关键词查找文章
	 * @param uId
	 * @param key
	 * @return
	 */
	public Page<ArticleInfo> searchArticles(int uId,String key,int pageIndex,int pageSize){
		List<ArticleInfo> list = articleDao.searchArticleByKey(key, uId);
		Page<ArticleInfo> page =  new Page<>(pageIndex, pageSize, list.size());
		page.setList(list);
		return page;
	}
	
	public Page<ArticleInfo> searchArticles(int uId,String key,int pageIndex,int pageSize,int tId){
		List<ArticleInfo> list = articleDao.searchArticleByKey(key, uId,tId);
		Page<ArticleInfo> page =  new Page<>(pageIndex, pageSize, list.size());
		page.setList(list);
		return page;
	}
	
	
	/**
	 * 删除多篇文章
	 * @param ids
	 * @return
	 */
	public String delCheck(String[] ids,String path) {
		//1.删除文件
		for (String id : ids) {
			ArticleInfo article = articleDao.FindById(Integer.parseInt(id));
			delFile(path, article.getPhoto());
		}
		
		//2.删除数据
		boolean result = articleDao.DeleteCheck(ids);
		if(result) {
			return "删除成功";
		}else {
			return "删除失败";
		}
	}
	
}
