package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.common.types.Money;
import org.apache.commons.lang.StringUtils;

/**
 * Исключение, требующее отмены операции
 *
 * @author khudyakov
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ImpossiblePerformOperationException extends BusinessDocumentLimitException
{
	public ImpossiblePerformOperationException(Limit limit)
	{
		super(StringUtils.EMPTY, limit);
	}

	public ImpossiblePerformOperationException(Limit limit, Money amount)
	{
		super(StringUtils.EMPTY, limit, amount);
	}

	public ImpossiblePerformOperationException(Limit limit, Long count)
	{
		super(StringUtils.EMPTY, limit, count);
	}

	public ImpossiblePerformOperationException(String message, Limit limit, Money amount)
	{
		super(message, limit, amount);
	}

	public String getLogMessage(Long documentId)
	{
		return String.format(Constants.IMPOSSIBLE_PERFORM_OPERATION_ERROR_MESSAGE, documentId) + super.getLogMessage(documentId);
	}
}
