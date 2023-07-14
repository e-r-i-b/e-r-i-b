package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */
class GateFactoryMock implements GateFactory
{
	public void initialize() throws GateException
	{
	}

	public <S extends Service> S service(Class<S> serviceInterface)
	{
		return null;
	}

	public <C extends GateConfig> C config(Class<C> configInterface)
	{
		return null;
	}

	public Collection<? extends Service> services()
	{
		throw new UnsupportedOperationException();
	}
}
