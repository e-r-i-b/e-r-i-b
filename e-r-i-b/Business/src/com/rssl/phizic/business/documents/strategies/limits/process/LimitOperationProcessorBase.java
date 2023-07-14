package com.rssl.phizic.business.documents.strategies.limits.process;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.RestrictionType;

import java.util.Map;

/**
 * Процессор, проверяющий попадание суммы документа в лимит
 *
 * @author khudyakov
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class LimitOperationProcessorBase extends LimitProcessorBase
{
	abstract Map<RestrictionType, LimitProcessor> getProcessors();

	public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		LimitProcessor processor = getProcessors().get(limit.getRestrictionType());
		if (processor == null)
		{
			throw new IllegalArgumentException(String.format(Constants.WRONG_RESTRICTION_LIMIT_TYPE_ERROR_MESSAGE, limit.getRestrictionType(), limit.getId()));
		}

		processor.process(limit, document, amountInfo);
	}
}
