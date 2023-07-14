package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * Кондишен проверяющий необходимость перевода документа в статус EXECUTED
 * @author gladishev
 * @ created 17.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class ExecutedNextStateCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		return "EXECUTED".equals(stateObject.getNextState());
	}
}
