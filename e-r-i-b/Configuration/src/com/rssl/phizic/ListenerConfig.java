package com.rssl.phizic;

import com.rssl.phizic.config.*;

/**
 * Конфиг для считывания настроек листенера (веб-сервиса сервера на стороне ИКФЛ)
 * @author egorova
 * @ created 10.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListenerConfig extends Config
{
	private static final String LISTENER_URL = "com.rssl.phizic.wsgate.listener.url";
	private static final String NODE_LISTENER_URL = "com.rssl.phizic.wsgate.node.listener.url";
	private static final String SERVICE_REDIRECT_URL = "com.rssl.phizic.service.redirect.url";
	private static final String NODE_WS_URL = "com.rssl.phizic.gate.backservice.hostname.";

	public ListenerConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
	}

	public String getNodeUrl()
	{
		return getProperty(NODE_LISTENER_URL);
	}

	public String getUrl()
	{
		return getProperty(LISTENER_URL);
	}

	public String getRedirectServiceUrl()
	{
		return getProperty(SERVICE_REDIRECT_URL);
	}

	public String getNodeWebServiceUrl(Long nodeId)
	{
		return getProperty(NODE_WS_URL + nodeId.toString());
	}
}
