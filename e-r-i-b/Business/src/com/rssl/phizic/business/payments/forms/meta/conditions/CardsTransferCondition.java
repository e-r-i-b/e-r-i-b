package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.gate.payments.CardsTransfer;

/**
 * @author krenev
 * @ created 21.05.2010
 * @ $Author$
 * @ $Revision$
 * проверяем, что объект является переводом между картами.
 */

public class CardsTransferCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (stateObject instanceof ConvertableToGateDocument)
		{
			return CardsTransfer.class.isAssignableFrom(((ConvertableToGateDocument) stateObject).asGateDocument().getType());
		}
		return false;
	}
}
