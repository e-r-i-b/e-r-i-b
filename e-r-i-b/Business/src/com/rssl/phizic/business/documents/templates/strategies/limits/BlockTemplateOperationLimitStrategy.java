package com.rssl.phizic.business.documents.templates.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.limits.CheckDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.exceptions.GateException;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Лимиты: стратегия проверяет превышение текущей суммы шаблона с размером заградительного лимита
 *
 * @author khudyakov
 * @ created 09.02.14
 * @ $Author$
 * @ $Revision$
 */
public class BlockTemplateOperationLimitStrategy implements CheckDocumentStrategy
{
	private static final LimitService limitService = new LimitService();

	private Limit limit;
	private TemplateDocument template;

	public BlockTemplateOperationLimitStrategy(TemplateDocument template) throws BusinessException
	{
		this.template = template;
		this.limit = getStrategyLimit();
	}

	public boolean check(ClientAccumulateLimitsInfo limitsInfo) throws BusinessException, BusinessLogicException
	{
		if (!FormType.isExternalDocument(template.getFormType()))
		{
			return true;
		}

		Money exactAmount = template.getExactAmount();
		if (limit == null || exactAmount == null)
		{
			return true;
		}

		Money convertedAmount = TemplateHelper.moneyConvert(exactAmount, limit.getAmount().getCurrency(), template);
		return (limit.getAmount().compareTo(convertedAmount) >= 0);
	}

	public boolean checkAndThrow(ClientAccumulateLimitsInfo limitsInfo) throws BusinessException, BusinessLogicException
	{
		if (check(limitsInfo))
		{
			return true;
		}
		throw new BlockDocumentOperationException(String.format(Constants.BLOCK_LIMIT_OPERATION_EXCEEDED_MESSAGE, "шаблон"), limit);
	}

	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		checkAndThrow(amountInfo);
	}

	protected Limit getStrategyLimit() throws BusinessException
	{
		try
		{
			List<Limit> temp = limitService.findLimits(template.getOffice(), LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATIONS, LimitHelper.getChannelType(template), null, Status.ACTIVE, null, null);
			if (CollectionUtils.isEmpty(temp))
			{
				return null;
			}
			if (temp.size() > 1)
			{
				throw new BusinessException("Количество активных лимитов не должно превышать 1.");
			}

			return temp.get(0);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public Limit getLimit()
	{
		return limit;
	}
}
