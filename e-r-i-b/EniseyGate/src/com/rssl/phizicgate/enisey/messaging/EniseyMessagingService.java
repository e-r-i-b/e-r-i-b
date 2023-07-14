package com.rssl.phizicgate.enisey.messaging;

import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizicgate.enisey.cache.EniseyMessagesCacheManager;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import java.io.StringReader;

/**
 * @author mihaylov
 * @ created 12.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class EniseyMessagingService extends MessagingServiceSupport
{
	public EniseyMessagingService(GateFactory factory) throws GateException
	{
		super(factory, MessageLogService.getMessageLogWriter(),
				"Enisey", ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG));
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		return null;
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		EniseyMessageData eniseyResponce = (EniseyMessageData) response;
		EniseyResponseHandler handler = new EniseyResponseHandler(messageInfo);

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(eniseyResponce.getBody())),handler);
			eniseyResponce.setId(handler.getMessageId());
			return handler;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return EniseyMessagesCacheManager.getInstance();
	}

}
