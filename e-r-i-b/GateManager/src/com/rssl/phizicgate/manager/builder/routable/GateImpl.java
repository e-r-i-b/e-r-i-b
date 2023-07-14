package com.rssl.phizicgate.manager.builder.routable;

import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.SimpleServiceCreator;
import com.rssl.phizic.gate.cache.CacheServiceCreator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.routing.Node;

/**
 * @author Krenev
 * @ created 20.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateImpl implements Gate
{
	private Node node;
	private GateFactory gateFactory;

	public GateImpl(Node node)
	{
		this.node = node;
	}

	public GateFactory getFactory()
	{
		return gateFactory;
	}

	public void initialize() throws GateException
	{
		GateFactory tmp = new GateFactoryImpl(node, new SimpleServiceCreator());
		tmp.initialize();
		gateFactory = tmp;
	}
}
