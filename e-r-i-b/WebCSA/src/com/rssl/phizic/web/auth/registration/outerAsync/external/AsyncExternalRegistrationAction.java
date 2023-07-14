package com.rssl.phizic.web.auth.registration.outerAsync.external;

import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.web.auth.registration.RegistrationForm;
import com.rssl.phizic.web.auth.registration.outerAsync.AsyncRegistrationActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен внешней асинхронной регистрации
 * @author tisov
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncExternalRegistrationAction extends AsyncRegistrationActionBase
{
	private final static String ACTION_PATH = "/async/page/external/registration";

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (!FrontSettingHelper.isAccessExternalRegistration())
			return mapping.findForward(INDEX_REDIRECT_FORWARD_NAME);
		RegistrationForm frm = (RegistrationForm) form;
		frm.setShowPromoBlock(true);
		frm.setActionPath(ACTION_PATH);
		return super.doExecute(mapping, form, request, response);
	}
}
