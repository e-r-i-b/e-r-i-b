package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * Стратегия выполнения документа, проверка на мошейничество
 *
 * @author khudyakov
 * @ created 12.03.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringPreConfirmDocumentStrategy extends FraudMonitoringDocumentStrategyBase
{
	public FraudMonitoringPreConfirmDocumentStrategy(BusinessDocument document)
	{
		super(document);
	}

	public InteractionType getInteractionType()
	{
		return InteractionType.ASYNC;
	}

	public PhaseType getPhaseType()
	{
		return PhaseType.SENDING_REQUEST;
	}

	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		process();
	}
}
