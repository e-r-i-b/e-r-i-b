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
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Четвёртый шаг процедуры подключения МБ: просмотр статуса подключения
 */
public class ViewRegistrationAction extends OperationalActionBase
{
	@SuppressWarnings({"ThisEscapedInObjectConstruction"})
	private final RegistrationWizard wizard = new RegistrationWizard(this);

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("next", "next");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return wizard.viewRegistrationStatus(mapping, form);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return wizard.completeRegistration(mapping);
	}
}