package com.rssl.phizicgate.manager.builder.persistent;

import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.CacheServiceCreator;
import com.rssl.phizic.gate.impl.SimpleServiceCreator;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author hudyakov
 * @ created 10.12.2009
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
