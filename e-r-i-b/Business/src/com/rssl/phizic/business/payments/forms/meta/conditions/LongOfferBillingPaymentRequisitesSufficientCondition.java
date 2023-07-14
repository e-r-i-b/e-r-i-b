package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;

/**
 * Кондишн на проверку достаточности реквизитов автоподписки биллингового платежа для осуществления оплаты
 * @author niculichev
 * @ created 13.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferBillingPaymentRequisitesSufficientCondition extends BillingPaymentRequisitesSufficientCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		// если реквизитов достаточно для оплаты и стоит признак автоплатежа
		return super.accepted(stateObject, stateMachineEvent) &&
				((stateObject instanceof AbstractLongOfferDocument) && ((AbstractLongOfferDocument) stateObject).isLongOffer());
	}
}
