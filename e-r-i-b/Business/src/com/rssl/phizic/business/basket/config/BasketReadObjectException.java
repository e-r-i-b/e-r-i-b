package com.rssl.phizic.business.basket.config;

/**
 * @author tisov
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Исключение, выбрасываемое в случае если объект учёта не был найден в файле настроек
 */
public class BasketReadObjectException extends Exception
{
	public BasketReadObjectException(String msg)
	{
		super(msg);
	}
}
