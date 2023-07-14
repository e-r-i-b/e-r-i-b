package com.rssl.phizic.business.documents.strategies.limits.process;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.RequireAdditionConfirmLimitException;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.common.types.Money;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Процессор, проверяющий попадание суммы документа в лимит, бросает RequireAdditionConfirmException
 *
 * @author khudyakov
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 */
public class RequireAdditionConfirmLimitProcessor extends LimitOperationProcessorBase
{
	private static final Map<RestrictionType, LimitProcessor> processors = new HashMap<RestrictionType, LimitProcessor>(3);
	static
	{
		processors.put(RestrictionType.OPERATION_COUNT_IN_DAY,      new CountOperationProcessor());
		processors.put(RestrictionType.OPERATION_COUNT_IN_HOUR,     new CountOperationProcessor());
		processors.put(RestrictionType.AMOUNT_IN_DAY,               new InDayAmountOperationProcessor());
	}

	@Override
	Map<RestrictionType, LimitProcessor> getProcessors()
	{
		return Collections.unmodifiableMap(processors);
	}


	private static class InDayAmountOperationProcessor extends LimitProcessorBase
	{
		public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
		{
			Money accumulateAmount = amountInfo.getAccumulateAmount(limit);
			if (limit.getAmount().compareTo(LimitHelper.getOperationAmount(document).add(accumulateAmount)) < 0)
			{
				throw new RequireAdditionConfirmLimitException(limit.getRestrictionType(), limit, accumulateAmount);
			}
		}
	}

	private static class CountOperationProcessor extends LimitProcessorBase
	{
		public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
		{
			//подсчитываем количество операций в разрезе лимита за последние сутки + текущая операция
			Long accumulateCount = amountInfo.getAccumulateCount(limit);
			if (limit.getOperationCount().compareTo(accumulateCount + 1L) < 0)
			{
				throw new RequireAdditionConfirmLimitException(limit.getRestrictionType(), limit, accumulateCount);
			}
		}
	}
}
