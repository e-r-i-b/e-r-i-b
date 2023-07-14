package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author basharin
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class AsyncIPasGenerateSecurityAction extends SetupSecurityAction
{
	private static final String NEXT_STAGE_KEY = "next";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("button.backToEdit", "backToEdit");
		keys.put("button.confirm", "confirm");
		keys.put("button.confirmSMS", "changeToSMS");
		keys.put("button.confirmCap", "changeToCap");
		keys.put("button.confirmPush", "changeToPush");
		return keys;
	}

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
	}

	protected Form getForm()
	{
		return SetupSecurityForm.GENERATE_IPAS_PASSWORD_FORM;
	}
}