package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;

/**
 * User: Moshenko
 * Date: 18.11.2011
 * Time: 11:22:46
 * ошибка в статусе периодического задания
 */
public class UnloadTaskStateException extends BusinessException
{
	UnloadTaskStateException(String message)
	{
		super(message);
	}
}
