package com.rssl.phizic.web.mapi.auth.login;

import com.rssl.auth.csamapi.operations.LoginOperation;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.Form;
import com.rssl.phizic.web.auth.ActionFormBase;
import com.rssl.phizic.web.auth.LookupDispatchAction;
import com.rssl.phizic.web.mapi.auth.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 03.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен входа пользовател€ в ћјѕ»
 */
public class LoginActionBase extends LookupDispatchAction
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("button.login", "login");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(OperationalActionBase.FORWARD_START);
	}

	/**
	 * ¬ход пользовател€
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return forward
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OperationalActionBase action = new OperationalActionBase()
		{
			@Override
			protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
			{
				LoginOperation op = (LoginOperation) operation;
				LoginForm frm = (LoginForm) form;

				frm.setHost(op.getHost());
				frm.setToken(op.getToken());
			}

			@Override
			protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
			{
				LoginOperation operation = new LoginOperation();
				operation.initialize(data);
				return operation;
			}

			@Override
			protected Form getForm()
			{
				return LoginForm.FORM;
			}
		};

		return action.start(mapping, form, request, response);
	}
}
