package com.rssl.phizicgate.manager.builder.persistentRoutable;

import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.CacheServiceCreator;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Гейт персистентно-роутерной части.
 *
 * @author bogdanov
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class GateImpl implements Gate
{
	private GateFactory gateFactory;

	public GateFactory getFactory()
	{
		return gateFactory;
	}

	public void initialize() throws GateException
	{
		GateFactory tmp = new GateFactoryImpl(new CacheServiceCreator());
		tmp.initialize();
		gateFactory = tmp;
	}
}
