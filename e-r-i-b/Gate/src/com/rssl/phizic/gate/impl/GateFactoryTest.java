package com.rssl.phizic.gate.impl;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import com.rssl.phizic.utils.test.annotation.ClassTest;

import java.io.IOException;

/**
 * @author Evgrafov
 * @ created 20.07.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

@ClassTest
public class GateFactoryTest extends RSSLTestCaseBase
{
	public void test() throws IOException, GateException
	{
		ConfigFactory.getConfig(GateConfig.class).buildGate();
	}
}