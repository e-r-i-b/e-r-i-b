package com.rssl.phizic.web.client.security;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.passwords.ChangePersonPasswordOperation;
import com.rssl.phizic.security.password.UserPasswordValidator;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 21.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class SelfChangePasswordAction extends OperationalActionBase
{
	private static final String FORWARD_SHOW = "ShowForm";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();

		map.put("button.changePassword", "change");

		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangePersonPasswordOperation operation = createOperation(ChangePersonPasswordOperation.class);
		operation.initialize();
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward change(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangePersonPasswordOperation operation = createOperation(ChangePersonPasswordOperation.class);
		operation.initialize();
		ChangePasswordForm frm = (ChangePasswordForm) form;
		String oldPassword = frm.getOldPassword();
		UserPasswordValidator validator = new UserPasswordValidator();
		try
		{
			validator.validateLoginInfo(operation.getLogin().getUserId(), oldPassword.toCharArray());
		}
		catch(SecurityLogicException sle)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Неверно указан старый пароль", false));
			saveErrors(request, msgs);
			return mapping.findForward(FORWARD_SHOW);
		}
		catch(SecurityException se)
		{
			throw new BusinessException(se);
		}

		new ChangePasswordAction().change(mapping, form, request, response);
        return mapping.findForward(FORWARD_SHOW);
	}
}
