package com.rssl.phizicgate.sofia.messaging;

import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizicgate.sofia.cache.SofiaMessagesCacheManager;

import java.io.StringReader;

import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class SofiaMessagingService extends MessagingServiceSupport
{
	protected SofiaMessagingService(GateFactory factory) throws GateException
	{
		super(factory, MessageLogService.getMessageLogWriter(),
				"Sofia", ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG));
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		SofiaMessageData sofiaResponse = (SofiaMessageData) response;
		SofiaResponseHandler handler = new SofiaResponseHandler(messageInfo);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(sofiaResponse.getBody())), handler);
			sofiaResponse.setId(handler.getMessageId());
			return handler;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return SofiaMessagesCacheManager.getInstance();
	}
}