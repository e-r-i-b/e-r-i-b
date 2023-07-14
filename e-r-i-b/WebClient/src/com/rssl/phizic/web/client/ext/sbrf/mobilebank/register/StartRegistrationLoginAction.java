package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.auth.modes.Stage;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Первый шаг процедуры подключения МБ
 */
public class StartRegistrationLoginAction extends SBRFLoginActionBase
{
	@SuppressWarnings({"ThisEscapedInObjectConstruction"})
	private final LoginRegistrationWizard wizard = new LoginRegistrationWizard(this);

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("skip", "skip");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		StartRegistrationForm form = (StartRegistrationForm) frm;
		form.setAuthenticationComplete(SecurityUtil.isAuthenticationComplete());
		return wizard.startRegistration(mapping, form);
	}

	public ActionForward skip(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Stage stage = currentStage();
		if (!stage.getKey().equals(wizard.getRegistrationLoginStage()))
			return redirectToStage(request, stage);
		return continueStage(mapping, request);
	}
}
