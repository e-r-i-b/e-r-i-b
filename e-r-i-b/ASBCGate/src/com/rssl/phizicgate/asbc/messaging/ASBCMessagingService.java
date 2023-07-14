package com.rssl.phizicgate.asbc.messaging;

import billing.FilialFacade;
import billing.FilialFacadeFactory;
import billing.FilialFacadeFactoryHome;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.utils.naming.NamingHelper;
import com.rssl.phizicgate.asbc.cache.ASBCMessagesCacheManager;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.xml.sax.InputSource;

import java.io.StringReader;
import javax.rmi.PortableRemoteObject;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Krenev
 * @ created 01.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCMessagingService extends MessagingServiceSupport
{
	private final java.lang.Object lock = new java.lang.Object();
	private volatile FilialFacadeFactoryHome filialFacadeFactoryHome;

	public ASBCMessagingService(GateFactory factory) throws GateException
	{
		super(factory, MessageLogService.getMessageLogWriter(),
				"ASBC", ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG));
	}

	private FilialFacadeFactoryHome getFilialFacadeFactoryHome() throws Exception
	{
		if (filialFacadeFactoryHome != null)
		{
			return filialFacadeFactoryHome;
		}
		synchronized (lock)
		{
			if (filialFacadeFactoryHome == null)
			{
				GateConnectionConfig connectionConfig = ConfigFactory.getConfig(GateConnectionConfig.class);
				ORB orb = (ORB) NamingHelper.getInitialContext().lookup("java:comp/ORB");
				Object object = orb.string_to_object(connectionConfig.getURL());
				NamingContextExt namingContext = NamingContextExtHelper.narrow(object);
				Object objectEJB = namingContext.resolve_str("ejb/billing/FilialFacadeFactoryHome");
				filialFacadeFactoryHome = (FilialFacadeFactoryHome) PortableRemoteObject.narrow(objectEJB, FilialFacadeFactoryHome.class);
			}
			return filialFacadeFactoryHome;
		}
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		ASBCMessageData asbcMessageData = (ASBCMessageData) messageData;
		//получаем параметры подключения
		GateConnectionConfig connectionConfig = ConfigFactory.getConfig(GateConnectionConfig.class);
		try
		{
			FilialFacadeFactory filialFacadeFactory = getFilialFacadeFactoryHome().create();
			FilialFacade filialFacade = filialFacadeFactory.getFilialFacade(connectionConfig.getUserName().getBytes(), connectionConfig.getPassword().getBytes());
			byte[] result = filialFacade.executeCommand(asbcMessageData.getBody());
			return new ASBCMessageData(result);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		ASBCMessageData asbcResponse = (ASBCMessageData) response;
		ASBCResponseHandler handler = new ASBCResponseHandler(messageInfo);

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(asbcResponse.getBodyAsString(null))), handler);
			asbcResponse.setId(handler.getMessageId());
			return handler;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return ASBCMessagesCacheManager.getInstance();
	}
}
