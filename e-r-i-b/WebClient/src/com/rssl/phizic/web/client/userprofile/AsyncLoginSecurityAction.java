package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author basharin
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class AsyncLoginSecurityAction extends SetupSecurityAction
{
	private static final String NEXT_STAGE_KEY = "next";

	protected boolean isAjax()
	{
		return true;
	}

	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
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

	protected void updateOperationData(ConfirmableOperationBase op, Map<String,Object> values)
	{
		//ничего не делаем
	}

	protected Form getForm()
	{
		return SetupSecurityForm.CHANGE_LOGIN_FORM;
	}
}
