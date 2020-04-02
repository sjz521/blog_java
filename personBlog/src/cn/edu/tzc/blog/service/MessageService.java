package cn.edu.tzc.blog.service;

import java.util.ArrayList;
import java.util.List;

import cn.edu.tzc.blog.dao.MessageDao;
import cn.edu.tzc.blog.domain.Message;
import cn.edu.tzc.blog.domain.MessageInfo;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.service.exception.MessageException;

public class MessageService {
	private MessageDao dao = new MessageDao();
	
	/**
	 * 获得所有留言数
	 * @return
	 */
	public List<MessageInfo> getAllMessage(){
		List<MessageInfo> replies = dao.getAllMessage();
		if(replies == null) {
			replies = new ArrayList<MessageInfo>();
		}
		return replies;
	}
	
	/**
	 * 添加留言
	 * @param reply
	 */
	public String addMessage(Message reply) {
		boolean result = dao.addMessage(reply);
		if(result) {
			return "留言添加成功";
		}else {
			return "留言添加失败";
		}
	}
	
	/**
	 * 删除某条留言
	 * @param id
	 * @return
	 */
	public String deleteMessage(int id) {
		boolean result = dao.deleteMessage(id);
		if(result) {
			return "删除成功";
		}else {
			return "删除失败";
		}
	}
	
	/**
	 * 删除全部留言
	 * @return
	 */
	public String deleteAllMessage() {
		boolean result = dao.deleteAllMessage();
		if(result) {
			return "删除成功";
		}
		else {
			return "删除失败";
		}
	}
	
	/**
	 * 删除勾选的留言
	 * @param ids
	 * @return
	 */
	public String deleteChecked(String[] ids) {
		boolean result = dao.deleteMessages(ids);
		if(result) {
			return "删除成功";
		}
		return "删除失败";
	}
	
	/**
	 * 获得所有留言数
	 * @return
	 */
	public int getTotal() {
		return dao.getTotal();
	}
	
	/**
	 * 获得某页的留言数
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<MessageInfo> getMessagePage(int pageIndex,int pageSize){
		return dao.getMessagesPage(pageIndex, pageSize);
	}
	
	/**
	 * 获得信息里的分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<MessageInfo> findMessageWithPage(int pageIndex,int pageSize){
		int totalRecord = dao.getTotal();
		Page<MessageInfo> page = new Page<>(pageIndex, pageSize, totalRecord);
		List<MessageInfo> list = dao.getMessagesPage(pageIndex, pageSize);
		page.setList(list);
		return page;
	}
	
	/**
	 * 获得文章下的留言分页
	 * @param pageIndex
	 * @param pageSize
	 * @param aId
	 * @return
	 */
	public Page<MessageInfo> findMessageWithPageByAid(int pageIndex,int pageSize,int aId){
		int totalRecord = dao.getTotal(aId);
		Page<MessageInfo> page = new Page<>(pageIndex, pageSize, totalRecord);
		List<MessageInfo> list = dao.getMessagesPage(pageIndex, pageSize,aId);
		page.setList(list);
		return page;
	}
}
