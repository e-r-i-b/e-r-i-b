package com.rssl.phizic.web.client.security;

import com.rssl.phizic.web.util.StringFunctions;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * валидация данных переданных через ajax
 * @author basharin
 * @ created 01.03.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncCheckOldPasswordAction extends CheckOldPasswordAction
{
	private static final String FORWARD_SUCCESS = "Success";
	private static final String FORWARD_FIELD_ERROR = "FieldError";
	private static final String FORWARD_MESSAGE_ERROR = "MessageError";

	protected Map<String, String> getKeyMethodMap()
	{
	    Map<String,String> map = new HashMap<String, String>();
		map.put("changePassword","changePassword");
		return map;
	}

	@Override protected ActionForward createSuccessForward(ActionMapping mapping)
	{
		return mapping.findForward(FORWARD_SUCCESS);
	}

	@Override protected ActionForward createErrorForward(ActionMapping mapping, HttpServletRequest request, ActionForm form, ActionMessage msg)
	{
		CheckOldPasswordForm frm = (CheckOldPasswordForm) form;
		frm.setTextError(msg.getKey());
		return mapping.findForward(FORWARD_MESSAGE_ERROR);
	}

	@Override public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionMessages errors = new ActionMessages();
		ActionForward forward = doChangePassword(mapping, form, request, response, errors);

		CheckOldPasswordForm frm = (CheckOldPasswordForm) form;
		Iterator iterator = errors.get(ActionMessages.GLOBAL_MESSAGE);
		if (iterator.hasNext())
		{
			ActionMessage message = (ActionMessage)iterator.next();
			if (message.getValues() != null)
			{
				frm.setNameFieldError(message.getKey());
				frm.setTextError(StringFunctions.replaceQuotes(((ActionMessage) message.getValues()[0]).getKey()));
				return mapping.findForward(FORWARD_FIELD_ERROR);
			}
			else
			{
				frm.setTextError(StringFunctions.replaceQuotes(message.getKey()));
				return mapping.findForward(FORWARD_MESSAGE_ERROR);
			}
		}
		return  forward;
	}

	@Override protected boolean isAjax()
	{
		return true;
	}
}
