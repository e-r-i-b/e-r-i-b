package com.rssl.phizic.rsa.exceptions;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author tisov
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 * Исключение, говорящее об ошибке сохранения данных по фрод-запросу в таблицу оповещений
 */
public class AppendNotificationException extends GateLogicException
{
	public AppendNotificationException(Throwable e)
	{
		super(e);
	}
}
