package com.rssl.phizgate.common.services;

import com.sun.xml.rpc.client.StubBase;

import javax.xml.rpc.Stub;

/**
 * Обертка стаба неинтегрированных шлюзов для обновления таймаута и урла
 * @author gladishev
 * @ created 09.01.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class StubTimeoutUrlWrapper<S extends java.rmi.Remote> extends StubTimeoutWrapper<S>
{
	private volatile String currentUrl = "";

	protected StubTimeoutUrlWrapper()
	{
		super(null);
	}

	protected String getUrl()
	{
		return getConfig().getURL();
	}

	protected void updateStub()
	{
		String actualUrl = getUrl();
		if (!currentUrl.equals(actualUrl))
		{
			super.resetParameters(); //очищаем параметры для обновления остальных свойств стаба после пересоздания
			currentUrl = actualUrl;
			createStub();
			((StubBase)stub)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, actualUrl);
		}
		super.updateStub();
	}

	protected boolean isActualStub()
	{
		return currentUrl.equals(getUrl()) && super.isActualStub();
	}

	protected abstract void createStub();
}
