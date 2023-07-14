package com.rssl.phizic.business.documents.strategies.limits.process;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ѕроцессор, провер€ющий попадание суммы документа в лимит, бросает ImpossiblePerformOperationException
 *
 * @author khudyakov
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 */
public class ImpossiblePerformLimitProcessor extends LimitOperationProcessorBase
{
	protected static final String OVERALL_AMOUNT_PER_PAY_DOCUMENT_ERROR = "¬ы превысили суточный лимит денежных средств по операци€м. ѕожалуйста повторите попытку позже.";

	private static final Map<RestrictionType, LimitProcessor> processors = new HashMap<RestrictionType, LimitProcessor>(6);
	static
	{
		processors.put(RestrictionType.MIN_AMOUNT,                  new MinAmountOperationProcessor());
		processors.put(RestrictionType.OPERATION_COUNT_IN_DAY,      new CountOperationProcessor());
		processors.put(RestrictionType.OPERATION_COUNT_IN_HOUR,     new CountOperationProcessor());
		processors.put(RestrictionType.AMOUNT_IN_DAY,               new InDayAmountOperationProcessor());
		processors.put(RestrictionType.OVERALL_AMOUNT_PER_DAY,      new OverallLimitAmountPerDay());
		processors.put(RestrictionType.PHONE_ALL_AMOUNT_IN_DAY,     new ExternalProviderAmountOperationProcessor());
		processors.put(RestrictionType.CARD_ALL_AMOUNT_IN_DAY,      new ExternalProviderAmountOperationProcessor());
	}

	@Override
	Map<RestrictionType, LimitProcessor> getProcessors()
	{
		return Collections.unmodifiableMap(processors);
	}

	private static class MinAmountOperationProcessor extends LimitProcessorBase
	{
		public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
		{
			Money operationAmount = LimitHelper.getOperationAmount(document);
			if (operationAmount.getDecimal().compareTo(BigDecimal.ZERO) != 0 && limit.getAmount().compareTo(operationAmount) > 0)
			{
				throw new ImpossiblePerformOperationException(limit);
			}
		}
	}

	private static class InDayAmountOperationProcessor extends LimitProcessorBase
	{
		public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
		{
			Money accumulateAmount = amountInfo.getAccumulateAmount(limit);
			if (limit.getAmount().compareTo(LimitHelper.getOperationAmount(document).add(accumulateAmount)) < 0)
			{
				throw new ImpossiblePerformOperationException(limit, accumulateAmount);
			}
		}
	}

	private static class CountOperationProcessor extends LimitProcessorBase
	{
		public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
		{
			//подсчитываем количество операций в разрезе лимита за последние сутки + текуща€ операци€
			Long accumulateCount = amountInfo.getAccumulateCount(limit);
			if (limit.getOperationCount().compareTo(accumulateCount + 1L) < 0)
			{
				throw new ImpossiblePerformOperationException(limit, accumulateCount);
			}
		}
	}

	private static class ExternalProviderAmountOperationProcessor extends LimitProcessorBase
	{
		public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
		{
			Money accumulateAmount = getExternalProviderAccumulatedAmount(limit, document);
			if (limit.getAmount().compareTo(LimitHelper.getOperationAmount(document).add(accumulateAmount)) < 0)
			{
				throw new ImpossiblePerformOperationException(limit, accumulateAmount);
			}
		}
	}

	private static class OverallLimitAmountPerDay extends LimitProcessorBase
	{
		public void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
		{
			Money accumulatedAmount = amountInfo.getOverallAccumulateAmount(limit);

			if (limit.getAmount().compareTo(LimitHelper.getOperationAmount(document).add(accumulatedAmount)) < 0)
			{
				throw new ImpossiblePerformOperationException(OVERALL_AMOUNT_PER_PAY_DOCUMENT_ERROR, limit, accumulatedAmount);
			}
		}
	}
}
