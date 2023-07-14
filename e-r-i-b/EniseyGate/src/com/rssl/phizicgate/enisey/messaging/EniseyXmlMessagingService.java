package com.rssl.phizicgate.enisey.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

/**
 * @author gladishev
 * @ created 08.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class EniseyXmlMessagingService extends EniseyMessagingService
{
	private static final int MESSAGE_LENGTH = 1000;


	public EniseyXmlMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		EniseyMessageData message = new EniseyMessageData();

		GateConnectionConfig connectionConfig = ConfigFactory.getConfig(GateConnectionConfig.class);

		try
		{
			Socket socket = new Socket(Proxy.NO_PROXY);
			try
			{
				socket.connect(new InetSocketAddress(connectionConfig.getHost(), Integer.parseInt( connectionConfig.getPort())));
				OutputStream outStream = socket.getOutputStream();
				byte[] data = ((String) messageData.getBody()).getBytes("UTF-8");
				outStream.write(getLengthString(data.length).getBytes("UTF-8"));
				outStream.write(data);
				outStream.flush();

				StringBuffer sb = new StringBuffer();
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				socket.setSoTimeout(connectionConfig.getConnectionTimeout().intValue());
				int len = 0;
				char[] c = new char[MESSAGE_LENGTH];
				while ((len = reader.read(c)) != -1)
					sb.append(c, 0, len);

				message.setBody(sb.delete(0, 4).toString());
				return message;
			}
			finally
			{
				socket.close();
			}
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
	}

	private String getLengthString(int length)
	{
		StringBuffer lengthStr = new StringBuffer(Long.toHexString(length));
		while (lengthStr.length()<4)
			lengthStr.insert(0, "0");
		return lengthStr.toString();
	}
}
