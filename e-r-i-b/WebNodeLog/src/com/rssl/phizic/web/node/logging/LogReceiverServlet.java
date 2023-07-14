package com.rssl.phizic.web.node.logging;

import com.rssl.phizic.web.jms.LogReceiverServletBase;
import com.rssl.phizic.web.jms.MessageReceiverCreator;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletConfig;

/**
 * @author krenev
 * @ created 26.01.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������� ������� ��������� ��������� ����������� �� jms.
 */
public class LogReceiverServlet extends LogReceiverServletBase
{

	@Override protected List<MessageReceiverCreator> createReceivers(ServletConfig servletConfig)
	{
		List<MessageReceiverCreator> receiverCreators = new LinkedList<MessageReceiverCreator>();
		//��������� ���������� ��������� ������� ��������� ������������.
		receiverCreators.add(new MessageReceiverCreator("operation-confirm-log", servletConfig));
		//��������� ���������� ��������� ������� ����������� ������.
		receiverCreators.add(new MessageReceiverCreator("logon-log", servletConfig));
		return receiverCreators;
	}
}
