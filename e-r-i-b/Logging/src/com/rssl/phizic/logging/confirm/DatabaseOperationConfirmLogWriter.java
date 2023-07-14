package com.rssl.phizic.logging.confirm;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author lukina
 * @ created 09.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class DatabaseOperationConfirmLogWriter extends OperationConfirmLogWriterBase
{
	private static OperationConfirmLogService service = new OperationConfirmLogService();

	protected void doSave(OperationConfirmLogEntry entry) throws Exception
	{
		service.add(entry);
	}
}
