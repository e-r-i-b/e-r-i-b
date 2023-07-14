package com.rssl.phizicgate.manager.builder.endService;

import com.rssl.phizic.gate.config.RefreshGateConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.impl.SimpleServiceCreator;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Гейт конечных сервисов.
 *
 * @author bogdanov
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class GateImpl implements Gate
{
	private RefreshGateConfig config;
	private GateFactory gateFactory;

	public GateImpl(RefreshGateConfig config)
	{
		this.config = config;
	}

	public GateFactory getFactory()
	{
		return gateFactory;
	}

	public void initialize() throws GateException
	{
		GateFactory tmp = new GateFactoryImpl(config, new SimpleServiceCreator());
		tmp.initialize();
		gateFactory = tmp;
	}
}
