package com.bossteach.messagemanager.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bossteach.model.Message;

public class MessageManagerAction extends AbstrtactMesssageManagerAction {

	private static final long serialVersionUID = 2229507497640458151L;
	private Log logger = LogFactory.getLog(MessageManagerAction.class);

	/**
	 * add messge by visitor
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addMessage() throws Exception {
		Message message = new Message();
		message.setContent(visitor.getMessage().getContent());
		message.setVisitor(visitor);
		message.setCreateDate(new Date());
		messageManagerService.createMessage(message);
		logger.info("Visitor" + visitor.getName() + "的留言已经保存.");
		return SUCCESS;
	}

	public String listMessage() throws Exception {
		
		return SUCCESS;
	}
}
