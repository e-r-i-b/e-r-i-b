package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

/**
 * @author akrenev
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * очистка дохода, который планируем получить
 */
public class ClearPlanMoneyHandler extends PersonalFinanceProfileHandlerBase
{
	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent)
	{
		profile.setPlanMoney(null);
	}
}
