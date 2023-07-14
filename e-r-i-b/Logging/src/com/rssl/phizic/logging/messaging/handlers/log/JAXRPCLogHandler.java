package com.rssl.phizic.logging.messaging.handlers.log;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

/**
 * User: Moshenko
 * Date: 15.07.2010
 * Time: 11:50:40
 */
public abstract class JAXRPCLogHandler extends GenericHandler
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String PASSWORD_KEY = "Password";
	private static final String MOCK_PASSWORD = "***";

	/**
	 * @return systemId системы
	 */
	protected abstract System getSystemId();

	public QName[] getHeaders()
	{
		return new QName[0];
	}

	public boolean handleRequest(MessageContext messageContext)
	{
		if (!isNeedLog())
		{
			return true;
		}

		if (messageContext!=null)
		{
			Date startTime = new Date();
			messageContext.setProperty("startTime",startTime);

		    Calendar date = Calendar.getInstance();
			MessagingLogEntry messagingLogEntry = MessageLogService.createLogEntry();
			messagingLogEntry.setSystem(getSystemId());
			messagingLogEntry.setDate(date);

			try
			{
			  String message = getStringMessage(messageContext);
			  SOAPMessageContext smc = (SOAPMessageContext) messageContext;
			  SOAPMessage soapMessage = smc.getMessage();
			  String messageType =  getMessageType(soapMessage);

			  messagingLogEntry.setMessageRequest(message);
			  messagingLogEntry.setMessageType(messageType);
			  messagingLogEntry.setMessageRequestId("0");

			  messageContext.setProperty("messagingLogEntry",messagingLogEntry);

			}
			catch(Exception e)
			{
				log.error("Проблемы с записью в журнал сообщений", e);
			}
		}
	    return true;
	}

	public boolean handleResponse(MessageContext messageContext)
	{
		if (!isNeedLog())
		{
			return true;
		}

		if (messageContext !=null)
		{
			try
			{
				MessagingLogEntry messagingLogEntry = (MessagingLogEntry)messageContext.getProperty("messagingLogEntry");
				if (messagingLogEntry == null)
				{
					return true;
				}

			   Date startTime = (Date)messageContext.getProperty("startTime");
			   Date endTime = new Date();
			   Long executionTime =  endTime.getTime() - startTime.getTime();
			   messagingLogEntry.setExecutionTime(executionTime);

			   String message = getStringMessage(messageContext);

			   messagingLogEntry.setMessageResponse(message);
			   messagingLogEntry.setMessageResponseId("0");

			   //пишем лог	
			   MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
			   messageLogWriter.write(messagingLogEntry);
			}
			catch(Exception e)
			{
			  log.error("Проблемы с записью в журнал сообщений", e);
			}
		}
		
		return true;
	}

	protected boolean isNeedLog()
	{
		return true;
	}

	/*
	 * Получаем xml документ из контектса.
	 *  @messageContext - конекта
	 */
	private String getStringMessage(MessageContext messageContext) throws SOAPException
	{
		SOAPMessageContext smc = (SOAPMessageContext) messageContext;
		SOAPMessage message = smc.getMessage();
		NodeList nodes = message.getSOAPBody().getFirstChild().getChildNodes();
		Node passwordNode = null;
		String password = null;
		for (int i = 0; i < nodes.getLength(); i++)
		{
			//Заменяем значение элемента Password на ***
			if (nodes.item(i).getLocalName().equalsIgnoreCase(PASSWORD_KEY))
			{
				passwordNode = nodes.item(i);
				password = passwordNode.getTextContent();
				passwordNode.setTextContent(MOCK_PASSWORD);
			}
		}
		StringWriter output = new StringWriter();
		StreamResult streamResult = new StreamResult(output);
		serializeMessage(message, streamResult);
		//Возвращаем значение Password
		if (passwordNode != null)
			passwordNode.setTextContent(password);
		return output.toString();
	}

   /*
	* Сериализуме сообщение
	* @result вывод
	* @msg soap сообщение
	*/
	private static void serializeMessage(SOAPMessage msg, Result result)
	{
      try
      {
		  // Create transformer
	      TransformerFactory tff = TransformerFactory.newInstance();
	      Transformer tf = tff.newTransformer();
	      // Get reply content
	      Source sc = msg.getSOAPPart().getContent();
	      // Set output transformation
	      tf.transform(sc, result);

      }
      catch (Exception e)
      {
           throw new JAXRPCException(e);
      }
	}

	private static String getMessageType(SOAPMessage message)
	{
		if (message == null)
			return "null";

		try {
			Node node = message.getSOAPBody();
			if (node != null)
				node = node.getFirstChild();
			if (node != null)
				return node.getNodeName();
		} catch (SOAPException ignored) {}
		return "unknown";
	}
}
