package cn.edu.tzc.blog.service;

import java.util.ArrayList;
import java.util.List;

import cn.edu.tzc.blog.dao.TypeDao;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.Type;
import cn.edu.tzc.blog.domain.TypeInfo;
import cn.edu.tzc.blog.service.exception.TypeException;

public class TypeService {
	
	private TypeDao typeDao = new TypeDao();
	
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
			message = "添加分类成功";
		}
		else {
			message = "添加分类失败";
		}
		return message;
	}
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public String deleteType(int id) {
		Type type = typeDao.getTypeById(id);
		String message = "标签:"+type.getName();
		boolean result = typeDao.deleteType(id);
		if(result) {
			message += "删除成功";
		}else {
			message += "删除失败";
		}
		return message;
	}
	
	/**
	 * 删除所有分类
	 * @return
	 */
	public String deleteAll() {
		boolean result = typeDao.deleteAll();
		String message = "";
		if(result) {
			message = "全部分类删除成功";
		}else {
			message = "全部分类删除失败";
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
	
	/**
	 * 删除多篇文章
	 * @param ids
	 * @return
	 */
	public String delChecked(String[] ids) {
		boolean result = typeDao.deleteTypes(ids);
		if(result) {
			return "删除成功";
		}
		return "删除失败";
	}
}
