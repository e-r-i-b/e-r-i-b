package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * @author Nady
 * @ created 19.02.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверка на наличие МБК у клиента
 */
public class LoanClaimVisitOfficeCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof ExtendedLoanClaim))
			throw new BusinessException("Неверный тип платежа id=" + stateObject.getId() + " (Ожидается ExtendedLoanClaim)");
		ExtendedLoanClaim claim = (ExtendedLoanClaim) stateObject;
		GuestPerson person = null;
		if (claim.getOwner().getLogin() instanceof GuestLogin)
			person = (GuestPerson) claim.getOwner().getPerson();
		else
			return false;

		return person != null && !person.isHaveMBKConnection();
	}
}
