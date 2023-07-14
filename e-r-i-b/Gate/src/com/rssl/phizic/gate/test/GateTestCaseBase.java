package com.rssl.phizic.gate.test;

import junit.framework.TestCase;
import com.rssl.phizic.gate.impl.GateFactoryImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.test.JNDIUnitTestHelper;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import javax.naming.NamingException;

/**
 * @author Roshka
 * @ created 01.03.2007
 * @ $Author: bogdanov $
 * @ $Revision: 4205 $
 */

public abstract class GateTestCaseBase extends RSSLTestCaseBase
{
	protected GateFactoryImpl gateFactory = null;

	protected GateTestCaseBase()
	{
		super("");
	}

	protected GateTestCaseBase(String name)
	{
		super(name);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		init();

		gateFactory = new GateFactoryImpl();
		gateFactory.initialize();
	}

	protected void init() throws GateException
	{
		if (JNDIUnitTestHelper.notInitialized())
		{
			try
			{
				JNDIUnitTestHelper.init();
			}
			catch (NamingException ne)
			{
				ne.printStackTrace();
				fail("NamingException thrown on Init : " + ne.getMessage());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				fail("Exception thrown on Init : " + e.getMessage());
			}
		}
	}
}