package ru.softlab.phizicgate.rsloansV64.junit;

import com.rssl.phizic.gate.test.GateTestCaseBase;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Omeliyanchuk
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class RSLoans64GateTestCaseBase extends GateTestCaseBase
{
	private static boolean initialized = false;

	protected RSLoans64GateTestCaseBase()
	{
		super("");
	}

	protected RSLoans64GateTestCaseBase(String name)
	{
		super(name);
	}

	protected void init() throws GateException
	{
		super.init();

		if (!initialized)
		{
			new RSLoans64GateInitializer().configure();
			initialized = true;
		}
	}
}
