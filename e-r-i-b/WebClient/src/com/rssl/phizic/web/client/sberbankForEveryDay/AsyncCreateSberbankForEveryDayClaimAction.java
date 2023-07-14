package com.rssl.phizic.web.client.sberbankForEveryDay;

import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * подтверждение заявки на получение дебетовой карты
 * @author lukina
 * @ created 22.01.2015
 * @ $Author$
 * @ $Revision$
 */
public class AsyncCreateSberbankForEveryDayClaimAction extends CreateSberbankForEveryDayClaimAction
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
