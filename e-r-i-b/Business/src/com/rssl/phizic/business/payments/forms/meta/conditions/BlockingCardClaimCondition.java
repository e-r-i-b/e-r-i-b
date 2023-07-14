package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.common.forms.state.StateObject;

/**
 * @author gladishev
 * @ created 16.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class BlockingCardClaimCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (stateObject instanceof ConvertableToGateDocument)
		{
			return CardBlockingClaim.class == ((ConvertableToGateDocument) stateObject).asGateDocument().getType();
		}
		return false;
	}
}
