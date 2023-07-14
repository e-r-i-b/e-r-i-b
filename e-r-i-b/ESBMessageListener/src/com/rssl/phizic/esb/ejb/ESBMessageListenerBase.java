package com.rssl.phizic.esb.ejb;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author komarov
 * @ created 30.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовый слушатель длинной очереди сегмента шины
 */
public abstract class ESBMessageListenerBase implements MessageDrivenBean, MessageListener
{
	private static final Log log = LogFactory.getLog(LogModule.Web.toString());

	protected abstract ESBSegment getSegment();
	protected abstract MessageProcessor getProcessor(Object response);

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext){}

	public void ejbRemove(){}

	public void onMessage(Message message)
	{
		String responseXMLString = null;
		String responseId =  null;
		String messageType = null;
		try
		{
			ApplicationInfo.setCurrentApplication(Application.ESBERIBListener);
			responseXMLString = ((TextMessage)message).getText();

			ESBSegment segment = getSegment();

			Object response = segment.getMessageParser().parseMessage((TextMessage) message);

			MessageProcessor messageProcessor = getProcessor(response);
			if (messageProcessor == null)
			{
				log.error("Не задан доработчик оффлайн ответа для сегмента " + segment + ". Тип сообщения " + (response == null? "неизвестен": response.getClass().getSimpleName()) + ".");
				return;
			}

			messageType = messageProcessor.getMessageType(response);
			responseId = messageProcessor.getMessageId(response);
			messageProcessor.processMessage(response, message);
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
			writeMessageLog(responseXMLString, responseId, messageType);
			ApplicationInfo.setDefaultApplication();
			OperationContext.clear();
		}
	}

	private void writeMessageLog(String responseXMLString, String responseId, String messageType)
	{
		if (responseXMLString == null)
			return;

		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setSystem(System.esberib);
			logEntry.setMessageType(messageType);
			logEntry.setMessageRequestId(responseId);
			logEntry.setMessageRequest(responseXMLString);
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
