package com.rssl.phizic.web.client.login;

import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * экшен для странице по помощи к входу
 * @author basharin
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */

public class LoginErrorAction extends OperationalActionBase
{
	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginErrorForm frm = (LoginErrorForm) form;
		AuthConfig authConfig = AuthGateSingleton.getAuthService().getConfig();
		frm.setUrlRecover(authConfig.getProperty("csaFront.recover.url"));
		frm.setUrlRegistration(authConfig.getProperty("csaFront.registration.url"));

		SelfRegistrationConfig selfRegistrationConfig = ConfigFactory.getConfig(SelfRegistrationConfig.class);
		frm.setVisibleMultipleRegistrationPart(selfRegistrationConfig.isVisibleMultipleRegistrationPart());

		return mapping.findForward(FORWARD_SHOW);
	}
}
