package com.rssl.phizic.web.employees;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.util.EmployeeInfoUtil;

/**
 * @author akrenev
 * @ created 06.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * Экшен добавляющий сообщение необходимости обновить инфу менеджера (если нужно)
 */

public class CheckManagerInfoAction implements AthenticationCompleteAction
{
	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		if (EmployeeInfoUtil.needUpdateManagerInfo())
			context.putMessage("Пожалуйста, заполните Ваши персональные данные. Для этого нажмите на ФИО вверху страницы");
	}
}
