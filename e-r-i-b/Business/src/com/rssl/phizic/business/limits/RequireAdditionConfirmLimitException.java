package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.common.types.Money;
import org.apache.commons.lang.StringUtils;

/**
 * @author niculichev
 * @ created 09.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class RequireAdditionConfirmLimitException extends BusinessDocumentLimitException
{
	private RestrictionType restrictionType;

	public RequireAdditionConfirmLimitException(RestrictionType restrictionType, Limit limit, Long count)
	{
		super(StringUtils.EMPTY, limit, count);
		this.restrictionType = restrictionType;
	}

	public RequireAdditionConfirmLimitException(RestrictionType restrictionType, Limit limit, Money amount)
	{
		super(StringUtils.EMPTY, limit, amount);
		this.restrictionType = restrictionType;
	}

	public RequireAdditionConfirmLimitException(String message, RestrictionType restrictionType, Limit limit)
	{
		super(message, limit);
		this.restrictionType = restrictionType;
	}

	public RestrictionType getRestrictionType()
	{
		return restrictionType;
	}

	public String getLogMessage(Long documentId)
	{
		return String.format(Constants.WAIT_ADDITIONAL_CONFIRM_BY_REASON_ERROR_MESSAGE, documentId, getRestrictionType()) + super.getLogMessage(documentId);
	}
}
