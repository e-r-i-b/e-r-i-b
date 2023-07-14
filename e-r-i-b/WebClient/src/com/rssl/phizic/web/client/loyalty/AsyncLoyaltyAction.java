package com.rssl.phizic.web.client.loyalty;

import com.rssl.phizic.operations.loyaltyProgram.LoyaltyProgramInfoOperation;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AsyncLoyaltyAction    extends AsyncOperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoyaltyProgramInfoOperation operation = createOperation(LoyaltyProgramInfoOperation.class);
		LoyaltyProgramInfoForm frm  = (LoyaltyProgramInfoForm) form;
		try
		{
			frm.setLink(operation.getLoyaltyProgramLink());
			frm.setBackError(operation.isBackError());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			frm.setRegError(true);
		}
		return mapping.findForward(FORWARD_START);
	}
}
