package com.rssl.phizicgate.rsretailV6r4.junit;

import com.rssl.phizic.gate.test.GateTestCaseBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizgate.common.messaging.retail.jni.RetailJNIStartServiceImpl;
import com.rssl.phizgate.common.messaging.retail.jni.jndi.RetailJNIHelper;

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
				new RetailJNIStartServiceImpl().start();
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
		RetailJNIHelper.lookupPoolFactory();
	}

	protected void tearDown() throws Exception
	{
		if(initialized)
		{
			RetailJNIHelper.lookupPoolFactory().close();
		}

		super.tearDown();
	}
}