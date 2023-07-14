package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;


/**
 * @author komarov
 * @ created 29.07.15
 * @ $Author$
 * @ $Revision$
 * �������� �� �������� ������������� ���������� ������������ ������� ��� ������������� ������ � ������ ������������.
 */
public class OfflineBillingPaymentRequisitesSufficientCondition extends BillingPaymentRequisitesSufficientCondition
{
	private static final OfflineDelayedCondition OFFLINE_DELAYED_CONDITION = new OfflineDelayedCondition();

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!OFFLINE_DELAYED_CONDITION.accepted(stateObject, stateMachineEvent))
			return false;

		return super.accepted(stateObject, stateMachineEvent);
	}

}