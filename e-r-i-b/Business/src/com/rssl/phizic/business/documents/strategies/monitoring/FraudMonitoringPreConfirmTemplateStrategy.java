package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * Стратегия выполнения шаблона документа, проверка на мошейничество
 *
 * @author khudyakov
 * @ created 22.05.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringPreConfirmTemplateStrategy extends FraudMonitoringTemplateStrategyBase
{
	public FraudMonitoringPreConfirmTemplateStrategy(TemplateDocument template)
	{
		super(template);
	}

	public InteractionType getInteractionType()
	{
		return InteractionType.ASYNC;
	}

	public PhaseType getPhaseType()
	{
		return PhaseType.SENDING_REQUEST;
	}
}
