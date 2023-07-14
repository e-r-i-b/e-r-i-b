package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.common.types.Money;
import org.apache.commons.lang.StringUtils;

/**
 * Исключение, требующее дополнительную проверку (например: перечитывание sim-карты)
 *
 * @author khudyakov
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class RequireAdditionCheckException extends BusinessDocumentLimitException
{
	public RequireAdditionCheckException(Limit limit)
	{
		super(StringUtils.EMPTY, limit);
	}

	public RequireAdditionCheckException(Limit limit, Money amount)
	{
		super(StringUtils.EMPTY, limit, amount);
	}

	public String getLogMessage(Long documentId)
	{
		return String.format(Constants.REQUIRE_ADDITIONAL_CHECK_ERROR_MESSAGE, documentId) + super.getLogMessage(documentId);
	}
}
