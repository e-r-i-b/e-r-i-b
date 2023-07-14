package com.rssl.phizic.business.persons.clients;

/**
 * @author gololobov
 * @ created 15.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ClientDepartmentNotSupportedException extends ClientDepartmentNotAvailableException
{
	public ClientDepartmentNotSupportedException(String message)
	{
		super(message);
	}

	public ClientDepartmentNotSupportedException(Throwable cause)
	{
		super(cause);
	}

	public ClientDepartmentNotSupportedException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
