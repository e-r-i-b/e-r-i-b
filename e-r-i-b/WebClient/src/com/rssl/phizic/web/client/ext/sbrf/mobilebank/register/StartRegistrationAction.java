package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.security.SecurityUtil;
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
public class StartRegistrationAction extends OperationalActionBase
{
	@SuppressWarnings({"ThisEscapedInObjectConstruction"})
	private final RegistrationWizard wizard = new RegistrationWizard(this);

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

	public ActionForward skip(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return wizard.skipRegistration(mapping);
	}
}
