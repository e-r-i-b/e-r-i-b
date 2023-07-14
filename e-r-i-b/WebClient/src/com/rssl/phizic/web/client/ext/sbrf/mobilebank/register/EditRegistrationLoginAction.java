package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
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
 * @ created 07.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Второй шаг процедуры подключения МБ
 */
public class EditRegistrationLoginAction extends SBRFLoginActionBase
{
	@SuppressWarnings({"ThisEscapedInObjectConstruction"})
	private final LoginRegistrationWizard wizard = new LoginRegistrationWizard(this);

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("skip", "skip");
		map.put("save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return wizard.startRegistrationEdit(mapping, form, request);
	}

	public ActionForward skip(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Stage stage = currentStage();
		if (!stage.getKey().equals(wizard.getRegistrationLoginStage()))
			return redirectToStage(request, stage);
		return continueStage(mapping, request);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return wizard.saveRegistrationEdit(mapping, form, request);
	}
}
