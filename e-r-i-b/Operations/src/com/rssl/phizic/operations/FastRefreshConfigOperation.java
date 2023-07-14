package com.rssl.phizic.operations;

import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.events.RefreshCsaConfigEvent;
import com.rssl.phizic.config.events.RefreshPhizIcConfigEvent;

/**
 * ���������� �������� ��.
 *
 * @author bogdanov
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class FastRefreshConfigOperation extends OperationBase
{
	/**
	 * �������� ��������� �� ���������� �������� ���.
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
	 * �������� ��������� �� ���������� �������� ��������� ����������.
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
