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
	public List<MessageInfo> GetAllReply(){
		List<MessageInfo> replies = dao.GetAllReply();
		if(replies == null) {
			replies = new ArrayList<MessageInfo>();
		}
		return replies;
	}
	
	/**
	 * 添加留言
	 * @param reply
	 */
	public boolean AddReply(Message reply) {
		return dao.AddReply(reply);
	}
	
	/**
	 * 删除某条留言
	 * @param id
	 * @return
	 */
	public String DeleteMessage(int id) {
		boolean result = dao.DeleteMessage(id);
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
	public String DeleteAllMessage() {
		boolean result = dao.DeleteAllMessage();
		if(result) {
			return "删除成功";
		}
		else {
			return "删除失败";
		}
	}
	
	/**
	 * 获得所有留言数
	 * @return
	 */
	public int GetTotal() {
		return dao.GetTotal();
	}
	
	/**
	 * 获得某页的留言数
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<MessageInfo> GetMessagePage(int pageIndex,int pageSize){
		return dao.GetMessagesPage(pageIndex, pageSize);
	}
	
	/**
	 * 获得信息里的分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<MessageInfo> findMessageWithPage(int pageIndex,int pageSize){
		int totalRecord = dao.GetTotal();
		Page<MessageInfo> page = new Page<>(pageIndex, pageSize, totalRecord);
		List<MessageInfo> list = dao.GetMessagesPage(pageIndex, pageSize);
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
		int totalRecord = dao.GetTotal(aId);
		Page<MessageInfo> page = new Page<>(pageIndex, pageSize, totalRecord);
		List<MessageInfo> list = dao.GetMessagesPage(pageIndex, pageSize,aId);
		page.setList(list);
		return page;
	}
}
