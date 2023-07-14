package com.rssl.phizic.auth;

import junit.framework.*;

/**
 * @author Kosyakov
 * @ created 16.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2121 $
 */
public class UserIdValueGeneratorTest extends TestCase
{
	public void testGenerator() {
		UserIdValueGenerator userIdValueGenerator;
		userIdValueGenerator=new ManualUserIdValueGenerator("test");
		String value=userIdValueGenerator.newUserId(5);
		assertEquals(value,"test");
		userIdValueGenerator=new UserIdValueGeneratorByTime();
		value=userIdValueGenerator.newUserId(100);
		assertNotNull(value);
	}
}
