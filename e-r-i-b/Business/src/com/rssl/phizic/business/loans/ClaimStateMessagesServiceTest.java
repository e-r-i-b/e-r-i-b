package com.rssl.phizic.business.loans;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author gladishev
 * @ created 18.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ClaimStateMessagesServiceTest extends BusinessTestCaseBase
{
	public void test() throws Exception
	{
		String message = ConfigFactory.getConfig(ClaimStateMessagesService.class).getMessage((long) 1);
		assertNotNull(message);
		System.out.println("--> " + message);
	}
}
