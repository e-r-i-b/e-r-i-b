package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 11.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class UserLoginNotificationAction extends SBRFLoginActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		throw new UnsupportedOperationException("Операция не поддерживается");

//		UserLogonNotificationAction userLogonNotificationAction = new UserLogonNotificationAction();
//		userLogonNotificationAction.execute(AuthenticationManager.getContext(request));
//		return continueStage(mapping, request);
	}

	protected String getSkinUrl(ActionForm form) throws BusinessException
	{
		//Скин нам здесь не нужен
		return "";
	}
}
