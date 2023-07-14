package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author mihaylov
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Бизнесовое исключение о необходимости выполнения документы в офлайн режиме
 */
public class BusinessOfflineDocumentException extends BusinessLogicException
{
	public BusinessOfflineDocumentException(Throwable cause)
	{
		super(cause);
	}

	public BusinessOfflineDocumentException(String message)
    {
        super(message);
    }
}
