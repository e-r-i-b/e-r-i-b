package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * Стратегия отправки сообщений во ФМ после обработки документа
 *
 * @author khudyakov
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringPostConfirmTemplateStrategy extends FraudMonitoringTemplateStrategyBase
{
	public FraudMonitoringPostConfirmTemplateStrategy(TemplateDocument template)
	{
		super(template);
	}

	public InteractionType getInteractionType()
	{
		return null;
	}

	public PhaseType getPhaseType()
	{
		return null;
	}

	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo)	{}
}
