package com.rssl.phizic.business.pfp.handlers;

import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;

import java.util.Calendar;

/**
 * Утанавливает начальную дату периода планирования
 * @author komarov
 * @ created 28.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class SetPfpStartPlaningDateHandler extends PersonalFinanceProfileHandlerBase
{
	protected void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (profile.getStartPlaningDate() == null)
			profile.setStartPlaningDate(Calendar.getInstance());
	}
}
