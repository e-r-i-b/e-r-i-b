package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.web.actions.DefaultExceptionHandler;
import com.rssl.phizic.web.common.client.ext.sbrf.security.ConfirmLoginAction;
import com.rssl.phizic.web.security.AuthenticationManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 13.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AsyncConfirmLoginAction extends ConfirmLoginAction
{
	private static final String NEXT_STAGE_KEY = "next";

	protected boolean isAjax()
	{
		return true;
	}

	/**
	 * Метод для вывода строки в output response
	 * @param otputString
	 * @param response
	 * @throws BusinessException
	 */
	private void writeToResponse(String otputString, HttpServletResponse response) throws BusinessException
	{
		ServletOutputStream ostream = null;
		try
		{
			ostream = response.getOutputStream();
			ostream.println(otputString);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Переопределяем метод для избежания отображения главной страници при протухании сессии
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward goToUnauthotized(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(DefaultExceptionHandler.FORWARD_SHOW_EMPTY_ERROR_PAGE);
	}

	/**
	 * Начать процесс аутентификации снова
	 * @param request запрос
	 * @param error причина ошибка (можно null)
	 * @return форвард на начало
	 */
	public ActionForward restartAuthentication(HttpServletRequest request, ActionMessage error)
	{
		return getCurrentMapping().findForward(DefaultExceptionHandler.FORWARD_SHOW_EMPTY_ERROR_PAGE);
	}

	protected ActionForward nextStage(ActionMapping mapping, HttpServletResponse response) throws BusinessException
	{
		writeToResponse(NEXT_STAGE_KEY, response);
		return null;
	}

	public ActionForward forwardNext(HttpServletRequest request, ActionMapping mapping) throws BusinessException, BusinessLogicException
	{
		AuthenticationManager.nextStage(request);
		return null;
	}
}
