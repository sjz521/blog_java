package cn.edu.tzc.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.tzc.blog.dao.MessageDao;
import cn.edu.tzc.blog.domain.Message;
import cn.edu.tzc.blog.domain.MessageInfo;
import cn.edu.tzc.blog.domain.Page;
import cn.edu.tzc.blog.domain.User;
import cn.edu.tzc.blog.service.exception.MessageException;
import cn.edu.tzc.blog.util.BasicUtil;

public class MessageService extends BasicUtil {
	private MessageDao dao = new MessageDao();
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
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
		UserService userService = new UserService();
		User user = userService.findUserById(reply.getuId());
		if(result) {
			logger.info(user.getEmail()+"添加留言成功");
			return "留言添加成功";
		}else {
			logger.info(user.getEmail()+"添加留言失败");
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
			logger.info("留言删除成功");
			return "删除成功";
		}else {
			logger.info("留言删除失败");
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
			logger.info("所有留言删除成功");
			return "删除成功";
		}
		else {
			logger.info("所有留言删除失败");
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
			logger.info("部分留言删除成功");
			return "删除成功";
		}
		logger.info("部分留言删除失败");
		return "删除失败";
	}
	
	/**
	 * 删除某个用户的所有留言
	 * @param uId
	 */
	public void deleteMessagesByUid(int uId) {
		List<Integer> messageIds = dao.getMessageByUId(uId);
		String[] ids = integerListToStrings(messageIds);
		deleteChecked(ids);
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
