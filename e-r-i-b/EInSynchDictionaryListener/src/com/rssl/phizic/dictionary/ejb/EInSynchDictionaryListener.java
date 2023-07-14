package com.rssl.phizic.dictionary.ejb;

import com.rssl.phizgate.common.profile.MBKCastService;
import com.rssl.phizgate.common.profile.PhoneUpdate;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.system.LogModule;
import org.apache.commons.logging.LogFactory;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Обработчик сообщений на обновление справочника телефонов клиентов сбербанка в EInvoicng
 * @author bogdanov
 * @ created 21.04.15
 * @ $Author$
 * @ $Revision$
 */
public class EInSynchDictionaryListener implements MessageDrivenBean, MessageListener
{
	private static final MBKCastService mbkCastService = new MBKCastService(null);

	public void ejbCreate() throws EJBException
	{
	}

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
	{
	}

	public void ejbRemove() throws EJBException
	{
	}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.Listener);
		try
		{
			ObjectMessage objectMessage = (ObjectMessage) message;
			String jmsType = message.getJMSType();

			if (MBKCastService.START_ACTUALIZE_MESSAGE_NAME.equals(jmsType))
			{
				mbkCastService.addLastPhoneUpdateInfo((PhoneUpdate)objectMessage.getObject());
			}
		}
		catch (Exception e)
		{
			LogFactory.getLog(LogModule.Scheduler.name()).error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}
}
