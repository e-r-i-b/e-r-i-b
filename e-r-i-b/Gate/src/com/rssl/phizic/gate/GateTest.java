package com.rssl.phizic.gate;

import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import com.rssl.phizic.config.ConfigFactory;
import junit.framework.Assert;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 07.10.2005 Time: 14:32:28 */
public class GateTest extends RSSLTestCaseBase
{
	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRSGate();
	}

	public void testGateCreate() throws GateException
	{
		GateConfig config = ConfigFactory.getConfig(GateConfig.class);

		String gateClass = config.getGateClass();
		Assert.assertNotNull(gateClass);

		Gate gate = config.buildGate();
		Assert.assertNotNull(gate);
	}

	public void testGateSingleton()
	{
		Assert.assertNotNull(GateSingleton.get());
		Assert.assertNotNull(GateSingleton.getFactory());
	}

	public void testService() throws GateException, GateLogicException
	{
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

		clientService.getClientById("0");
	}
}
