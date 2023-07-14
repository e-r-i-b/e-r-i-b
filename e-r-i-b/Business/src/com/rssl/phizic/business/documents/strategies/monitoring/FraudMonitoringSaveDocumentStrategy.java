package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.gate.claims.cards.PreApprovedLoanCardClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.longoffer.CreateCardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EditCardToAccountLongOffer;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

import java.util.ArrayList;
import java.util.List;

/**
 * Стратегия выполнения документа, проверка на мошейничество (сохранение документа)
 *
 * @author khudyakov
 * @ created 26.02.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringSaveDocumentStrategy extends FraudMonitoringDocumentStrategyBase
{
	private static final List<Class<? extends GateDocument>> acceptableDocuments = new ArrayList<Class<? extends GateDocument>>();
	static
	{
		acceptableDocuments.add(PreApprovedLoanCardClaim.class);
		acceptableDocuments.add(CreateCardToAccountLongOffer.class);
		acceptableDocuments.add(EditCardToAccountLongOffer.class);
		acceptableDocuments.add(LoanCardClaim.class);
	}

	private ConfirmRequest confirmRequest;

	public FraudMonitoringSaveDocumentStrategy(BusinessDocument document)
	{
		super(document);
		confirmRequest = ConfirmationManager.currentConfirmRequest(getDocument());
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

	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		if (acceptableDocuments.contains(getDocument().getType()))
		{
			process();
		}
	}
}
