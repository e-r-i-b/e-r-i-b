package com.rssl.phizic.business.exception;

import com.rssl.phizic.business.exception.locale.ExternalLocalizingExceptionProcessor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.statistics.exception.ExceptionStatisticService;
import com.rssl.phizic.gate.statistics.exception.ExternalExceptionInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.logging.exceptions.ExternalExceptionEntry;

/**
 * @author akrenev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * сервис сбора статистики по исключениям
 */

public class ExternalExceptionEntryService extends AbstractService implements ExceptionStatisticService
{
	private static final ExceptionEntryUpdateService service = new ExceptionEntryUpdateService();

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 */
	public ExternalExceptionEntryService(GateFactory factory)
	{
		super(factory);

	}

	public String addException(ExternalExceptionInfo exceptionInfo)
	{
		String errorCode = exceptionInfo.getErrorCode();
		String messageKey = exceptionInfo.getMessageKey();
		ExternalExceptionEntry exceptionEntry = new ExternalExceptionEntry(errorCode, exceptionInfo.getDetail(), messageKey, exceptionInfo.getSystem());
		String message = service.getMessageAndUpdate(exceptionEntry, ExceptionEntryApplication.fromApplication(exceptionInfo.getApplication()), exceptionInfo.getTb());
		return StringHelper.isNotEmpty(message)? message: ExternalLocalizingExceptionProcessor.getMessage(exceptionInfo);
	}
}
