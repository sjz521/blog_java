package cn.edu.tzc.blog.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaSize.ISO;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

import cn.edu.tzc.blog.dao.ArticleDao;
import cn.edu.tzc.blog.dao.UserDao;
import cn.edu.tzc.blog.domain.Article;
import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.service.exception.ArticleException;
import cn.edu.tzc.blog.util.FileUtil;

public class ArticleService extends FileUtil {
	private ArticleDao articleDao = new ArticleDao();
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	/**
	 * 获得全部文章
	 * @param uid
	 * @return
	 */
	public List<ArticleInfo> getAllArticles(int uid){
		return articleDao.getAll(uid);
	}
	
	/**
	 * 查找文章
	 * @param id
	 * @return
	 * @throws ArticleException
	 */
	public ArticleInfo findById(int id) throws ArticleException {
		ArticleInfo articleInfo =  articleDao.findById(id);
		if(articleInfo == null) {
			throw new ArticleException("找不到该文章！！");
		}
		return articleInfo;
	}
	
	/**
	 * 查找分类下的所有文章
	 * @param tId
	 * @param uId
	 * @return
	 */
	public List<ArticleInfo> getArticlesByTid(int tId,int uId){
		return articleDao.getArticlesByTid(tId, uId);
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
		List<ArticleInfo> articles = articleDao.getArticlePage(pageIndex, pageSize, uId);
		return articles;
	}
	
	public List<ArticleInfo> getArticlePage(int uId,int pageIndex,int pageSize,int tId){
		List<ArticleInfo> articles = articleDao.getArticleByTid(pageIndex, pageSize, uId, tId);
		return articles;
	}
	
	/**
	 * 获得文章数
	 * @param uId
	 * @return
	 */
	public int getArticleCount(int uId) {
		return articleDao.getToal(uId);
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
	public String addArticle(Article article) {
		boolean result = articleDao.addArticle(article);
		if(result) {
			logger.info("文章—"+article.getTitle()+"添加成功");
			return "文章添加成功";
		}else {
			logger.info("文章—"+article.getTitle()+"添加失败");
			return "文章添加失败";
		}
	}
	
	/**
	 * 更新文章
	 * @param article
	 * @param imgPath	文章图片在.metadata文件夹里的路径
	 * @param imgPublicPath	文章图片在public文件夹下的路径
	 * @param contentPath	文章内容在.metadata文件夹里的路径
	 * @param contentPublicPath	文章内容在public文件夹的路径
	 */
	public String updateArticle(Article article,String imgPath,String imgPublicPath,String contentPath,String contentPublicPath) {
		Article oldInfo = articleDao.findById(article.getId());
		if(article.getPhoto() != oldInfo.getPhoto()) {
			delFile(imgPath, oldInfo.getPhoto());
			delFile(imgPublicPath, oldInfo.getPhoto());
		}
		if(!article.getContent().equals(oldInfo.getContent())) {
			delFile(contentPath, oldInfo.getContent());
			delFile(contentPublicPath, oldInfo.getContent());
		}
		boolean result = articleDao.updateArticle(article);
		if(result) {
			logger.info("文章—"+article.getTitle()+"修改成功");
			return "文章修改成功";
		}else {
			logger.info("文章—"+article.getTitle()+"修改失败");
			return "文章修改失败";
		}
	}
	
	/**
	 * 删除文章
	 * @param id
	 * @return
	 * @throws ArticleException
	 */
	public String deleteArticle(int id,String path,String publicPath) {
		Article article = articleDao.findById(id);
		boolean fileResult = delFile(path, article.getPhoto());
		fileResult = delFile(publicPath, article.getPhoto());
		if(!fileResult) {
			return "文章相关图片删除失败";
		}
		boolean result = articleDao.deleteArticle(id);
		if(result) {
			logger.info("文章—"+article.getTitle()+"删除成功");
			return "删除成功";
		}else {
			logger.info("文章—"+article.getTitle()+"删除失败");
			return "删除失败";
		}
	}
	
	/**
	 * 删除全部文章
	 * @param uId
	 * @return
	 * @throws ArticleException
	 */
	public String deleteAllArticle(int uId,String path,String publicPath) throws ArticleException {
		List<ArticleInfo> articles = articleDao.getAll(uId);
		for (ArticleInfo articleInfo : articles) {
			delFile(path, articleInfo.getPhoto());
			delFile(publicPath, articleInfo.getPhoto());
		}
		boolean result = articleDao.deleteAll(uId);
		if(result) {
			logger.info("全部文章删除成功");
			return "删除成功";
		}else {
			logger.info("全部文章删除失败");
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
		int totalRecord = articleDao.getToal(uId);
		Page<ArticleInfo> page = new Page<>(pageIndex, pageSize,totalRecord);
		List<ArticleInfo> articleInfos = articleDao.getArticlePage(pageIndex, pageSize, uId);
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
		List<ArticleInfo> articleInfos = articleDao.getArticleByTid(pageIndex, pageSize, uId, tId);
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
	public String delCheck(String[] ids,String path,String publicPath) {
		//1.删除文件
		for (String id : ids) {
			ArticleInfo article = articleDao.findById(Integer.parseInt(id));
			delFile(path, article.getPhoto());
			delFile(publicPath, article.getPhoto());
		}
		
		//2.删除数据
		boolean result = articleDao.deleteArticles(ids);
		if(result) {
			logger.info("部分文章删除成功");
			return "删除成功";
		}else {
			logger.info("部分文章删除失败");
			return "删除失败";
		}
	}
	
	/**
	 * 根据分类id删除文章
	 * @param tId
	 * @param uId
	 */
	public void deleteArticlesByTid(int tId,int uId) {
		List<Integer> articleIds = articleDao.getArticleIdsByTid(tId, uId);
		String[] ids = integerListToStrings(articleIds);
		articleDao.deleteArticles(ids);
	}
	
}
