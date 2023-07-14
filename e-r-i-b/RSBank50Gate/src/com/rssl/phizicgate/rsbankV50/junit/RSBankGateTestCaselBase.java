package com.rssl.phizicgate.rsbankV50.junit;

import com.rssl.phizic.utils.test.annotation.IncludeTest;
import com.rssl.phizic.gate.test.GateTestCaseBase;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kidyaev
 * @ created 03.10.2005
 * @ $Author: Kidyaev $
 * @ $Revision$
 */

@IncludeTest(configurations = "russlav")
public abstract class RSBankGateTestCaselBase extends GateTestCaseBase
{
	private static boolean initialized = false;

	protected RSBankGateTestCaselBase()
	{
		super("");
	}

	protected RSBankGateTestCaselBase(String name)
	{
		super(name);
	}

	protected void init() throws GateException
	{
		super.init();
		if (!initialized)
		{
			new RSBankGateInitializer().configure();
			initialized = true;
		}
	}
}