package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * @author osminin
 * @ created 27.04.15
 * @ $Author$
 * @ $Revision$
 *
 * Проверка на то, что платеж обрабатывается сотрудником ВСП, вошедшим под клиентом по карте
 */
public class EmployeeCardVisitingModeCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		BusinessDocument document = (BusinessDocument) stateObject;
		Login login = document.getOwner().getLogin();

		return UserVisitingMode.EMPLOYEE_INPUT_BY_CARD == login.getLastUserVisitingMode();
	}
}
