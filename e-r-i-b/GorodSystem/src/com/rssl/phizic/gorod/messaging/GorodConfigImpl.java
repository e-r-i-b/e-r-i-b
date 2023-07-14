package com.rssl.phizic.gorod.messaging;

import com.rssl.phizic.config.*;

/**
 * @author Gainanov
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodConfigImpl extends Config implements GorodConfigImplMBean
{
	private static final String MOCK_KEY = "gorod.mock";
	private static final String HOST_KEY = "gorod.host";
	private static final String PORT_KEY = "gorod.port";
	private static final String PAN_KEY = "gorod.ikfl.pan";
	private static final String PIN_KEY = "gorod.ikfl.pin";
	private static final String CLIENT_PAN_KEY = "gorod.client.pan";
	private static final String IKFL_PREF_KEY = "gorod.ikfl.prefix";
	private static final String IKFL_POST_KEY = "gorod.ikfl.postfix";

	public GorodConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public void printStartMessage()
	{
		//вызывается еще до готовности логов, поэтому пишем так
		System.out.println("JMXBean "+GorodConfigImpl.class.getCanonicalName() + " успешно запущен");
	}

	public void doRefresh() throws ConfigurationException
	{
	}

	public boolean IsMock()
	{
		return Boolean.parseBoolean(getProperty(MOCK_KEY));
	}

	public String getHost()
	{
		return getProperty(HOST_KEY);
	}

	public int getPort()
	{
		return Integer.parseInt(getProperty(PORT_KEY));
	}

	public String getPAN()
	{
		return getProperty(PAN_KEY);
	}

	public String getPIN()
	{
		return getProperty(PIN_KEY);
	}

	public String getPrefix()
	{
		return getProperty(IKFL_PREF_KEY);
	}

	public String getPostFix()
	{
		return getProperty(IKFL_POST_KEY);
	}

	public String getClientPan()
	{
		return getProperty(CLIENT_PAN_KEY);
	}
}
