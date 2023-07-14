package com.rssl.phizic.business.smsbanking.pseudonyms;

import com.rssl.phizic.business.BusinessException;

/**
 * @author hudyakov
 * @ created 15.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccessPseudonymException extends BusinessException
{
	/**
	 * конструктор по сообщению
	 * @param message
	 */
	public AccessPseudonymException(String message)
    {
        super(message);
    }

    public AccessPseudonymException(Throwable cause)
    {
        super(cause);
    }

	public AccessPseudonymException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
