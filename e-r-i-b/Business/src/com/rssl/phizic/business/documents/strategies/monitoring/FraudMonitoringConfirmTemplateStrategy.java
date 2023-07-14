package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * Стратегия выполнения шаблона документа, проверка на мошейничество
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringConfirmTemplateStrategy extends FraudMonitoringTemplateStrategyBase
{
	private ConfirmRequest confirmRequest;

	public FraudMonitoringConfirmTemplateStrategy(TemplateDocument template)
	{
		super(template);
		confirmRequest = ConfirmationManager.currentConfirmRequest(template);
	}

	public InteractionType getInteractionType()
	{
		if (confirmRequest == null)
		{
			return InteractionType.SYNC;
		}

		ConfirmStrategyType confirmStrategyType = confirmRequest.getStrategyType();
		if (confirmStrategyType == null)
		{
			return InteractionType.SYNC;
		}

		return confirmStrategyType == ConfirmStrategyType.none ? InteractionType.SYNC : InteractionType.ASYNC;
	}

	public PhaseType getPhaseType()
	{
		InteractionType interactionType = getInteractionType();
		if (InteractionType.SYNC == interactionType)
		{
			return PhaseType.CONTINUOUS_INTERACTION;
		}

		return PhaseType.WAITING_FOR_RESPONSE;
	}
}
