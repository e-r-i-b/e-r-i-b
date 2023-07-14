package com.rssl.phizic.gate.statistics.exception;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 17.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер для сбора статистики и декорирования ошибок внешних систем
 */

public final class GateExceptionStatisticHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);
	private static final List<Application> externalExceptionApplications = new ArrayList<Application>(12);

	static
	{
		//при добавлении не забываем про:
		// 1) com/rssl/phizic/web/configure/exceptions/resources.properties    (application.?)
		// 2) /WEB-INF/jsp/configure/exceptions/list.jsp                       (filter(application))
		externalExceptionApplications.add(Application.PhizIA);
		externalExceptionApplications.add(Application.PhizIC);
		externalExceptionApplications.add(Application.mobile5);
		externalExceptionApplications.add(Application.mobile6);
		externalExceptionApplications.add(Application.mobile7);
		externalExceptionApplications.add(Application.mobile8);
		externalExceptionApplications.add(Application.mobile9);
		externalExceptionApplications.add(Application.socialApi);
		externalExceptionApplications.add(Application.WebAPI);
		externalExceptionApplications.add(Application.ErmbSmsChannel);
		externalExceptionApplications.add(Application.atm);
		externalExceptionApplications.add(Application.Gate);
	}

	private GateExceptionStatisticHelper(){}

	/**
	 * Собрать основные данные об ошибке
	 * @param system система, породившая ошибку
	 * @param messageName запрос, породившый ошибку
	 * @param errorCode код ответа
	 * @param errorText текст ошибки в ответе
	 * @return основные данные об ошибке
	 */
	public static ExternalExceptionInfo getBaseExceptionInfo(String system, String messageName, String errorCode, String errorText)
	{
		Application application = LogThreadContext.getInitiatorApplication();
		String tb = LogThreadContext.getDepartmentRegion();

		ExternalExceptionInfo exceptionInfo = new ExternalExceptionInfo();
		exceptionInfo.setSystem(system);
		exceptionInfo.setMessageKey(messageName);
		exceptionInfo.setErrorCode(errorCode);
		exceptionInfo.setErrorText(errorText);
		exceptionInfo.setApplication(application);
		exceptionInfo.setTb(tb);
		return exceptionInfo;
	}

	/**
	 * получить замаппленное сообщение для ошибки
	 * @param exceptionInfo описание ошибки
	 * @return замаппленное сообщение
	 */
	public static String getCustomErrorMessage(ExternalExceptionInfo exceptionInfo)
	{
		try
		{
			if (!externalExceptionApplications.contains(exceptionInfo.getApplication()))
				return null;

			ExceptionStatisticService service = GateSingleton.getFactory().service(ExceptionStatisticService.class);
			return service.addException(exceptionInfo);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		return StringUtils.EMPTY;
	}
}
