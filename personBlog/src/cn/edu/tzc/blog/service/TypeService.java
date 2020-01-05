package cn.edu.tzc.blog.service;

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
	public List<Type> showAllTypes(){
		return typeDao.showAllTypes();
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
	public boolean addType(Type type) {
		return typeDao.addType(type);
	}
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public boolean deleteType(int id) {
		return typeDao.deleteType(id);
	}
	
	/**
	 * 删除所有分类
	 * @return
	 */
	public boolean deleteAll() {
		return typeDao.deleteAll();
	}
	
	public Type getTypeById(int id) throws TypeException {
		Type type = typeDao.getTypeById(id);
		if(type == null){
			throw new TypeException("未找到相应的分类");
		}
		
		return type;
	}
	
	/**
	 * 获得分类里的分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<TypeInfo> findTypeWithPage(int pageIndex,int pageSize){
		int totalRecord = typeDao.getCount();
		Page<TypeInfo> page = new Page<>(pageIndex, pageSize, totalRecord);
		List<TypeInfo> list = typeDao.getTypeInfos(pageSize, pageIndex);
		page.setList(list);
		return page;
	}
}
