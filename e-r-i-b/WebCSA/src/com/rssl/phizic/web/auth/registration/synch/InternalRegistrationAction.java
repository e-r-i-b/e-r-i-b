package com.rssl.phizic.web.auth.registration.synch;

import com.rssl.phizic.config.FrontSettingHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niculichev
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 */
public class InternalRegistrationAction extends RegistrationActionBase
{
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!FrontSettingHelper.isAccessInternalRegistration())
			return mapping.findForward(LOGIN_REDIRECT);

		return super.doExecute(mapping, form, request, response);
	}
}
