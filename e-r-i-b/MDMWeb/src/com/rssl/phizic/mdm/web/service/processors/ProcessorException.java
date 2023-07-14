package com.rssl.phizic.mdm.web.service.processors;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ошибка обработки запроса
 */

public class ProcessorException extends GateException
{
	/**
	 * @param message сообщение ошибки
	 * @param cause источник ошибки
	 */
	public ProcessorException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @return код ошибки
	 */
	public long getErrorCode()
	{
		return -1;
	}

	/**
	 * @return описание ошибки
	 */
	public String getErrorDescription()
	{
		return getMessage();
	}
}
