package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.system.SystemLogEntry;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Roshka
 * @ created 27.03.2006
 * @ $Author$
 * @ $Revision$
 */
public class DownloadSystemLogTest extends BusinessTestCaseBase
{
	public void testDownloadSystemLog() throws BusinessException, DataAccessException
	{
		DownloadSystemLogOperation operation = new DownloadSystemLogOperation();

		Map<String, Object> parametrs = new HashMap<String, Object>();
		parametrs.put("surName", null);
		parametrs.put("firstName", null);
		parametrs.put("patrName", null);
		parametrs.put("messageWord", null);

		Query query = operation.createQuery("getLogEntries");
		query.setMaxResults(100);
		query.setParameters(parametrs);

		Iterator<SystemLogEntry> iterator = query.executeIterator();

		SystemLogEntry systemLogEntry;
		while(iterator.hasNext() )
		{
			systemLogEntry = iterator.next();
			assertNotNull( systemLogEntry );
		}
	}
}