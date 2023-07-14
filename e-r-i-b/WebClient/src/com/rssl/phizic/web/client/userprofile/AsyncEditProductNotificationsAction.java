package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 20.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class AsyncEditProductNotificationsAction extends EditProductNotificationsAction
{
	private static final String NEXT_STAGE_KEY = "next";

	protected boolean isAjax()
	{
		return true;
	}

	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
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
