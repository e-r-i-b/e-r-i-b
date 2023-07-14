package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizgate.ext.sbrf.common.messaging.CODFormatResponseHandler;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.EmptyMessagesCacheManager;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.logging.messaging.MessageLogService;
import org.xml.sax.InputSource;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author krenev
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 * Абстрактный, базовый мессаждинг сервис.
 */
public abstract class AbstractCPFLMessagingService extends MessagingServiceSupport
{
	/**
	 * ctor
	 * @param factory фабрика гейта
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	protected AbstractCPFLMessagingService(GateFactory factory) throws GateException
	{
		super(factory, MessageLogService.getMessageLogWriter(),
				"CPFL", ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG));
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		CPFLMessageData cpflMessageData = (CPFLMessageData) response;
		CODFormatResponseHandler handler = new CODFormatResponseHandler(messageInfo);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		StringReader characterStream = new StringReader(cpflMessageData.getBody());
		try
		{
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(characterStream), handler);
			cpflMessageData.setId(handler.getMessageId());
			return handler;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		finally
		{
			characterStream.close();
		}
	}

	/**
	 * Получение кеш менеджера сообщений
	 * @return MessagesCacheManager
	 */
	public MessagesCacheManager getMessagesCacheManager()
	{
		return EmptyMessagesCacheManager.getInstance();
	}
}
