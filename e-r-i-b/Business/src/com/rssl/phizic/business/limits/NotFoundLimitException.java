package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.limits.Constants;

/**
 * @author khudyakov
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class NotFoundLimitException extends BusinessLogicException
{
	public NotFoundLimitException(String message)
	{
		super(message);
	}

	public NotFoundLimitException(Throwable cause)
	{
		super(cause);
	}

	public NotFoundLimitException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NotFoundLimitException(LimitType type, BusinessDocument document)
	{
		super(String.format(Constants.NOT_FOUND_LIMIT_ERROR_MESSAGE, document.getId(), type.name()));
	}
}
