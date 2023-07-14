package com.rssl.phizic.web.atm.auth.login;

import com.rssl.auth.csaatm.operations.LoginOperation;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.Form;
import com.rssl.phizic.web.auth.ActionFormBase;
import com.rssl.phizic.web.auth.LookupDispatchAction;
import com.rssl.phizic.web.atm.auth.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Экшен входа пользователя в АТМ через ЦСА
 */
public class LoginAction extends LookupDispatchAction
{
	/**
	 * Вход пользователя в атм
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return forward
	 * @throws Exception
	 */
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OperationalActionBase action = new OperationalActionBase()
		{
			@Override protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
			{
				LoginForm frm = (LoginForm) form;
				LoginOperation op = (LoginOperation) operation;

				frm.setHost(op.getHost());
				frm.setToken(op.getToken());
			}

			@Override protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
			{
				LoginOperation operation = new LoginOperation();
				operation.initialize(data);
				return operation;
			}

			@Override protected Form getForm()
			{
				return LoginForm.FORM;
			}
		};

		return action.start(mapping, form, request, response);
	}
}
