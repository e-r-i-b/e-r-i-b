package com.rssl.phizic.web.auth.recover;

import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.RecoverPasswordOperationInfo;
import com.rssl.auth.csa.front.security.LoginSecurityManager;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.auth.AuthStageActionBase;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.rssl.auth.security.SecurityManager;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экш самостоятельного восстановления пароля
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class RecoverPasswordAction extends AuthStageActionBase
{
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!FrontSettingHelper.isAccessRecoverPassword())
			throw new UnsupportedOperationException("Восстановление пароля недоступно.");

		return super.doExecute(mapping, form, request, response);
	}

	protected OperationInfo getOperationInfo()
	{
		return new RecoverPasswordOperationInfo(Stages.PRE_CONFIRM);
	}

	protected void updateForm(OperationInfo operationInfo, AuthStageFormBase frm, boolean isFirstShowForm)
	{
		super.updateForm(operationInfo, frm, isFirstShowForm);
		Map<String, Object> confirmParams = operationInfo.getConfirmParams();
		//  если есть что отобразить для формы подтверждения
		if(operationInfo.getCurrentStage() == Stages.CONFIRM && isFirstShowForm && confirmParams.get("Timeout") != null)
		{
			saveMessage(currentRequest(), StrutsUtils.getMessage(
					"message.confirm.timeout", "commonBundle", confirmParams.get("Timeout")));
		}
	}

	protected boolean processSpecificCaptha(ActionForm form, HttpServletRequest request, String operationName) throws Exception
	{
		RecoverPasswordForm frm = (RecoverPasswordForm) form;
		SecurityManager manager = LoginSecurityManager.getIt();

		if("begin".equals(operationName)
				&& isRequiredShowCaptcha(request, DEFAULT_CAPTCHA_SERVLET_NAME, manager, frm.getLogin(), isNeedConstantTuringTest()))
		{
			CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			return true;
		}
		else
		{
			CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			return false;
		}
	}

	public ActionForward specificCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(TURING_TEST_FORWARD);
	}
}
