package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.HandlerFilter;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.gate.claims.*;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.longoffer.*;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;

import java.util.HashSet;
import java.util.Set;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * кодишен платежи, поддерживаемые шиной.
 */
public class ESBERIBPaymentsCondition implements StateObjectCondition
{
	//кондишен на проверку является ли биллинговый платеж с карты шинным
	private static final StateObjectCondition esbCardBillingPaymentCondition = new ESBBillingPaymentCondition();
	//фильтр на поддерживаемость БП офисом, в который отправляется платеж
	private static final HandlerFilter documentOfficeESBSupportedFilter = new DocumentOfficeESBSupportedFilter();
	//множество поддерживаемых шиной платажей
	private static final Set<Class<? extends GateDocument>> esbDocuments = new HashSet<Class<? extends GateDocument>>();

	static
	{
		esbDocuments.add(ClientAccountsTransfer.class);
		esbDocuments.add(ClientAccountsLongOffer.class);
		esbDocuments.add(AccountToCardTransfer.class);
		esbDocuments.add(CardToAccountTransfer.class);
		esbDocuments.add(CardToAccountLongOffer.class);
		esbDocuments.add(CardJurIntraBankTransfer.class);
		esbDocuments.add(CardJurIntraBankTransferLongOffer.class);
		esbDocuments.add(CardJurTransfer.class);
		esbDocuments.add(CardJurTransferLongOffer.class);
		esbDocuments.add(CardIntraBankPayment.class);
		esbDocuments.add(CardIntraBankPaymentLongOffer.class);
		esbDocuments.add(CardRUSPayment.class);
		esbDocuments.add(CardRUSPaymentLongOffer.class);
		esbDocuments.add(LoanTransfer.class);
		esbDocuments.add(LoanTransferLongOffer.class);
		esbDocuments.add(RefuseLongOffer.class);
		esbDocuments.add(AccountIntraBankPaymentLongOffer.class);
		esbDocuments.add(AccountRUSPaymentLongOffer.class);
		esbDocuments.add(DepositorFormClaim.class);
		esbDocuments.add(RecallDepositaryClaim.class);
		esbDocuments.add(AccountOpeningClaim.class);
		esbDocuments.add(AccountOpeningFromCardClaim.class);
		esbDocuments.add(IMAOpeningClaim.class);
		esbDocuments.add(IMAOpeningFromCardClaim.class);
		esbDocuments.add(DemandAccountOpeningClaim.class);
		esbDocuments.add(CardRUSTaxPayment.class);
		esbDocuments.add(IMAToAccountTransfer.class);
		esbDocuments.add(AccountToIMATransfer.class);
		esbDocuments.add(IMAToCardTransfer.class);
		esbDocuments.add(CardToIMATransfer.class);
		//Заявка на утерю сберкнижки
		esbDocuments.add(LossPassbookApplicationClaim.class);
		esbDocuments.add(AccountClosingPaymentToCard.class);
		esbDocuments.add(AccountChangeMinBalanceClaim.class);
		esbDocuments.add(AccountChangeInterestDestinationClaim.class);
		esbDocuments.add(WithdrawDocument.class);
	}

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof ConvertableToGateDocument))
		{
			return false;
		}
		//1. проверяем поддерживает ли БП офис, в который оптравляется документ.
		try
		{
			if (!documentOfficeESBSupportedFilter.isEnabled(stateObject))
			{
				//не поддерживает - платеж не может идти в шину по определению.
				return false;
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}

		//2. проверяем тип платежа
		Class<? extends GateDocument> type = ((ConvertableToGateDocument) stateObject).asGateDocument().getType();
		//отдельно обрабатываем биллинги с карт
		if (CardPaymentSystemPayment.class.isAssignableFrom(type))
		{
			//биллинговые платежи с карты являются шинными только, если поставщик не iqw
			return esbCardBillingPaymentCondition.accepted(stateObject, stateMachineEvent);
		}
		if(WithdrawDocument.class.isAssignableFrom(type))
		{
			GateDocument parent = ((WithdrawDocument)((ConvertableToGateDocument) stateObject).asGateDocument()).getTransferPayment();
			if(parent instanceof StateObject && CardPaymentSystemPayment.class.isAssignableFrom(parent.getType()))
				return esbCardBillingPaymentCondition.accepted((StateObject) parent, stateMachineEvent);
		}
		return esbDocuments.contains(type);
	}
}
