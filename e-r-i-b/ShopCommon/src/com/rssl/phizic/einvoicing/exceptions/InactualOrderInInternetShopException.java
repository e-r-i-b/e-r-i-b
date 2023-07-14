package com.rssl.phizic.einvoicing.exceptions;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * ���������� � �������������� ������ � ��������-��������.
 *
 * @author bogdanov
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

public class InactualOrderInInternetShopException extends GateLogicException
{
	private static final String INACTIVE_MESSAGE = "������ ����� ��� ������� ��� ������� � ������� ����������. ������ ����������.";

	public InactualOrderInInternetShopException()
	{
		super(INACTIVE_MESSAGE);
	}

	public InactualOrderInInternetShopException(String message)
	{
		super(INACTIVE_MESSAGE);
	}

	public InactualOrderInInternetShopException(Throwable cause)
	{
		super(INACTIVE_MESSAGE, cause);
	}
}
