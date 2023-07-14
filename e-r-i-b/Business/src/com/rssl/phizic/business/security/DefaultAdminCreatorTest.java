package com.rssl.phizic.business.security;

import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Kidyaev
 * @ created 12.01.2006
 * @ $Author$
 * @ $Revision$
 */
public class DefaultAdminCreatorTest extends BusinessTestCaseBase
{
	public void manualTestDefaultAdmin() throws Exception
	{
		new DefaultAdminCreator().create();
	}

	public void test()
	{
	}
}
