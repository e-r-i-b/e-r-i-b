package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author akrenev
 * @ created 17.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер, проверяющий количество целей в планировании
 */
public class CheckTargetHandler extends PersonalFinanceProfileHandlerBase
{
	private static final String ERROR_MESSAGE = "Пожалуйста, укажите хотя бы одну цель.";

	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentLogicException
	{
		if (CollectionUtils.isEmpty(profile.getPersonTargets()))
			throw makeValidationFail(ERROR_MESSAGE);
	}
}
