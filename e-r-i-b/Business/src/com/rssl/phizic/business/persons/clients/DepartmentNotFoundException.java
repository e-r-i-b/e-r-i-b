package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.business.BusinessException;

/**
 * @author akrenev
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 * �� ������ �����������, � ������� ������������� ������.
 */
public class DepartmentNotFoundException extends BusinessException
{
	public DepartmentNotFoundException(String message)
	{
		super(message);
	}

	public DepartmentNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public DepartmentNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
