package com.rssl.phizic.business.departments;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 03.10.2006
 * Time: 16:02:01
 * To change this template use File | Settings | File Templates.
 */
public class DublicateDepartmentException extends BusinessLogicException
{
	public DublicateDepartmentException(String message)
    {
        super(message);
    }

    public DublicateDepartmentException(Throwable cause)
    {
        super(cause);
    }

	public DublicateDepartmentException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
