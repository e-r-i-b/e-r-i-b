package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.limits.Limit;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Balovtsev
 * @since 21.01.2015.
 */
public class OverallAmountPerDayDocumentLimitStrategy extends DocumentLimitStrategyBase
{
	public OverallAmountPerDayDocumentLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	void doCheck(List<Limit> limits, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		super.doCheck(limits, document, DocumentLimitManager.buildLimitAmountInfoByLogin(documentOwner.getLogin(), null));
	}

	@Override
	protected List<Limit> getStrategyLimits() throws BusinessException
	{
		List<Limit> activeLimits = limitService.findActiveOverallLimits();

		if (CollectionUtils.isEmpty(activeLimits))
		{
			return Collections.emptyList();
		}

		if (activeLimits.size() > 1)
		{
			throw new BusinessException("Количество активных лимитов не должно превышать 1.");
		}

		return activeLimits;
	}
}
