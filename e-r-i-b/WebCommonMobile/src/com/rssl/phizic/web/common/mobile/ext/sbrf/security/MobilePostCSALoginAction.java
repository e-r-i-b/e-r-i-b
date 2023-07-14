package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.context.Constants;
import com.rssl.phizic.operations.security.MobilePostCSALoginOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.common.client.ext.sbrf.security.LoginNamePasswordAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 05.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Экшен аутентификации пользователя МАПИ
 */
public class MobilePostCSALoginAction extends LoginNamePasswordAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobilePostCSALoginForm frm = (MobilePostCSALoginForm) form;
		String token = frm.getToken();

		if (StringHelper.isEmpty(token))
		{
			String message = "Токен аутентификации не задан";
			log.error(message);
			saveError(request, message);
			return mapping.findForward(FORWARD_API_ERROR);
		}

		MobilePostCSALoginOperation operation = new MobilePostCSALoginOperation(token);
		AuthData authData = operation.getAuthData();
		writeFindProfileEvent(authData, authData.getDeviceInfo());
		changeAccessPolicy(authData, request);
		setRSADataToSession(authData);
		//Обновление контекста аутентификаци
		operation.updateAuthenticationContext();

		return doLogin(authData, mapping, frm, request, response);
	}

	private void setRSADataToSession(AuthData authData)
	{
		WebContext.getCurrentRequest().getSession().setAttribute(Constants.CONTEXT_DATA_NAME, authData.getRsaData());
	}

	private void changeAccessPolicy(AuthData authData, HttpServletRequest request)
	{
		if (AuthorizedZoneType.PRE_AUTHORIZATION == authData.getAuthorizedZoneType())
		{
			//Если это завершающий шаг входа, а не регистрации, то устанавливаем политику ограниченного доступа
			tryStartAuthentication(authData.getAuthorizedZoneType().getActionPath(), request);
		}
	}
}
