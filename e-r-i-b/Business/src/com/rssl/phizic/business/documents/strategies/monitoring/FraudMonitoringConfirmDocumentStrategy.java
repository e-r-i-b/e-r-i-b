package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ConvertibleToGateDocumentAdapter;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.fraudMonitoring.exceptions.RequireAdditionConfirmFraudException;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.AccountOpeningClaim;
import com.rssl.phizic.gate.claims.IMAOpeningClaim;
import com.rssl.phizic.gate.claims.cards.PreApprovedLoanCardClaim;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.payments.AccountToIMATransfer;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOtherBank;
import com.rssl.phizic.gate.payments.longoffer.CreateCardToAccountLongOffer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Стратегия выполнения документа, проверка на мошейничество
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringConfirmDocumentStrategy extends FraudMonitoringDocumentStrategyBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	//список документов с получением коммиссии из ЦОД на стадии подтверждения. Отправляем по ним запрос в RSA, только если комиссия ещё не была получена
	private static final List<Class<? extends GateDocument>> documentsWithCommissionReceivingCheck = new ArrayList<Class<? extends GateDocument>>();
	private static final List<Class<? extends GateDocument>> disableDocuments = new ArrayList<Class<? extends GateDocument>>();

	static
	{
		disableDocuments.add(PreApprovedLoanCardClaim.class);
		disableDocuments.add(CreateCardToAccountLongOffer.class);

		documentsWithCommissionReceivingCheck.add(ClientAccountsTransfer.class);
		documentsWithCommissionReceivingCheck.add(AccountToIMATransfer.class);
		documentsWithCommissionReceivingCheck.add(ExternalCardsTransferToOtherBank.class);
		documentsWithCommissionReceivingCheck.add(AccountOpeningClaim.class);
		documentsWithCommissionReceivingCheck.add(IMAOpeningClaim.class);
		documentsWithCommissionReceivingCheck.add(AccountToCardTransfer.class);
	}

	private ConfirmRequest confirmRequest;

	public FraudMonitoringConfirmDocumentStrategy(BusinessDocument document)
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
		try
		{
			if (documentsWithCommissionReceivingCheck.contains(getDocument().getType()))
			{
				GateDocument gateDocument = new ConvertibleToGateDocumentAdapter(getDocument()).asGateDocument();
				if (GateSingleton.getFactory().service(BackRefCommissionTBSettingService.class).isCalcCommissionSupport(gateDocument)
					&& CollectionUtils.isNotEmpty(gateDocument.getWriteDownOperations()))
				{
					//если комиссия получена - значит запрос по операции уже был отправлен. Ничего не делаем
					return;
				}
			}

			if (disableDocuments.contains(getDocument().getType()))
			{
				return;
			}

			process();
		}
		catch (ProhibitionOperationFraudException e)
		{
			throw e;
		}
		catch (RequireAdditionConfirmFraudException e)
		{
			throw e;
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error("Не удалось отправить запрос во фрод-мониторинг во время подтверждения операции", e);
		}
	}
}
