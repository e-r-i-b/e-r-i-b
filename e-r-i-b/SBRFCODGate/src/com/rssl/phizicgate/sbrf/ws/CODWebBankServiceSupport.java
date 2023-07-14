package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizgate.ext.sbrf.common.messaging.CODFormatResponseHandler;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizicgate.sbrf.ws.cache.MessagesCacheManagerImpl;
import org.xml.sax.InputSource;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Evgrafov
 * @ created 06.03.2007
 * @ $Author: osminin $
 * @ $Revision: 23598 $
 */

public abstract class CODWebBankServiceSupport extends MessagingServiceSupport
{
	/**
	 * service ctor
	 * @param factory фабрика
	 */
	protected CODWebBankServiceSupport(GateFactory factory) throws GateException
	{
		super
				(
						factory,
						MessageLogService.getMessageLogWriter(),
						"COD",
						ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG)
				);
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return MessagesCacheManagerImpl.getInstance();
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		CODMessageData     codResponse = (CODMessageData) response;
		CODFormatResponseHandler codHandler  = new CODFormatResponseHandler(messageInfo);
		SAXParserFactory   factory     = SAXParserFactory.newInstance();
		SAXParser saxParser;

		try
		{
			saxParser = factory.newSAXParser();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		try
		{
			saxParser.parse(new InputSource(new StringReader(codResponse.getBody())), codHandler);

			codResponse.setId(codHandler.getMessageId());
			codResponse.setDate(codHandler.getMessageDate());
			codResponse.setAbonent(codHandler.getFromAbonent());
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return codHandler;
	}
}