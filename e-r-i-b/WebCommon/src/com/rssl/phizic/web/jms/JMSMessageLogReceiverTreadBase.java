package com.rssl.phizic.web.jms;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.messaging.FinancialMessagingLogEntry;
import com.rssl.phizic.logging.messaging.MessageTranslateHelper;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.utils.naming.NamingHelper;
import org.apache.commons.logging.LogFactory;

import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.List;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Обработчик сообщения для MessageLog.
 *
 * @author bogdanov
 * @ created 30.04.2015
 * @ $Author$
 * @ $Revision$
 *
 */

public class JMSMessageLogReceiverTreadBase extends JMSReceiverTreadBase
{

	public JMSMessageLogReceiverTreadBase(long timeout, int batchSize, int flushTryCount, String queueName, String queueFactoryName, String dbInstanceName)
	{
		super(timeout, batchSize, flushTryCount, queueName, queueFactoryName, dbInstanceName);
	}

	protected Object getObjectFromMessage(Message msg) throws JMSException
	{
	    Object obj = super.getObjectFromMessage(msg);
		if (obj == null)
			return obj;

		MessagingLogEntry entry = (MessagingLogEntry) obj;
		ApplicationInfo.setCurrentApplication(Application.WebLog);
		try
		{
			if (MessageTranslateHelper.isFinancial(entry.getMessageType()))
				return new FinancialMessagingLogEntry(entry);

			return entry;
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}
}