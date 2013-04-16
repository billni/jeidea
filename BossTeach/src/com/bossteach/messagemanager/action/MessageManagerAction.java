package com.bossteach.messagemanager.action;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bossteach.model.Visitor;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class MessageManagerAction extends AbstrtactMesssageManagerAction {

	private static final long serialVersionUID = 2229507497640458151L;
	private Log logger = LogFactory.getLog(MessageManagerAction.class);

	/**
	 * add message by visitor	
	 * @return
	 * @throws Exception
	 */
	public String addMessage() throws Exception {
		Visitor visitor = message.getVisitor();
		visitor.setCreatedDate(new Date());
		visitor.setActive(true);
		message.setCreatedDate(new Date());
		messageManagerService.createMessage(message);
		logger.info("Visitor" + message.getVisitor().getName() + "的留言已经保存.");
		return NONE;
	}

	public String listMessage() throws Exception {
		messages = messageManagerService.listMessage();
		setResultObj(messages);
		return SUCCESS;
	}
	
	public String contactus()throws Exception{
		logger.info("联系我们...");
		return SUCCESS;
	}
	
	public void getUser(){
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
        }
	}
}
