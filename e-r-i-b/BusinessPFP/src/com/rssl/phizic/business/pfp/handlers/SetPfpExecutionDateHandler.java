package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 27.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class SetPfpExecutionDateHandler extends PersonalFinanceProfileHandlerBase
{
	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent)
	{
		if (profile.getExecutionDate() == null)
			profile.setExecutionDate(Calendar.getInstance());
	}
}
