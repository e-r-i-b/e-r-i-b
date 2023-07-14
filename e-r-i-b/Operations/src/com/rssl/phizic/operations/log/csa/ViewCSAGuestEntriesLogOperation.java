package com.rssl.phizic.operations.log.csa;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.log.MessageLogOperation;


/**
 * @author tisov
 * @ created 23.07.15
 * @ $Author$
 * @ $Revision$
 * Операция просмотра списка записей о гостевых входах
 */
public class ViewCSAGuestEntriesLogOperation extends MessageLogOperation
{
	protected String getInstanceName()
	{
		return Constants.CSA_DB_LOG_INSTANCE_NAME;
	}
}
