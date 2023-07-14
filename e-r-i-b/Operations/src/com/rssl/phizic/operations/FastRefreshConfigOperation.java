package com.rssl.phizic.operations;

import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.events.RefreshCsaConfigEvent;
import com.rssl.phizic.config.events.RefreshPhizIcConfigEvent;

/**
 * применение настроек СП.
 *
 * @author bogdanov
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class FastRefreshConfigOperation extends OperationBase
{
	/**
	 * Отсылает сообщение об обновлении настроек ЦСА.
	 */
	public void refreshCsaConfig() throws BusinessException
	{
		try
		{
			EventSender.getInstance().sendEvent(new RefreshCsaConfigEvent());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Отсылает сообщение об обновлении настроек основного приложения.
	 */
	public void refreshPhizIcConfig() throws BusinessException
	{
		try
		{
			EventSender.getInstance().sendEvent(new RefreshPhizIcConfigEvent());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
