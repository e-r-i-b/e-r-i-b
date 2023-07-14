package com.rssl.phizic.einvoicing.exceptions;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Исключение о неактуальности заказа в интернет-магазине.
 *
 * @author bogdanov
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

public class InactualOrderInInternetShopException extends GateLogicException
{
	private static final String INACTIVE_MESSAGE = "Данный заказ был отменен или изменен в системе поставщика. Оплата невозможна.";

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
