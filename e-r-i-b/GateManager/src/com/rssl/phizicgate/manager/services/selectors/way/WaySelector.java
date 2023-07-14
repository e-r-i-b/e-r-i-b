package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.config.ExternalSystemConfig;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.documents.mapping.ServiceMapping;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * абстрактный класс селектора
 */

abstract class WaySelector<S>
{
	protected abstract ExternalSystemIntegrationMode getMode(S source, String serviceName) throws GateException;

	final ExternalSystemIntegrationMode getIntegrationMode(S source, String serviceName) throws GateException
	{
		return getDefaultIfNull(getMode(source, serviceName), ExternalSystemIntegrationMode.WS);
	}

	protected static ExternalSystemIntegrationMode getDefaultIfNull(ExternalSystemIntegrationMode source, ExternalSystemIntegrationMode defaultValue)
	{
		return source == null? defaultValue: source;
	}

	protected static ExternalSystemConfig getConfig()
	{
		return ServiceMapping.getConfig();
	}
}
