package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;

/**
 * @author: vagin
 * @ created: 18.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class RefuseAutoPaymentTypeCondition  implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		return ((AutoPaymentBase)stateObject).getType() == RefuseAutoPayment.class;
	}
}
