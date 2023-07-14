package com.rssl.phizicgate.manager.config;

import com.rssl.phizic.utils.test.RSSLTestCaseBase;


/**
 * @author Krenev
 * @ created 20.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class GatesConfigTest extends RSSLTestCaseBase
{
	public void testGatesConfig() throws Exception
	{
		GatesConfig gatesConfig = new GatesConfig();
		/*Set<Class<? extends ExternalSystem>> systems = gatesConfig.getExternalSystems();
		assertNotNull(systems);
		assertTrue(systems.size() > 0);
		for (Class<? extends ExternalSystem> system : systems)
		{
			GateDescription gateDescription = gatesConfig.getGateDescription(system.newInstance());
			assertNotNull(gateDescription);
		}*/
	}
}
