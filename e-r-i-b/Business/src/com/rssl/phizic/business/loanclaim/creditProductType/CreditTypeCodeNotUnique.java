package com.rssl.phizic.business.loanclaim.creditProductType;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class CreditTypeCodeNotUnique extends BusinessLogicException
{
	public CreditTypeCodeNotUnique(Throwable cause,Long code)
	{
		super("Тип кредитного продукта с кодом типа: " + code+ " уже существует в системе",cause);
	}

	public CreditTypeCodeNotUnique(String message, Throwable cause)
	{
		super(message, cause);
	}
}
