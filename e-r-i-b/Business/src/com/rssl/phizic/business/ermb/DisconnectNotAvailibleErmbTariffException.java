package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Moshenko
 * @ created 04.12.13
 * @ $Author$
 * @ $Revision$
 * Ошибка говорящая о том что тариф нельзя удалить.(есть привязанные пользователи)
 */
public class DisconnectNotAvailibleErmbTariffException extends BusinessLogicException
{
	private long activeTariffId; //Id активного тарифа, который пытаются отключить.

	public DisconnectNotAvailibleErmbTariffException(Throwable cause,long activeTariffId)
	{
		super(cause);
		this.activeTariffId = activeTariffId;
	}

	public DisconnectNotAvailibleErmbTariffException(String message, Throwable cause, long activeTariffId)
	{
		super(message, cause);
		this.activeTariffId = activeTariffId;
	}

	public long getActiveTariffId()
	{
		return activeTariffId;
	}
}
