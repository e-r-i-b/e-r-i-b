package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * Тест для загрузки истории изменения курса ЦБ
 * @author Gololobov
 * @ created 03.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CBRatesHistoryLoaderTaskTest extends BusinessTestCaseBase
{
	public void testManualCBRatesHistoryLoaderTest() throws Exception
	{
		CBRatesHistoryLoaderTask task = new CBRatesHistoryLoaderTask();
		task.safeExecute();
	}
}
