package com.rssl.phizic.auth.pin;

import com.rssl.phizic.security.test.SecurityTestBase;

/**
 * @author Roshka
 * @ created 24.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class PINValueGeneratorTest extends SecurityTestBase
{
	public void test()
	{
		String userId = new PINValueGenerator().newUserId(8);
		assertNotNull( userId );
	}
}