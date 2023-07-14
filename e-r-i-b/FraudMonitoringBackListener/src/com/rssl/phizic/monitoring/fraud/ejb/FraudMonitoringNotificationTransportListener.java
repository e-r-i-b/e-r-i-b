package com.rssl.phizic.monitoring.fraud.ejb;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.monitoring.fraud.messages.NotificationTransportMessageProcessor;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author tisov
 * @ created 17.07.15
 * @ $Author$
 * @ $Revision$
 * Приёмник JMS-сообщений с фрод-оповещений от других приложений(на данный момент - от CSABack)
 */
public class FraudMonitoringNotificationTransportListener implements MessageDrivenBean, MessageListener
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException {}

	public void ejbRemove() throws EJBException {}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.FMListener);

		try
		{
			new NotificationTransportMessageProcessor(message).process();
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}
}
