package com.rssl.phizic.business.ermb;

/**
 * User: Moshenko
 * Date: 03.10.12
 * Time: 11:47
 * Статусы операций
 */
public enum ErmbOperationStatus
{
	PROVIDED,//предоставляемые
	NOT_PROVIDED,//не предоставляемые
	NOT_PROVIDED_WHEN_NO_PAY;//не предоставляемые при невозможности списать абонентскую плату
}
