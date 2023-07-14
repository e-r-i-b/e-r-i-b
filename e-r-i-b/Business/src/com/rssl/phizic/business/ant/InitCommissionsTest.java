package com.rssl.phizic.business.ant;

import junit.framework.TestCase;

/**
 * @author Evgrafov
 * @ created 17.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */

public class InitCommissionsTest extends TestCase
{
	public void test(){}

	public void manualTest() throws Exception
	{
		InitCommissionsTask task = new InitCommissionsTask();

		task.init();
		task.execute();
	}
}