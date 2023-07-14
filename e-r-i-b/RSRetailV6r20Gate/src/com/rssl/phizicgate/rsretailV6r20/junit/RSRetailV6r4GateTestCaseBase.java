package com.rssl.phizicgate.rsretailV6r20.junit;

import com.rssl.phizic.gate.test.GateTestCaseBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizgate.common.messaging.retail.jni.RetailJNIStartServiceImpl;

import javax.management.MBeanException;

/**
 * @author Omeliyanchuk
 * @ created 20.09.2007
 * @ $Author$
 * @ $Revision$
 */

public class RSRetailV6r4GateTestCaseBase extends GateTestCaseBase
{
	private static boolean initialized = false;

	protected RSRetailV6r4GateTestCaseBase()
	{
		super("");
	}

	protected RSRetailV6r4GateTestCaseBase(String name)
	{
		super(name);
	}

	protected void init() throws GateException
	{
		super.init();
		if (!initialized)
		{
			new RSRetailV6r4GateInitializer().configure();
			try
			{
				RetailJNIStartServiceImpl retail = new RetailJNIStartServiceImpl();
				retail.start();
			}
			catch(MBeanException ex)
			{
				throw new GateException(ex);
			}
			initialized = true;
		}
	}

	protected void setUp() throws Exception
	{
		super.setUp();
	}

	protected void tearDown() throws Exception
	{

		super.tearDown();
	}
}