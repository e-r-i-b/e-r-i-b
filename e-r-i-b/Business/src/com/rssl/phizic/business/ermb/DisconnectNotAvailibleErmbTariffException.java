package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Moshenko
 * @ created 04.12.13
 * @ $Author$
 * @ $Revision$
 * ������ ��������� � ��� ��� ����� ������ �������.(���� ����������� ������������)
 */
public class DisconnectNotAvailibleErmbTariffException extends BusinessLogicException
{
	private long activeTariffId; //Id ��������� ������, ������� �������� ���������.

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
