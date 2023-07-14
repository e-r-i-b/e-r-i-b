package com.rssl.phizic.business.departments;

import com.rssl.phizic.business.BusinessException;

/**
 * Исключение бросаемое при остановке синхронизации клиента
 * при неудачной проверке подразделения
 *
 * @author sergunin
 * @ created 28.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentIsNotServicedException extends BusinessException
{

	public DepartmentIsNotServicedException(String message)
	{
		super(message);
	}

	public DepartmentIsNotServicedException(Throwable cause)
	{
		super(cause);
	}

	public DepartmentIsNotServicedException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
