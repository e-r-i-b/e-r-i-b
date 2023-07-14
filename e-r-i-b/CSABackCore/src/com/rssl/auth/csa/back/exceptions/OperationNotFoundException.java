package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author krenev
 * @ created 27.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class OperationNotFoundException extends LogicException
{
	public OperationNotFoundException(String message)
	{
		super(message);
	}
	public OperationNotFoundException(String ouid, Class clazz)
	{
		this("Не найдена операция " + clazz.getName() + " с иденфтификатором " + ouid);
	}

}
