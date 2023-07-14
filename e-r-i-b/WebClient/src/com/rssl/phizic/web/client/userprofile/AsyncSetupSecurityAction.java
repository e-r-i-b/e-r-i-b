package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonBase;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.userprofile.SetupSecurityOperation;
import com.rssl.phizic.utils.PhoneNumberUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 16.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AsyncSetupSecurityAction extends SetupSecurityAction
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

	protected void updateForm(ConfirmableOperationBase op, ActionForm form) throws BusinessException
	{
		SetupSecurityOperation operation = (SetupSecurityOperation) op;
		SetupSecurityForm frm   = (SetupSecurityForm) form;
		PersonBase person = operation.getPerson();
		frm.setField("mobilePhone", PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhones()));
		frm.setField("email", person.getEmail());
	}
}
