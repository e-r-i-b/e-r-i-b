package com.rssl.phizic.business.ant;

import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Omeliyanchuk
 * @ created 13.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class DictionariesLoaderTaskTest extends BusinessTestCaseBase
{
	public void manualDictionaryTest() throws Exception
	{
		DictionariesLoaderTask task = new DictionariesLoaderTask();
		task.safeExecute();
	}
}
