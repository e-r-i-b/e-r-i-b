package com.rssl.phizic.web.client.ext.sbrf.persons;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.config.authService.AuthServiceConfig;
import com.rssl.phizic.operations.ofert.ChangeUseOfertOperation;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 23.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class UseOfertAction extends LoginStageActionSupport
{
	private static final String FORWARD_SHOW = "Show";
	private static final String FORWARD_CONFIRM = "Confirm";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirmOfert","confirm");
		map.put("button.skip", "skip");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UseOfertForm frm = (UseOfertForm) form;
		Login login = (Login) AuthenticationContext.getContext().getLogin();
		ChangeUseOfertOperation operation = createOperation("ChangeUseOfertOperation");
		operation.initialize(login);

		frm.setFullName(operation.getPerson().getFullName());
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UseOfertForm frm = (UseOfertForm) form;
		Login login = (Login) AuthenticationContext.getContext().getLogin();
		ChangeUseOfertOperation operation = createOperation("ChangeUseOfertOperation");
		operation.initialize(login);

		//Значение чекбокса, означающего согласие клиента с офертой
		String selectAgreed = (String) frm.getField("selectAgreed");
		//клиент отказался от оферты
		String selectCancel = (String) frm.getField("selectCancel");

		ActionMessages msgs = new ActionMessages();
		if (selectAgreed != null && selectCancel != null)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Выберите только один вариант.", false));
			saveErrors(request, msgs);
			return start(mapping, form, request, response);
		}
		if (selectAgreed == null && selectCancel == null)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Выберите один из вариантов.", false));
			saveErrors(request, msgs);
			return start(mapping, form, request, response);
		}
		if (selectAgreed != null)
		{
			operation.setUseOfert(true);
			completeStage();
			return mapping.findForward(FORWARD_CONFIRM);
		}
		operation.setUseOfert(false);
		if(ConfigFactory.getConfig(AuthServiceConfig.class).isUseOwnAuth())
			completeStage();
		else
			restartAuthentication(request, new ActionMessage("Для продолжения работы в системе необходимо принять условия оферты!", false));
		return mapping.findForward(FORWARD_CONFIRM);
	}

	public ActionForward skip(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(ConfigFactory.getConfig(AuthServiceConfig.class).isUseOwnAuth())
			completeStage();
		else
			restartAuthentication(request, new ActionMessage("Для продолжения работы в системе необходимо принять условия оферты!", false));
		return mapping.findForward(FORWARD_CONFIRM);
	}
}
