package com.rssl.phizic.web.actions;

import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import com.rssl.phizic.web.util.FraudMonitoringUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Хендлер ошибок от ВС ФМ
 *
 * @author khudyakov
 * @ created 18.08.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringErrorHandler extends ExceptionHandler
{
	private static final String FORWARD_SHOW_AJAX_ERROR_PAGE                        = "ShowAjaxFraudMonitoringErrorPage";

	public ActionForward execute (Exception exception, ExceptionConfig exceptionConfig, ActionMapping mapping, ActionForm formInstance, HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		ExceptionLogHelper.writeLogMessage(exception);  //сохраняем исключение
		request.getSession(true).setAttribute("messageKey", exception.getClass().getName());

		FraudMonitoringUtils.storeTokens();             //сохраняем полученные от ФМ значения токенов в сессию
		FraudMonitoringUtils.storeCookie();             //обновляем значение cookie

		if (PersonContext.isAvailable())
		{
			//устанавливаем флаг блокировки профиля в контекст
			PersonContext.getPersonDataProvider().getPersonData().setBlocked(true);
			PersonContext.getPersonDataProvider().getPersonData().setFraud(true);
		}

		String path = exceptionConfig.getPath();
		if (BooleanUtils.isTrue((Boolean) request.getAttribute(LookupDispatchAction.IS_AJAX_ATTRIBUTE_NAME)))
		{
			ActionForward forward = mapping.findForward(FORWARD_SHOW_AJAX_ERROR_PAGE);
			if (forward != null)
			{
				path = forward.getPath();
			}
		}

		if (mapping instanceof UserAgentActionMapping)
		{
			path = ((UserAgentActionMapping) mapping).getAgentRelativePath(path);
		}

		return new ActionForward(path);
	}
}
