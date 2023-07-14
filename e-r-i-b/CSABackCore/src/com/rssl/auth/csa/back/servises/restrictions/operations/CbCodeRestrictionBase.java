package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.DenyCbCodeOperationException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.Operation;

import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * Базовый класс ограничений, в разрезе ТБ
 */

public abstract class CbCodeRestrictionBase<T extends Operation> implements OperationRestriction<T>
{
	public final void check(T operation) throws RestrictionException
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		Pattern denyPattern = getTBDenyPattern();
		if (denyPattern == null)
		{
			return;
		}

		if (denyPattern.matcher(operation.getCbCode()).matches())
		{
			throw new DenyCbCodeOperationException(getRestrictionViolatedMessage());
		}
	}

	/**
	 * @return сообщение о сработанном исключении.
	 */
	public abstract String getRestrictionViolatedMessage();

	/**
	 * @return регулярное выражение запрещенных ТБ. null, если запрета нет
	 */
	public abstract Pattern getTBDenyPattern();
}
