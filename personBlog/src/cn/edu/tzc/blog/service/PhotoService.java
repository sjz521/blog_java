package cn.edu.tzc.blog.service;

import java.util.List;

import cn.edu.tzc.blog.dao.PhotoDao;
import cn.edu.tzc.blog.domain.FileHelper;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.Photo;
import cn.edu.tzc.blog.domain.PhotoInfo;
import cn.edu.tzc.blog.service.exception.PhotoException;

public class PhotoService extends FileHelper {
	private PhotoDao photoDao = new PhotoDao();
	
	public List<Photo> showAll(int uId) {
		List<Photo> photos = photoDao.ShowAll(uId);
		/*if(photos == null) {
			throw new PhotoException("博主没有上传照片");
		}*/
		return photos;
	}
	
	public List<PhotoInfo> GetAllPhotos(int uId){
		List<PhotoInfo> photos = photoDao.GetAll(uId);
		/*if(photos == null) {
			throw new PhotoException("博主没有上传照片");
		}*/
		return photos;
	}
	
	public Photo GetPhotoById(int id) {
		Photo photo =  photoDao.GetPhotoById(id);
		/*if(photo == null) {
			throw new PhotoException("");
		}*/
		return photo;
	}
	
	public String DeletePhoto(int id,String path) {
		Photo photo = photoDao.GetPhotoById(id);
		boolean fileResult = delFile(path,photo.getName());
		if(!fileResult) {
			return "删除"+photo.getName()+"文件失败";
		}
		boolean result = photoDao.DeletePhoto(id);
		if(result) {
			return "删除成功";
		}else {
			return "删除失败";
		}
	}
	
	public String DeleteAllPhoto(int uId,String path) {
		List<Photo> photos = photoDao.ShowAll(uId);
		boolean fileResult = false;
		for (Photo photo : photos) {
			fileResult = delFile(path, photo.getName());
			if(!fileResult) {
				return "删除"+photo.getName()+"文件失败";
			}
		}
		boolean result = photoDao.DeleteAllPhoto(uId);
		if(result) {
			return "删除成功";
		}else {
			return "删除失败";
		}
	}
	
	public void AddPhoto(Photo photo) {
		photoDao.AddPhoto(photo);
	}
	
	public int GetTotal(int uId) {
		return photoDao.getToal(uId);
	}
	
	public List<PhotoInfo> GetPhotoInPage(int uId,int pageIndex,int pageSize){
		return photoDao.GetPhotoInPage(pageIndex, pageSize, uId);
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
		List<PhotoInfo> list = photoDao.GetPhotoInPage(pageIndex, pageSize, uId);
		page.setList(list);
		return page;
	}
	
	public List<PhotoInfo> get5Photo(int uId){
		return photoDao.get5Photo(uId);
	}
}
