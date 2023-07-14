package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * @author komarov
 * @ created 01.04.2014
 * @ $Author$
 * @ $Revision$
 */
public class OurCardCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!(stateObject instanceof RurPayment))
			throw new BusinessException("Ожидается RurPayment");

		return ((RurPayment)stateObject).isOurBankCard();
	}
}
