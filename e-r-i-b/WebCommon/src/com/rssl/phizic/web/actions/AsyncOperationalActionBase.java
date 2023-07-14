package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForward;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый экшен для обработки асинхронных запросов
 */
public abstract class AsyncOperationalActionBase extends OperationalActionBase
{
	protected boolean getEmptyErrorPage()
	{
		return true;
	}

	protected ActionForward forwardError(String errorText, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		try
		{
			response.setContentType("text/html;charset=windows-1251");
			PrintWriter pw = response.getWriter();
			saveErrors(request, Collections.singletonList(errorText));
			// Пустая страница с текстом ошибки
			pw.println(StringHelper.isEmpty(errorText) ? " " : errorText);
			return null;
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}
}
