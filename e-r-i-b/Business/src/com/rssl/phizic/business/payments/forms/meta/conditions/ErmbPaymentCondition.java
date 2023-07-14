package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * @author Rtischeva
 * @ created 20.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbPaymentCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (ApplicationUtil.isErmbSms())
			return true;
		return false;
	}
}
