package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

/**
 * Заполненность реквизитов при создании автоподписки по сущности подписки на инвойсы
 * @author niculichev
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubByInvoceSubEntityRequisitesSufficientCondition extends LongOfferBillingPaymentRequisitesSufficientCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!(stateObject instanceof JurPayment))
			return false;

		JurPayment payment = (JurPayment) stateObject;
		if(payment.getType() != CardPaymentSystemPaymentLongOffer.class)
			return false;

		if(payment.getInvoiceSubscriptionId() == null)
			return false;

		return super.accepted(stateObject, stateMachineEvent);
	}
}
