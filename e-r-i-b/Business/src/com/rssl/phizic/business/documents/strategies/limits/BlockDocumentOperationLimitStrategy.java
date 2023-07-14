package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.BlockDocumentOperationException;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Стратегия учета заградительного лимита разовой операции.
 * (сумма разовой операции не должна превышать сумму заградительного лимита банка)
 *
 * @author basharin
 * @ created 27.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class BlockDocumentOperationLimitStrategy extends DocumentLimitStrategyBase
{
	public BlockDocumentOperationLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		if (!check(amountInfo))
		{
			String message = String.format(Constants.BLOCK_LIMIT_OPERATION_EXCEEDED_MESSAGE, getDocument().isLongOffer() ? "автоплатеж" : "платеж");
			throw new BlockDocumentOperationException(message, getCurrentLimit());
		}
	}

	@Override
	protected List<Limit> getStrategyLimits() throws BusinessException
	{
		List<Limit> temp = limitService.findActiveLimits(getDocument(), LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATIONS, LimitHelper.getChannelType(getDocument()));
		if (CollectionUtils.isEmpty(temp))
		{
			return Collections.emptyList();
		}

		List<Limit> limits = new ArrayList<Limit>();
		for (Limit limit : temp)
		{
			// создаем копию, чтоб хибернейт не обновил в базе как изменившийся объект в контексте
			limits.add(new Limit(limit, LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATION));
		}

		return limits;
	}
}

