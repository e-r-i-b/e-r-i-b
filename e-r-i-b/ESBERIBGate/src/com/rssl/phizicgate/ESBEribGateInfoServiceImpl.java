package com.rssl.phizicgate;

import com.rssl.phizgate.common.services.GateInfoServiceImpl;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.GateSettingsConfig;

/**
 * Ёкземпл€р сервиса информации о возможност€х шлюза дл€ шины.
 * @author niculichev
 * @ created 30.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ESBEribGateInfoServiceImpl extends GateInfoServiceImpl
{
	public ESBEribGateInfoServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected GateSettingsConfig getGateSettingsConfig()
	{
		return ConfigFactory.getConfig(GateSettingsConfig.class, Application.ESBERIBListener);
	}
}
