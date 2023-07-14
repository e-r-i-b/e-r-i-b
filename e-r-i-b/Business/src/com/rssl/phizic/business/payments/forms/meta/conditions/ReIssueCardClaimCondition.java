package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.gate.payments.ReIssueCardClaim;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * Кондишен на проверку является ли документ заявкой на перевыпуск карты.
 *
 * @author bogdanov
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReIssueCardClaimCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (stateObject instanceof ConvertableToGateDocument)
		{
			return ReIssueCardClaim.class == ((ConvertableToGateDocument) stateObject).asGateDocument().getType();
		}
		return false;
	}
}
