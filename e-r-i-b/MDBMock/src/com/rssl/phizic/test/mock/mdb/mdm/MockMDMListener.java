package com.rssl.phizic.test.mock.mdb.mdm;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.test.mock.mdb.mdm.processors.MockCreateMDMNotificationProcessor;
import com.rssl.phizic.test.mock.mdb.mdm.processors.MockGetFullMDMClientInfoProcessor;
import com.rssl.phizicgate.mdm.integration.mdm.generated.CustAgreemtInqRq;
import com.rssl.phizicgate.mdm.integration.mdm.generated.IFX;
import com.rssl.phizicgate.mdm.integration.mdm.message.JAXBMessageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 20.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушечный слушатель сообщений в мдм
 */

public class MockMDMListener implements MessageDrivenBean, MessageListener
{
	private static final Log log = LogFactory.getLog(LogModule.Web.toString());
	private static final Map<Class, MockMDMMessageProcessor> processors = new HashMap<Class, MockMDMMessageProcessor>();

	static
	{
		processors.put(CustAgreemtInqRq.class, new MockGetFullMDMClientInfoProcessor());
		processors.put(IFX.class, new MockCreateMDMNotificationProcessor());
	}

	protected MockMDMMessageProcessor getProcessor(Object response)
	{
		return processors.get(response.getClass());
	}

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext){}

	public void ejbRemove(){}

	public void onMessage(Message jmsMessage)
	{
		try
		{
			ApplicationInfo.setCurrentApplication(Application.TestApp);

			log.error("[Mock-MDM] Обработка запроса:\n" + ((TextMessage)jmsMessage).getText());

			Object message = JAXBMessageHelper.getInstance().parseMessage((TextMessage) jmsMessage);

			MockMDMMessageProcessor messageProcessor = getProcessor(message);
			if (messageProcessor == null)
			{
				log.error("[Mock-MDM] Не задан обработчик. Тип сообщения " + (message == null ? "неизвестен" : message.getClass().getSimpleName()) + ".");
				return;
			}

			messageProcessor.processMessage(message, jmsMessage.getJMSMessageID());
		}
		catch (Exception e)
		{
			log.error("[Mock-MDM] Ошибка обработки запроса.", e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
			OperationContext.clear();
		}
	}
}
