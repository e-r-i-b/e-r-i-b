package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizic.gate.impl.GateFactoryImpl;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;

/**
 * @author Roshka
 * @ created 21.11.2006
 * @ $Author$
 * @ $Revision$
 */

@IncludeTest(configurations = "sbrf")
public abstract class SBRFGateTestCaseBase extends RSSLTestCaseBase
{
	protected GateFactoryImpl gateFactory = null;

	protected void setUp() throws Exception
	{
		super.setUp();

		gateFactory = new GateFactoryImpl();
		gateFactory.initialize();
	}
}