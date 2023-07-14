package com.rssl.phizic.web.auth.registration.asynch;

import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.RegistrationOperationInfo;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.config.RegistrationAccessType;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.auth.AuthStageActionBase;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн самостоятельной регистрации
 * @author niculichev
 * @ created 17.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationAction extends AuthStageActionBase
{
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(FrontSettingHelper.getRegistrationAccessType() != RegistrationAccessType.popup)
			throw new UnsupportedOperationException("Процедура регистрации через всплывающие окна запрещена");

		if(!FrontSettingHelper.isAccessInternalRegistration())
			throw new UnsupportedOperationException("Регистрация недоступна");

		return super.doExecute(mapping, form, request, response);
	}

	protected OperationInfo getOperationInfo()
	{
		return new RegistrationOperationInfo(Stages.PRE_CONFIRM);
	}

	protected void updateForm(OperationInfo operationInfo, AuthStageFormBase frm, boolean isFirstShowForm)
	{
		super.updateForm(operationInfo, frm, isFirstShowForm);
		// для формы подтверждения выводим время и попытки
		Map<String, Object> confirmParams = operationInfo.getConfirmParams();
		if(operationInfo.getCurrentStage() == Stages.CONFIRM && isFirstShowForm && confirmParams.get("Timeout") != null)
		{
			saveMessage(currentRequest(), StrutsUtils.getMessage(
					"message.confirm.timeout", "commonBundle", confirmParams.get("Timeout")));
		}

	}
}
