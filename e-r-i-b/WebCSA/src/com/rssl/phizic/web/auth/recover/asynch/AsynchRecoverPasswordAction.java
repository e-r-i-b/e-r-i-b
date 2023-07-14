package com.rssl.phizic.web.auth.recover.asynch;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.exceptions.LogableExceptionHandler;
import com.rssl.auth.csa.front.exceptions.SmsWasNotConfirmedInterruptStageException;
import com.rssl.auth.csa.front.exceptions.ValidateException;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.RecoverPasswordOperationInfo;
import com.rssl.auth.csa.front.operations.auth.RegistrationOperationInfo;
import com.rssl.auth.csa.front.operations.auth.ValidatePasswordOperation;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import com.rssl.phizic.web.auth.recover.RecoverPasswordForm;
import com.rssl.phizic.web.auth.registration.RegistrationForm;
import com.rssl.phizic.web.auth.registration.outerAsync.AsyncAuthStageActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен асинхронного восстановления пароля
 * @author tisov
 * @ created 30.01.14
 * @ $Author$
 * @ $Revision$
 */

public class AsynchRecoverPasswordAction extends AsyncAuthStageActionBase
{

	@Override
	protected OperationInfo getOperationInfo()
	{
		return new RecoverPasswordOperationInfo(Stages.PRE_CONFIRM);
	}

	@Override
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setAttribute(LogableExceptionHandler.IS_REQUEST_AJAX_ATTRIBUTE_NAME, true);
		if(!FrontSettingHelper.isAccessRecoverPassword())
			throw new UnsupportedOperationException("Восстановление пароля недоступно.");

		RecoverPasswordForm frm = (RecoverPasswordForm) form;
		frm.setHintDelay(ConfigFactory.getConfig(SelfRegistrationConfig.class).getHintDelay());
		return super.doExecute(mapping, form, request, response);
	}

	public ActionForward checkPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthStageFormBase frm = (AuthStageFormBase) form;
		if (FrontSettingHelper.getAsyncCheckingFieldsIsEnabled())
		{
			RecoverPasswordOperationInfo info = (RecoverPasswordOperationInfo) getOperationInfo(request);
			try
			{
				Map<String, Object> formData =
						checkFormData(RegistrationForm.CHECK_PASSWORD_FORM, getFieldValuesSource(frm));

				ValidatePasswordOperation operation = new ValidatePasswordOperation();
				operation.initialize(info, (String) formData.get(RegistrationForm.PASSWORD_FIELD_NAME));
				operation.execute();

				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_SUCCESS_STATE);
			}
			catch (ValidateException e)
			{
				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_FAIL_STATE);
				saveErrors(request, e.getMessages());
			}
			catch (FrontLogicException e)
			{
				// проверка идет в разрезе поля
				saveError(request, new ActionMessage(RegistrationForm.PASSWORD_FIELD_NAME, new ActionMessage(e.getMessage(), false)));
				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_FAIL_STATE);
			}
		}
		else
		{
			frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_SUCCESS_STATE);
		}
		return mapping.findForward(CHECK_FIELD_RESPONSE_NAME);
	}

	@Override
	protected ActionForward doBeginStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception {
		try
		{
			return super.doBeginStage(mapping, frm, request, info);
		}
		catch (SmsWasNotConfirmedInterruptStageException e) {
			frm.setRedirect("/async/page/recovery/timeout");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
	}

	@Override
	protected ActionForward doNextStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception {
		try
		{
			return super.doNextStage(mapping, frm, request, info);
		}
		catch (SmsWasNotConfirmedInterruptStageException e) {
			frm.setRedirect("/async/page/recovery/timeout");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
	}
}
