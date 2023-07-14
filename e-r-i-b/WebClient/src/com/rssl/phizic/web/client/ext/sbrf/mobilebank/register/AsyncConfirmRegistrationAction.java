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
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Третий шаг процедуры подключения МБ на входе: асинхронная отправка и проверка пароля
 */
public class AsyncConfirmRegistrationAction extends OperationalActionBase
{
	@SuppressWarnings({"ThisEscapedInObjectConstruction"})
	private final RegistrationWizard wizard = new LoginRegistrationWizard(this);

	@Override
	protected boolean isAjax()
	{
		return true;
	}

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("preconfirm", "preconfirm");
		map.put("button.confirm", "confirm");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// Сюда в асинхроне не попадаем
		throw new IllegalStateException();
	}

	public ActionForward preconfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return wizard.preconfirmRegistration(mapping, form, request);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward forward = wizard.confirmRegistration(mapping, form, request);
		if (forward.getName().equals(ActionForwards.FORWARD_NEXT)) {
			response.getWriter().print("next");
			return null;
		}
		return forward;
	}
}