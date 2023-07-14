package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * @author Gulov
 * @ created 10.02.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверка существования анкеты
 */
public class LoanClaimQuestionnaireExistCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof ExtendedLoanClaim))
			throw new BusinessException("Неверный тип платежа id=" + stateObject.getId() + " (Ожидается ExtendedLoanClaim)");
		ExtendedLoanClaim claim = (ExtendedLoanClaim) stateObject;
		return claim.getQuestionCount() == 0;
	}
}
