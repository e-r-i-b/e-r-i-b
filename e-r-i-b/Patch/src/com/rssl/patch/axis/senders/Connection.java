package com.rssl.patch.axis.senders;

import java.util.Hashtable;

/**
 * Парамметры подключения
 *
 * @author khudyakov
 * @ created 08.06.15
 * @ $Author$
 * @ $Revision$
 */
public class Connection
{
	private String protocol;
	private String host;
	private int port;
	private int timeout;
	private Hashtable options;

	public Connection(String protocol, String host, int port, int timeout, Hashtable options)
	{
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.timeout = timeout;
		this.options = options;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public String getHost()
	{
		return host;
	}

	public int getPort()
	{
		return port;
	}

	public int getTimeout()
	{
		return timeout;
	}

	public Hashtable getOptions()
	{
		return options;
	}
}
