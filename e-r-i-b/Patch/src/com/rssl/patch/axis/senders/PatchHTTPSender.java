package com.rssl.patch.axis.senders;

import com.rssl.patch.axis.senders.factory.SenderFactoryManager;
import org.apache.axis.MessageContext;
import org.apache.axis.components.net.BooleanHolder;
import org.apache.axis.components.net.DefaultSocketFactory;
import org.apache.axis.components.net.SocketFactory;
import org.apache.axis.transport.http.HTTPSender;
import org.apache.axis.transport.http.SocketHolder;
import org.apache.axis.utils.Messages;

import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;

/**
 * Http сендер, "расшир€ющий" способности базового сендера
 *
 * @author khudyakov
 * @ created 05.06.15
 * @ $Author$
 * @ $Revision$
 */
public class PatchHTTPSender extends HTTPSender
{
	/**
	 * Creates a socket connection to the SOAP server
	 *
	 * @param protocol "http" for standard, "https" for ssl.
	 * @param host host name
	 * @param port port to connect to
	 * @param otherHeaders buffer for storing additional headers that need to be sent
	 * @param useFullURL flag to indicate if the complete URL has to be sent
	 *
	 * @throws java.io.IOException
	 */
	protected void getSocket(SocketHolder sockHolder, MessageContext msgContext, String protocol, String host, int port, int timeout, StringBuffer otherHeaders, BooleanHolder useFullURL) throws Exception
	{
		SocketFactory factory = SenderFactoryManager.getInstance().getFactory(msgContext, new Connection(protocol, host, port, timeout, getAttributes(timeout)));
		if (factory == null)
		{
			throw new IOException(Messages.getMessage("noSocketFactory", protocol));
		}

		Socket socket = factory.create(host, port, otherHeaders, useFullURL);
		if (timeout > 0)
		{
			socket.setSoTimeout(timeout);
		}
		sockHolder.setSocket(socket);
	}

	private Hashtable getAttributes(int timeout)
	{
		Hashtable options = getOptions();
		if (timeout > 0)
		{
			if (options == null)
			{
				options = new Hashtable();
			}
			options.put(DefaultSocketFactory.CONNECT_TIMEOUT, Integer.toString(timeout));
		}
		return options;
	}
}
