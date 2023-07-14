package com.rssl.phizic.operations.security;

import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Roshka
 * @ created 27.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class ShowAvailablePINsTaskTest extends BusinessTestCaseBase
{
	public void testShowAvailablePINsTask() throws Exception
	{
		ShowAvailablePINsTask piNsTask = new ShowAvailablePINsTask();

		piNsTask.safeExecute();
	}
}
