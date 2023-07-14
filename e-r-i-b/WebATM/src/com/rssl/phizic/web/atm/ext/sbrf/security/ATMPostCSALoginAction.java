package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.operations.security.ATMPostCSALoginOperation;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.client.ext.sbrf.security.LoginNamePasswordAction;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * экшен завершения аутентификации пользователя АТМ
 */
public class ATMPostCSALoginAction extends LoginNamePasswordAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	@Override protected Form getLoginForm()
	{
		return ATMPostCSALoginForm.ATM_LOGIN_FORM;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), getLoginForm());

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_API_ERROR);
		}
		Map<String, Object> data = formProcessor.getResult();

		String token = (String) data.get("token");
		String codeATM = (String) data.get("codeATM");
		ATMPostCSALoginOperation operation = new ATMPostCSALoginOperation(token, codeATM);

		return doLogin(operation.getAuthData(), mapping, form, request, response);
	}
}
