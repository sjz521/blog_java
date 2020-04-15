package cn.edu.tzc.blog.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.tzc.blog.dao.PhotoDao;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.Photo;
import cn.edu.tzc.blog.domain.PhotoInfo;
import cn.edu.tzc.blog.service.exception.PhotoException;
import cn.edu.tzc.blog.util.FileUtil;

public class PhotoService extends FileUtil {
	private PhotoDao photoDao = new PhotoDao();
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	/**
	 * 根据作者id获得所有图片
	 * @param uId
	 * @return
	 */
	public List<Photo> getAll(int uId) {
		List<Photo> photos = photoDao.getAll(uId);
		/*if(photos == null) {
			throw new PhotoException("博主没有上传照片");
		}*/
		return photos;
	}
	
	/**
	 * 根据作者id获得所有图片相关信息
	 * @param uId
	 * @return
	 */
	public List<PhotoInfo> getAllPhotos(int uId){
		List<PhotoInfo> photos = photoDao.getAllInfo(uId);
		/*if(photos == null) {
			throw new PhotoException("博主没有上传照片");
		}*/
		return photos;
	}
	
	/**
	 * 根据图片id获得图片
	 * @param id
	 * @return
	 */
	public Photo getPhotoById(int id) {
		Photo photo =  photoDao.getPhotoById(id);
		/*if(photo == null) {
			throw new PhotoException("");
		}*/
		return photo;
	}
	
	/**
	 * 根据id删除单张图片
	 * @param id
	 * @param path
	 * @return
	 */
	public String deletePhoto(int id,String path,String filePath) {
		Photo photo = photoDao.getPhotoById(id);
		boolean fileResult = delFile(path,photo.getName());
		if(!fileResult) {
			return "删除"+photo.getName()+"文件失败";
		}
		fileResult = delFile(filePath, photo.getName());
		if(!fileResult) {
			return "删除"+photo.getName()+"文件失败";
		}
		boolean result = photoDao.deletePhoto(id);
		if(result) {
			logger.info("图片"+photo.getName()+"删除成功");
			return "删除成功";
		}else {
			logger.error("图片"+photo.getName()+"删除失败");
			return "删除失败";
		}
	}
	
	/**
	 * 删除全部图片
	 * @param uId
	 * @param path
	 * @return
	 */
	public String deleteAllPhoto(int uId,String path,String filePath) {
		List<Photo> photos = photoDao.getAll(uId);
		boolean fileResult = false;
		for (Photo photo : photos) {
			fileResult = delFile(path, photo.getName());
			if(!fileResult) {
				return "删除"+photo.getName()+"文件失败";
			}
			fileResult = delFile(filePath, photo.getName());
			if(!fileResult) {
				return "删除"+photo.getName()+"文件失败";
			}
		}
		boolean result = photoDao.deleteAllPhoto(uId);
		if(result) {
			logger.info("所有图片删除成功");
			return "删除成功";
		}else {
			logger.error("所有图片删除失败");
			return "删除失败";
		}
	}
	
	/**
	 * 删除多张图片
	 * @param ids
	 * @param path
	 * @return
	 */
	public String deleteChecked(String[] ids,String path,String filePath) {
		//1.删除图片文件
		for (String id : ids) {
			Photo photo = photoDao.getPhotoById(Integer.parseInt(id));
			boolean fileResult = delFile(path, photo.getName());
			if(!fileResult) {
				return "删除"+photo.getName()+"文件失败";
			}
			fileResult = delFile(filePath, photo.getName());
		}
		//2.删除数据库记录
		boolean result = photoDao.deletePhotos(ids);
		if(result) {
			logger.info("部分图片删除成功");
			return "删除成功";
		}
		logger.info("部分图片删除失败");
		return "删除失败";
	}
	
	/**
	 * 添加图片
	 * @param photo
	 * @return
	 */
	public String addPhoto(Photo photo) {
		boolean result = photoDao.addPhoto(photo);
		if(result) {
			logger.info("图片"+photo.getName()+"添加成功");
			return "图片添加成功";
		}else {
			logger.error("图片"+photo.getName()+"添加失败");
			return "图片添加失败";
		}
	}
	
	public int getTotal(int uId) {
		return photoDao.getToal(uId);
	}
	
	public List<PhotoInfo> getPhotoInPage(int uId,int pageIndex,int pageSize){
		return photoDao.getPhotoPage(pageIndex, pageSize, uId);
	}
	
	/**
	 * 获得分页里的图片信息
	 * @param pageIndex
	 * @param pageSize
	 * @param uId
	 * @return
	 */
	public Page<PhotoInfo> findPhotoWithPage(int pageIndex,int pageSize,int uId){
		int totalRecord = photoDao.getToal(uId);
		Page<PhotoInfo> page = new Page<>(pageIndex, pageSize, totalRecord);
		List<PhotoInfo> list = photoDao.getPhotoPage(pageIndex, pageSize, uId);
		page.setList(list);
		return page;
	}
	
	public List<PhotoInfo> get5Photo(int uId){
		return photoDao.get5Photo(uId);
	}
}
