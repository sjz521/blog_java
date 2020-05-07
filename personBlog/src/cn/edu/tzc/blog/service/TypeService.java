package cn.edu.tzc.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.tzc.blog.dao.ArticleDao;
import cn.edu.tzc.blog.dao.TypeDao;
import cn.edu.tzc.blog.domain.ArticleInfo;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.TypeInfo;
import cn.edu.tzc.blog.service.exception.TypeException;

public class TypeService {
	private TypeDao typeDao = new TypeDao();
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private ArticleService articleService = new ArticleService();
	
	/**
	 * 返回所有分类
	 * @return
	 * @throws TypeException
	 */
	public List<Type> getAllTypes(){
		return typeDao.getAll();
	}
	
	/**
	 * 获得分类个数
	 * @return
	 */
	public int getCount() {
		return typeDao.getCount();
	}
	
	/**
	 * 获得部分分类
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<TypeInfo> getTypeInfos(int pageIndex,int pageSize){
		return typeDao.getTypeInfos(pageSize, pageIndex);
	}
	
	/**
	 * 添加分类
	 * @param type
	 * @return
	 */
	/*public boolean addType(Type type) {
		return typeDao.addType(type);
	}*/
	public String addType(Type type) {
		boolean result = typeDao.addType(type);
		String message = "";
		if(result) {
			logger.info("标签"+type.getName()+"添加成功");
			message = "添加标签成功";
		}
		else {
			logger.error("标签"+type.getName()+"添加失败");
			message = "添加标签失败";
		}
		return message;
	}
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public String deleteType(int id,int uId) {
		Type type = typeDao.getTypeById(id);
		String message = "标签:"+type.getName();
		boolean result = typeDao.deleteType(id);
		if(result) {
			logger.info("标签"+type.getName()+"删除成功");
			articleService.deleteArticlesByTid(id, uId);
			message += "删除成功";
		}else {
			logger.error("标签"+type.getName()+"删除失败");
			message += "删除失败";
		}
		return message;
	}
	
	/**
	 * 删除多个分类
	 * @param ids
	 * @return
	 */
	public String delChecked(String[] ids,int uId) {
		for (String id : ids) {
			articleService.deleteArticlesByTid(Integer.parseInt(id), uId);
		}
		boolean result = typeDao.deleteTypes(ids);
		if(result) {
			logger.info("部分标签删除成功");
			return "删除成功";
		}
		logger.error("部分标签删除失败");
		return "删除失败";
	}
	
	/**
	 * 删除所有分类
	 * @return
	 */
	public String deleteAll(int uId) {
		List<Type> types = typeDao.getAll();
		for (Type type : types) {
			articleService.deleteArticlesByTid(type.getId(), uId);
		}
		boolean result = typeDao.deleteAll();
		String message = "";
		if(result) {
			logger.info("全部标签删除成功");
			message = "全部标签删除成功";
		}else {
			logger.error("全部标签删除失败");
			message = "全部标签删除失败";
		}
		return message;
	}
	
	public Type getTypeById(int id){
		return typeDao.getTypeById(id);
	}
	
	/**
	 * 获得分类里的分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<TypeInfo> findTypeWithPage(int pageIndex,int pageSize){
		int totalRecord = typeDao.getCount();
		Page<TypeInfo> page = new Page(pageIndex, pageSize, totalRecord);
		List<TypeInfo> list = typeDao.getTypeInfos(pageSize, pageIndex);
		page.setList(list);
		return page;
	}
	
	
}
