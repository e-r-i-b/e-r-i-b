package com.rssl.phizic.operations.log;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.utils.DateHelper;

import java.util.*;

/**
 * @author Roshka
 * @ created 15.03.2006
 * @ $Author$
 * @ $Revision$
 */

public class DownloadUserLogTest extends BusinessTestCaseBase
{
	public void testDownloadUserLog() throws BusinessException, DataAccessException
	{
		DownloadUserLogOperation operation = new DownloadUserLogOperation();
		Query query = operation.createQuery("getLogEntries");
//		query.setParameter("fromDate", null);
//		query.setParameter("toDate", new Date());
//		query.executeList();
		Calendar calendarFrom = DateHelper.getPreviousMonth();
		Calendar calendarTo = DateHelper.getCurrentDate();
		query.setParameter("fromDate", calendarFrom);
		query.setParameter("toDate", calendarTo);
		Iterator<Object> iterator = query.executeIterator();
		assertNotNull(iterator);

		while (iterator.hasNext())
		{
			Object o = iterator.next();
			assertNotNull(o);
		}

//		assertFalse(items.isEmpty());
	}
}
