package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Erkin
 * @ created 17.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Кондишен всегда возвращает true
 */
public final class AlwaysTrueStateObjectCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		return true;
	}
}
