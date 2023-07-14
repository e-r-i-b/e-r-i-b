package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;
import com.rssl.phizic.gate.payments.AccountOrIMAOpeningClaimBase;

/**
 * Проверяет условие перехода по состояниям при открытии счетов и ОМС, а также для операций по ОМС
 * @ author: Gololobov
 * @ created: 13.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class AccountIMATypeOperationCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!( (stateObject instanceof AccountOrIMAOpeningClaimBase) ||
			   (stateObject instanceof AccountOrIMATransferBase)))
		{
			return false;
		}
		return true;
	}
}
