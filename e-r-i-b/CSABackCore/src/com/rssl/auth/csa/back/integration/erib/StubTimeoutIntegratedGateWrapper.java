package com.rssl.auth.csa.back.integration.erib;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.integration.erib.generated.CSABackRefService;
import com.rssl.auth.csa.back.integration.erib.generated.CSABackRefServiceImpl_Impl;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.phizgate.common.services.StubTimeoutUrlWrapper;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author akrenev
 * @ created 18.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Обертка стаба неинтегрированных шлюзов для обновления таймаута и урла
 */

class StubTimeoutIntegratedGateWrapper extends StubTimeoutUrlWrapper<CSABackRefService>
{
	private final Config config;
	private final Node node;

	private StubTimeoutIntegratedGateWrapper(Node node)
	{
		super();
		config = ConfigFactory.getConfig(Config.class);
		this.node = node;
	}

	static CSABackRefService getStub(Node node)
	{
		return new StubTimeoutIntegratedGateWrapper(node).getStub();
	}

	public Integer getTimeout()
	{
		return config.getERIBCsaBackTimeout();
	}

	protected String getUrl()
	{
		return String.format(config.getERIBURL(), node.getListenerHostname());
	}

	protected void createStub()
	{
		setStub(new CSABackRefServiceImpl_Impl().getCSABackRefServicePort());
	}
}
