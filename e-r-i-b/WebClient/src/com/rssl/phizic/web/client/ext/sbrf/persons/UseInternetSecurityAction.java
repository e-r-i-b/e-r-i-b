package com.rssl.phizic.web.client.ext.sbrf.persons;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.operations.security.ChangeUseInternetSecurityOperation;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 10.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class UseInternetSecurityAction extends LoginStageActionSupport
{
	private static final String FORWARD_SHOW = "Show";
	private static final String FORWARD_CONFIRM = "Confirm";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirm","confirm");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// создание операции оставлено только для проверки прав доступа
		ChangeUseInternetSecurityOperation operation = createOperation("ChangeUseInternetSecurityOperation");

		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UseInternetSecurityForm frm = (UseInternetSecurityForm) form;
		Login login = (Login) AuthenticationContext.getContext().getLogin();
		ChangeUseInternetSecurityOperation operation = createOperation("ChangeUseInternetSecurityOperation");

		//Значение чекбокса, означающего согласие клиента
		String selectAgreed = (String) frm.getField("selectAgreed");

		ActionMessages msgs = new ActionMessages();

		if (selectAgreed == null )
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Для продолжения работы необходимо принять условиия.", false));
			saveErrors(request, msgs);
			return start(mapping, form, request, response);
		}
		operation.initialize(login);
		operation.setUseInternetSecurity(true);
		completeStage();
		return mapping.findForward(FORWARD_CONFIRM);

	}
}
