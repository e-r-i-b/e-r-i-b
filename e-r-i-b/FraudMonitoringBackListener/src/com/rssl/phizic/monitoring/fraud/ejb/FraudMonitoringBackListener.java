package com.rssl.phizic.monitoring.fraud.ejb;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.monitoring.fraud.messages.MessageProcessor;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Листенер сообщений блокировки профиля клиента
 *
 * @author khudyakov
 * @ created 22.06.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringBackListener implements MessageDrivenBean, MessageListener
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException {}

	public void ejbRemove() throws EJBException {}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.FMListener);

		try
		{
			new MessageProcessor(message).process();
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки сообщения.", e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}
}
