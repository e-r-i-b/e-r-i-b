package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.phizic.web.actions.OperationalActionBase;
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
public class EditRegistrationAction extends OperationalActionBase
{
	@SuppressWarnings({"ThisEscapedInObjectConstruction"})
	private final RegistrationWizard wizard = new RegistrationWizard(this);

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
		return wizard.skipRegistration(mapping);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return wizard.saveRegistrationEdit(mapping, form, request);
	}
}