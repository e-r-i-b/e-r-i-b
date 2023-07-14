package com.rssl.phizic.business.ermb.migration.list.task.migration;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * Ошибка сценария миграции
 * @author Puzikov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

class MigrationLogicException extends IKFLException
{
	/**
	 * ctor
	 * @param s message
	 */
	MigrationLogicException(String s)
	{
		super(s);
	}
}
