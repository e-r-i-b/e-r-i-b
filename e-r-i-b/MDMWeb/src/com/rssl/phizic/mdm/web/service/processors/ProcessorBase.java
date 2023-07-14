package com.rssl.phizic.mdm.web.service.processors;

import com.rssl.phizic.mdm.web.service.generated.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовая реализация процессора запроса
 */

public abstract class ProcessorBase<T>
{
	protected abstract T getData(RequestData requestData);
	protected abstract void process(T source, ResponseData destination) throws ProcessorException;

	/**
	 * обработать запрос
	 * @param requestData данные запроса
	 * @param responseData данные ответа
	 * @throws ProcessorException
	 */
	public final void process(RequestData requestData, ResponseData responseData) throws ProcessorException
	{
		process(getData(requestData), responseData);
	}

	protected void processError(String message, Throwable exception) throws ProcessorException
	{
		throw new ProcessorException(message, exception);
	}

	protected static Calendar parseCalendar(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}
}
