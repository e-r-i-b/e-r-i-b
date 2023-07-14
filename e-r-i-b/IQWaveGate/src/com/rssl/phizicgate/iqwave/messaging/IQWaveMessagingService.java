package com.rssl.phizicgate.iqwave.messaging;

import com.rssl.phizic.config.ConfigFactory;
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

import java.io.StringReader;

import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class IQWaveMessagingService extends MessagingServiceSupport
{
	private static final String RESPONSE_MAX_LENGTH_PROPERTY_KEY = "com.rssl.iccs.iqwave.response.maxLength";

	protected IQWaveMessagingService(GateFactory factory) throws GateException
	{
		super(factory, MessageLogService.getMessageLogWriter(),
				"IQWave", ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG));
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		IQWaveMessageData iqwaveResponse = (IQWaveMessageData) response;
		String maxResponseLength = ConfigFactory.getConfig(PropertyConfig.class).getProperty(RESPONSE_MAX_LENGTH_PROPERTY_KEY);
		if (iqwaveResponse.getBody() != null && !StringHelper.isEmpty(maxResponseLength) && iqwaveResponse.getBody().length() > Long.valueOf(maxResponseLength))
			throw new GateLogicException("Невозможно отобразить запрошенную информацию. Размер ответа от внешней системы превышает установленную настройку.");

		IQWaveMessageHandler handler = new IQWaveMessageHandler(messageInfo);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(iqwaveResponse.getBody())), handler);
			iqwaveResponse.setId(handler.getMessageId());
			return handler;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return IQWaveMessagesCacheManager.getInstance();
	}
}