package com.rssl.phizic.esb.ejb.mdm;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.esb.ejb.mdm.processors.CreateOrReplaceProfileAndProductsProcessor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizicgate.mdm.integration.mdm.generated.CustAgreemtModNf;
import com.rssl.phizicgate.mdm.integration.mdm.message.JAXBMessageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 17.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Слушатель оповещений мдм
 */

public class Listener implements MessageDrivenBean, MessageListener
{
	private static final Log log = LogFactory.getLog(LogModule.Web.toString());
	private static final Map<Class, MessageProcessor> processors = new HashMap<Class, MessageProcessor>();

	static
	{
		processors.put(CustAgreemtModNf.class,  new CreateOrReplaceProfileAndProductsProcessor());
	}

	protected MessageProcessor getProcessor(Object response)
	{
		return processors.get(response.getClass());
	}

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext){}

	public void ejbRemove(){}

	public void onMessage(Message jmsMessage)
	{
		String messageXMLString = null;
		String messageId =  null;
		String messageType = null;
		try
		{
			ApplicationInfo.setCurrentApplication(Application.MDMListener);
			messageXMLString = ((TextMessage)jmsMessage).getText();

			Object message = JAXBMessageHelper.getInstance().parseMessage((TextMessage) jmsMessage);

			MessageProcessor messageProcessor = getProcessor(message);
			if (messageProcessor == null)
			{
				log.error("Не задан доработчик оффлайн ответа для сегмента МДМ. Тип сообщения " + (message == null ? "неизвестен" : message.getClass().getSimpleName()) + ".");
				return;
			}

			messageType = messageProcessor.getMessageType(message);
			messageId = messageProcessor.getMessageId(message);
			messageProcessor.processMessage(message);
		}
		catch (JMSException e)
		{
			log.error("Ошибка обработки запроса ["+ messageType + "]", e);
		}
		catch (GateException e)
		{
			log.error("Ошибка обработки запроса ["+ messageType + "]", e);
		}
		catch (GateLogicException e)
		{
			log.error("Ошибка обработки запроса ["+ messageType + "]", e);
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки запроса ["+ messageType + "]", e);
		}
		finally
		{
			writeMessageLog(messageXMLString, messageId, messageType);
			ApplicationInfo.setDefaultApplication();
			OperationContext.clear();
		}
	}

	private void writeMessageLog(String messageXMLString, String messageId, String messageType)
	{
		if (messageXMLString == null)
			return;

		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setSystem(System.mdm);
			logEntry.setMessageType(messageType);
			logEntry.setMessageRequestId(messageId);
			logEntry.setMessageRequest(messageXMLString);
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Проблемы с записью в журнал сообщений", e);
		}
		finally
		{
			LogThreadContext.clear();
		}
	}
}
