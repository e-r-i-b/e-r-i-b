package com.rssl.phizic.web.auth.registration.outerAsync;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.web.auth.registration.RegistrationForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен асинхронной регистрации
 * @author tisov
 * @ created 16.01.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncRegistrationAction extends AsyncRegistrationActionBase
{

	private final static String ACTION_PATH = "/async/page/registration";

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (!FrontSettingHelper.isAccessInternalRegistration())
			return mapping.findForward(INDEX_REDIRECT_FORWARD_NAME);
		RegistrationForm frm = (RegistrationForm) form;
		frm.setShowPromoBlock(false);
		frm.setActionPath(ACTION_PATH);
		frm.setHintDelay(ConfigFactory.getConfig(SelfRegistrationConfig.class).getHintDelay());
		frm.setMinLoginLength(ConfigFactory.getConfig(CSAFrontConfig.class).getMinimumLoginLength());
		return super.doExecute(mapping, form, request, response);
	}

}
