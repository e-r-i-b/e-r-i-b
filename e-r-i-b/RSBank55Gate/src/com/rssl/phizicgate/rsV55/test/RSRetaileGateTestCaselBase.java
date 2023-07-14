package com.rssl.phizicgate.rsV55.test;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.test.GateTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;
import com.rssl.phizicgate.junit.RsV55GateInitializer;
import com.rssl.phizgate.common.messaging.retail.jni.RetailJNIStartServiceImpl;

import javax.management.MBeanException;
/**
 * @author Kidyaev
 * @ created 03.10.2005
 * @ $Author: danilov $
 * @ $Revision: 3676 $
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
		//servive.start();
		//todo ENH009881  �������������� ������������ �����.
		if (!initialized)
		{
			new RsV55GateInitializer().configure();
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
