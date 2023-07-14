package com.rssl.phizicgate.rsV51.test;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.test.GateTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;
import com.rssl.phizicgate.junit.RsV51GateInitializer;
import com.rssl.phizgate.common.messaging.retail.jni.RetailJNIStartServiceImpl;

import javax.management.MBeanException;
/**
 * @author Kidyaev
 * @ created 03.10.2005
 * @ $Author: bogdanov $
 * @ $Revision: 13400 $
 */

@IncludeTest(configurations = "russlav")
public abstract class RSRetaileGateTestCaselBase extends GateTestCaseBase
{
	private static boolean initialized = false;

	protected RSRetaileGateTestCaselBase()
	{
		super("");
	}

	protected RSRetaileGateTestCaselBase(String name)
	{
		super(name);
	}

	protected void init() throws GateException
	{
		super.init();

		RetailJNIStartServiceImpl servive = null;
		try
		{
			servive = new RetailJNIStartServiceImpl();
		}
		catch (MBeanException e)
		{
			throw new RuntimeException(e);
		}
		servive.start();

		if (!initialized)
		{
			new RsV51GateInitializer().configure();
			initialized = true;
		}
	}
	protected void tearDown() throws Exception
	{
		RetailJNIStartServiceImpl servive = new RetailJNIStartServiceImpl();
		servive.stop();
		super.tearDown();
	}
}
