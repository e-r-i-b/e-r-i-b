package com.rssl.phizic.web.common.client.widget;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Barinov
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшн дл€ загрузки виджетов клиента в сессию
 */
public class LoadWidgetsAction implements AthenticationCompleteAction
{
	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		try
		{
			WidgetManager.loadWidgets();
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new SecurityLogicException(e);
		}
	}
}
