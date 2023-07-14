package com.rssl.phizic.operations.log;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;

import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 29.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class MessageLogOperationTest extends BusinessTestCaseBase
{
	public void testGetMessageLog() throws Exception
	{
		MessageLogOperation operation = new MessageLogOperation();

		Map<String, Object> parametrs = new HashMap<String, Object>();
		parametrs.put("requestWord", "");
		parametrs.put("responceWord", "");

		Query query = operation.createQuery("getLogEntries");
		query.setParameters(parametrs);
		query.setMaxResults(20);

		Iterator<Object> logentries = query.executeIterator();

		assertNotNull(logentries);

		for(int i=0;(i<10)&&(logentries.hasNext());i++)
		{
			MessagingLogEntry obj = (MessagingLogEntry)logentries.next();
			String responce = operation.getEntity().getMessageResponse();
		}
	}
}
