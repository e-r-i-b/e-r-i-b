package com.rssl.phizic.business.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;

/**
 * @author Kidyaev
 * @ created 22.12.2006
 * @ $Author$
 * @ $Revision$
 */
@ExcludeTest(configurations="sbrf")
public class RefusingReasonDictionaryTest extends BusinessTestCaseBase
{
	public void testLoadDictionary() throws BusinessException
	{
		RefusingReasonDictionary dictionary = new RefusingReasonDictionary();
		assertTrue(dictionary.getRefusingReasons().size() > 0);
	}
}
