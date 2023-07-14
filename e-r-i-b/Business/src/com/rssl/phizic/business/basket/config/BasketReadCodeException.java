package com.rssl.phizic.business.basket.config;

/**
 * @author tisov
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * »сключение, выбрасываемое в случае если по заданному коду категории услуги не удалось сформировать экземпл€р,
 * то есть в файле настроек не оказалось какого-то из полей категории
 */
public class BasketReadCodeException extends Exception
{
	public BasketReadCodeException(String msg)
	{
		super(msg);
	}
}
