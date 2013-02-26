package com.bossteach.messagemanager.action;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bossteach.core.util.ci.ComponentInterfaceFactory;
import com.bossteach.messagemanager.ci.VisitorManagerCI;
import com.bossteach.model.Visitor;

public class MessageManagerAction extends AbstrtactMesssageManagerAction {

	private static final long serialVersionUID = 2229507497640458151L;
	private Log logger = LogFactory.getLog(MessageManagerAction.class);

	/**
	 * add message by visitor	
	 * @return
	 * @throws Exception
	 */
	public String addMessage() throws Exception {
		VisitorManagerCI visitorManagerCI =(VisitorManagerCI)ComponentInterfaceFactory.getComponentInteface("visitorManagerCI");
		List<Visitor> visitors = visitorManagerCI.findVisitor(message.getVisitor().getMail().trim());		
		if (visitors != null && visitors.size()>0) {			
			message.setVisitor(visitors.get(0));
		} else {
			Visitor visitor = message.getVisitor();
			visitor.setCreatedDate(new Date());
			visitor.setActive(true);						
		}
		message.setCreatedDate(new Date());
		messageManagerService.createMessage(message);
		logger.info("Visitor" + message.getVisitor().getName() + "的留言已经保存.");
		return NONE;
	}

	public String listMessage() throws Exception {
		messages = messageManagerService.listMessage();
		return SUCCESS;
	}
	
	public String contactus()throws Exception{
		logger.info("联系我们...");
		return SUCCESS;
	}
}
