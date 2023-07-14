package com.rssl.phizic.gate;

import com.rssl.phizic.config.ConfigFactory;

/**
 * @author Kidyaev
 * @ created 07.10.2005
 * @ $Author: Roshka $
 * @ $Revision$
 */

/**
 * Точка доступа к единственному экземпляру шлюза
 */
public final class GateSingleton
{
	private static volatile Gate gate = null;

	public static Gate get()
	{
		if (gate == null)
		{
			synchronized (GateSingleton.class)
			{
				if (gate == null)
				{
					GateConfig config = ConfigFactory.getConfig(GateConfig.class);
					gate = config.buildGate();
				}
			}
		}
		return gate;
	}

	public static GateFactory getFactory()
	{
		return get().getFactory();
	}
}