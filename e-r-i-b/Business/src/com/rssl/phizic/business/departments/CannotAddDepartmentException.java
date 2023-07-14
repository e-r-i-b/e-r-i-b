package com.rssl.phizic.business.departments;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author eMakarov
 * @ created 16.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class CannotAddDepartmentException extends BusinessLogicException
{
	public CannotAddDepartmentException(String message)
    {
        super(message);
    }

    public CannotAddDepartmentException(Throwable cause)
    {
        super(cause);
    }

	public CannotAddDepartmentException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
