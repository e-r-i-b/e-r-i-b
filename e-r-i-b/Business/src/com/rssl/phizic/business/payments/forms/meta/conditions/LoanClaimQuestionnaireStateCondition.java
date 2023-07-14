package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.FieldValueChange;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import static com.rssl.phizic.business.payments.forms.meta.LoanClaimQuestionnaireUpgradeHandler.*;

/**
 * @author Gulov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверка на изменения в анкете
 */
public class LoanClaimQuestionnaireStateCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof ExtendedLoanClaim))
			throw new BusinessException("Неверный тип платежа id=" + stateObject.getId() + " (Ожидается ExtendedLoanClaim)");
		FieldValueChange fieldValue = stateMachineEvent.getChangedField(QUESTIONNAIRE_STATE_FIELD);
		if (fieldValue == null)
			return false;
		String value = (String) fieldValue.getNewValue();
		if (QUESTIONNAIRE_STATE_ADDED.equals(value))
			return true;
		else if (QUESTIONNAIRE_STATE_DELETED.equals(value))
			return false;
		else if (QUESTIONNAIRE_STATE_CHANGED.equals(value))
			return true;
		return false;
	}
}
