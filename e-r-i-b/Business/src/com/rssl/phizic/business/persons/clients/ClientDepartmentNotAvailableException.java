package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.business.BusinessException;

/**
 * @author egorova
 * @ created 14.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ClientDepartmentNotAvailableException extends BusinessException
{
    public ClientDepartmentNotAvailableException(String message)
    {
        super(message);
    }

    public ClientDepartmentNotAvailableException(Throwable cause)
    {
        super(cause);
    }

	public ClientDepartmentNotAvailableException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
