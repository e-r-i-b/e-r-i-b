package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Ислючение, возникающее при таймауте
 * @author niculichev
 * @ created 09.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class BusinessTimeOutException extends BusinessLogicException
{
	public BusinessTimeOutException(String message)
	{
		super(message);
	}

	public BusinessTimeOutException(Throwable cause)
    {
        super(cause);
    }
}
