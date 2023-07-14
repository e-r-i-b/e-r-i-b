package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.exceptions.GateException;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author Balovtsev
 * @since 22.01.2015.
 */
public class OverallAmountPerDayTemplateLimitStrategy implements CheckDocumentStrategy
{
	protected static final String OVERALL_AMOUNT_PER_PAY_TEMPLATE_ERROR = "Вы не можете содать шаблон на сумму большую суточного лимита. Пожалуйста, измените сумму и повторите операцию.";

	private static final LimitService limitService = new LimitService();

	private Limit            limit;
	private TemplateDocument template;

	public OverallAmountPerDayTemplateLimitStrategy(TemplateDocument template) throws BusinessException
	{
		this.template = template;

		if (needUse(template))
		{
			this.limit = getStrategyLimit();
		}
	}

	private boolean needUse(TemplateDocument template)
	{
		return !FormType.isInternalDocument(template.getFormType());
	}

	public boolean check(ClientAccumulateLimitsInfo limitsInfo) throws BusinessException, BusinessLogicException
	{
		if (FormType.isInternalDocument(template.getFormType()))
		{
			return true;
		}

		// Создиние шаблонов без указанной суммы разрешается
		if (limit == null || template.getExactAmount() == null)
		{
			return true;
		}

		// Сумма указанная в шаблоне не может превышать суточный кумулятивный лимит
		return limit.getAmount().compareTo(LimitHelper.getOperationAmount(template)) >= 0;
	}

	public boolean checkAndThrow(ClientAccumulateLimitsInfo limitsInfo) throws BusinessException, BusinessLogicException
	{
		if (check(limitsInfo))
		{
			return true;
		}

		throw new ImpossiblePerformOperationException(OVERALL_AMOUNT_PER_PAY_TEMPLATE_ERROR, limit, null);
	}

	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		checkAndThrow(amountInfo);
	}

	public Limit getStrategyLimit() throws BusinessException
	{
        List<Limit> limits = limitService.findActiveOverallLimits();

        if (CollectionUtils.isEmpty(limits))
        {
            return null;
        }

        if (limits.size() > 1)
        {
            throw new BusinessException("Количество активных лимитов не должно превышать 1.");
        }

        return limits.get(0);
	}
}
