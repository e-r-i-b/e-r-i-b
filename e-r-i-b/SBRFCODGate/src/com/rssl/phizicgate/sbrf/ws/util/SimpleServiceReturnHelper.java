package com.rssl.phizicgate.sbrf.ws.util;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.config.cod.CodGateConfig;

/**
 * Простой хелпер, обслуживающий старый алгоритм шлюзирования.
 *
 * @author bogdanov
 * @ created 05.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class SimpleServiceReturnHelper extends ServiceReturnHelper
{
	public GateFactory factoryFor(AbstractAccountTransfer document) throws GateException
	{
		return GateSingleton.getFactory();
	}

	public <S extends Service> S service(Object routeInfoReturner, Class<S> serviceClass) throws GateException
	{
		return GateSingleton.getFactory().service(serviceClass);
	}

	public String getEndURL(GateFactory factory) throws GateException
	{
		CodGateConfig config = ConfigFactory.getConfig(CodGateConfig.class);
		return config.getCodUrl();
	}
}
