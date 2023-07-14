package com.rssl.phizic.web.security;

import com.rssl.phizic.authgate.authorization.AuthGateService;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 14.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class CSALogoffAction extends LogoffActionBase
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthGateService service = AuthGateSingleton.getAuthService();
		AuthParamsContainer container = new AuthParamsContainer();
		container.addParameter("SID", AuthenticationContext.getContext().getCSA_SID());
		service.closeSession(container);
		return super.execute(mapping, form, request, response);
	}
}
