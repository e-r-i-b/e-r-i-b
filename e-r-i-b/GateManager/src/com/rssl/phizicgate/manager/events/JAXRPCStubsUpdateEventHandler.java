package com.rssl.phizicgate.manager.events;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.events.EventHandler;
import com.rssl.phizic.gate.GateConfig;
import com.rssl.phizicgate.manager.GateManager;

/**
 * Обработчик события обновления свойств стабов
 * @author gladishev
 * @ created 24.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class JAXRPCStubsUpdateEventHandler implements EventHandler<JAXRPCStubsUpdateEvent>
{
	public JAXRPCStubsUpdateEventHandler()
	{
	}

	public void process(JAXRPCStubsUpdateEvent event) throws Exception
	{
		GateConfig config = ConfigFactory.getConfig(GateConfig.class);
		GateManager.getInstance().updateStubs(config.getTimeout());
	}
}
