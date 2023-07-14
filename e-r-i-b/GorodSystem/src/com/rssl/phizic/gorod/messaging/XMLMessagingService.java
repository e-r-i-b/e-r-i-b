package com.rssl.phizic.gorod.messaging;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.gorod.messaging.mock.MockXMLMessagingService;
import com.rssl.phizic.gorod.cache.MessagesCacheManagerImpl;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.config.ConfigFactory;
import org.xml.sax.InputSource;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Gainanov
 * @ created 16.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class XMLMessagingService extends MessagingServiceSupport
{
	private static List<XMLMessagingService> services = null;
	private static final int TIMEOUT = 60000;
	private static final int MESSAGE_LENGTH = 1000;

	/**
	 * получаем инстанс сервиса
	 * @return инстанс сервиса
	 * @throws GateException
	 */
	public static synchronized XMLMessagingService getInstance(GateFactory factory) throws GateException
	{
		XMLMessagingService service = null;
		if (services == null)
		{
			service = createService(factory);
			services = new ArrayList<XMLMessagingService>();
			services.add(service);
		}
		else
		{
			for (XMLMessagingService listService : services)
				if (listService.getFactory().equals(factory))
					service = listService;
		}
		if (service == null)
		{
			service = createService(factory);
			services.add(service);
		}
		return service;
	}

	private static XMLMessagingService createService(GateFactory factory) throws GateException
	{
		GorodConfigImpl config = ConfigFactory.getConfig(GorodConfigImpl.class);
		XMLMessagingService service = null;
		if (config.IsMock())
			service = new MockXMLMessagingService(factory);
		else
			service = new XMLMessagingService(factory);
		return service;
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return MessagesCacheManagerImpl.getInstance();
	}

	protected XMLMessagingService(GateFactory factory) throws GateException
	{
		super(factory, MessageLogService.getMessageLogWriter(),
				"GOROD", ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG));
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		GorodConfigImpl config = ConfigFactory.getConfig(GorodConfigImpl.class);
		GorodMessageData message = new GorodMessageData();
		try
		{
			Socket socket = new Socket(Proxy.NO_PROXY);
			try
			{
				socket.connect(new InetSocketAddress(config.getHost(), config.getPort()));
				PrintWriter output = new PrintWriter(socket.getOutputStream());
				output.write(messageData.getBodyAsString("utf-8"));
				output.flush();

				StringBuffer sb = new StringBuffer();
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				socket.setSoTimeout(TIMEOUT);
				int len = 0;
				char[] c = new char[MESSAGE_LENGTH];
				while ((len = reader.read(c)) != -1)
					sb.append(c, 0, len);
				String response = sb.toString();

				message.setBody(response.getBytes());
				return message;
			}
			finally
			{
				socket.close();
			}
		}
		catch (IOException e)
		{
			throw new TemporalGateException(e);
		}
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		response.setBody(response.getBodyAsString("windows-1251").replace("WINDOWS-1251", "windows-1251").getBytes());
		response.setBody(response.getBodyAsString("windows-1251").replace("xsi:nil=\"true\"", "").getBytes());
		GorodMessageData gorodResponse = (GorodMessageData) response;
		GorodResponseHandler gorodHandler = new GorodResponseHandler(messageInfo);
		SAXParserFactory factory = SAXParserFactory.newInstance();
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
			InputSource s = new InputSource(new ByteArrayInputStream((byte[]) gorodResponse.getBody()));
			s.setEncoding("windows-1251");
			saxParser.parse(s, gorodHandler);
//			saxParser.parse(new ByteArrayInputStream((byte[])gorodResponse.getBody()), gorodHandler);

			gorodResponse.setId(gorodHandler.getMessageId());
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return gorodHandler;
	}

	public GateMessage createRequest(String nameRequest) throws GateException
	{
		GorodConfigImpl config = ConfigFactory.getConfig(GorodConfigImpl.class);
		try
		{
			GateMessage msg = super.createRequest(nameRequest);
			msg.addParameter("/Credential/pan", config.getPAN());
			msg.addParameter("/Credential/pin", config.getPIN());
			return msg;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
