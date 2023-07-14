package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Balovtsev
 * Date: 24.05.2012
 * Time: 10:53:59
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class AsynchQuickServiceAction extends ListRegistrationsAction
{
	private static final String NEXT_STAGE_KEY = "next";

	protected boolean isAjax()
	{
		return true;
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		try
		{
			response.getWriter().write(NEXT_STAGE_KEY);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}

		return null;
	}
}