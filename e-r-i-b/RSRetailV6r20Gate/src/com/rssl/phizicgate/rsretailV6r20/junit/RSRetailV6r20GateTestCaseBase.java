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

public class RSRetailV6r20GateTestCaseBase extends GateTestCaseBase
{
	private static boolean initialized = false;

	protected RSRetailV6r20GateTestCaseBase()
	{
		super("");
	}

	protected RSRetailV6r20GateTestCaseBase(String name)
	{
		super(name);
	}

	protected void init() throws GateException
	{
		super.init();
		if (!initialized)
		{
			new RSRetailV6r20GateInitializer().configure();
			try
			{
				RetailJNIStartServiceImpl retail = new RetailJNIStartServiceImpl();
				//retail.start();
				//todo ENH009881  Модифицировать существующие тесты. 

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