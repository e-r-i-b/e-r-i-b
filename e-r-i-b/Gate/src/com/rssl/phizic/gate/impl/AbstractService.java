package com.rssl.phizic.gate.impl;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author Evgrafov
 * @ created 19.05.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57422 $
 */
public abstract class AbstractService implements Service
{
	protected static final String BUSINESS_DELEGATE_KEY = ".delegate.business";
	protected static final String GATE_DELEGATE_KEY = ".delegate.gate";
	protected static final String ROUTABLE_DELEGATE_KEY = ".delegate.routable";	
	protected static final String DELEGATE_KEY = ".delegate";
	protected static final String ESB_DELEGATE_KEY = ".delegate.esb";

	private GateFactory factory;

	protected AbstractService(GateFactory factory)
	{
		this.factory = factory;
	}

	public GateFactory getFactory()
	{
		return factory;
	}

	protected AbstractService getDelegate(String delegateKey)
	{
		try
		{
			String delegateClassName = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(delegateKey);
			Class classManager = ClassHelper.loadClass(delegateClassName);
			return (AbstractService) classManager.getConstructor(GateFactory.class).newInstance(getFactory());
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
}