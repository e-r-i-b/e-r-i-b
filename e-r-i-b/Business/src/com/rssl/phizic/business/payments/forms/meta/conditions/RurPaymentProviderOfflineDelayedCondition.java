package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.RurPayment;

/**
 * Условие для фиксации недоступности внешних систем. Используется при оплате яндекс кошелька в переводе частному лицу
 * @author gladishev
 * @ created 11.11.2014
 * @ $Author$
 * @ $Revision$
 */

public class RurPaymentProviderOfflineDelayedCondition extends OfflineDelayedCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof RurPayment))
			throw new BusinessException("Ожидается RurPayment");

		RurPayment payment = (RurPayment) stateObject;
		if (!payment.isServiceProviderPayment())
			return false;

		return super.accepted(stateObject, stateMachineEvent);
	}
}
