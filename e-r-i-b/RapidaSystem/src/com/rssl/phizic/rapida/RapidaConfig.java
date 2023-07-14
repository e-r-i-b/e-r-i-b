package com.rssl.phizic.rapida;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * @author Krenev
 * @ created 28.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class RapidaConfig
{
	private static final String PROTOCOL_KEY = "rapida.protocol";
	private static final String HOST_KEY = "rapida.host";
	private static final String RECONNECT_TRY_COUNT_KEY = "rapida.connection.reconnect.try.count";
	private static final String RECONNECT_TIMEOUT_KEY = "rapida.connection.reconnect.timeout";
	private static final String TIMEOUT_KEY = "rapida.connection.read.timeout";
	private static final String TERMINAL_ID = "rapida.terminal.id";
	private static final String QUERY_PREFIX = "rapida.query.pefix";
	private static final String PAYMENT_ID_PREFIX = "rapida.payment.id.pefix";

	private static final Properties properties = readProperties();

	private static Properties readProperties()
	{
		Properties temp = new Properties(System.getProperties());
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("rapida.properties");

		try
		{
			temp.load(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return temp;
	}

	public String getProtocol(){
		return properties.getProperty(PROTOCOL_KEY);
	}

	public String getHost(){
		return properties.getProperty(HOST_KEY);
	}

	public int getReconnectTryCount(){
		return Integer.parseInt(properties.getProperty(RECONNECT_TRY_COUNT_KEY));
	}

	public int  getReconnectTimeout(){
		return Integer.parseInt(properties.getProperty(RECONNECT_TIMEOUT_KEY));
	}

	public int getTimeout(){
		return Integer.parseInt(properties.getProperty(TIMEOUT_KEY));
	}

	public Object getTerminalID()
	{
		return properties.getProperty(TERMINAL_ID);
	}

	public String getQueryPrefix()
	{
		return properties.getProperty(QUERY_PREFIX);
	}

	public String getPaymentIDPrefix()
	{
		return properties.getProperty(PAYMENT_ID_PREFIX);
	}
}
