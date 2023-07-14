package com.rssl.phizgate.common.services;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.sun.xml.rpc.client.StubBase;

import javax.xml.rpc.Stub;

/**
 * Обертка стаба неинтегрированных шлюзов для обновления url
 * @author gladishev
 * @ created 09.01.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class StubUrlWrapper<S extends java.rmi.Remote> extends StubWrapperBase<S>
{
	private volatile String currentUrl = "";

	public StubUrlWrapper()
	{
		super(null);
	}

	protected String getUrl()
	{
		return ConfigFactory.getConfig(GateConnectionConfig.class).getURL();
	}

	protected void updateStub()
	{
		String actualUrl = getUrl();
		if (!currentUrl.equals(actualUrl))
		{
			currentUrl = actualUrl;
			createStub();
			((StubBase)stub)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, actualUrl);
		}
	}

	protected abstract void createStub();

	protected boolean isActualStub()
	{
		return currentUrl.equals(getUrl());
	}
}