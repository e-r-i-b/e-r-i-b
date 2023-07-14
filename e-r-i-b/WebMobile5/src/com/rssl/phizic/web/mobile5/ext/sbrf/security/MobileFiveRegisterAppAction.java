package com.rssl.phizic.web.mobile5.ext.sbrf.security;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.security.GetAuthDataOperation;
import com.rssl.phizic.operations.security.MobileAppRegistrationOperation;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.mobile.ext.sbrf.security.MobileRegisterAppAction;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Регистрация в МП (для версии 5.0)
 * @author Jatsky
 * @ created 13.08.13
 * @ $Author$
 * @ $Revision$
 */

public class MobileFiveRegisterAppAction extends MobileRegisterAppAction
{

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>();
		keyMethodMap.put("register", "register");
		keyMethodMap.put("checkCaptcha", "checkCaptcha");
		keyMethodMap.put("refreshCaptcha", "refreshCaptcha");
		keyMethodMap.put("confirm", "confirm");
		return keyMethodMap;
	}

	@Override
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileFiveRegisterAppForm frm = (MobileFiveRegisterAppForm) form;
		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), getLoginForm());

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}

		Map<String, Object> result = formProcessor.getResult();
		try
		{
			if (isMockConfirmParams(request, frm, result))
			{
				return mapping.findForward(FORWARD_SHOW);
			}
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage(e.getMessage(), false));
			return mapping.findForward(FORWARD_SHOW);
		}

		return login(mapping, form, request, response);
	}

	@Override protected Form getLoginForm()
	{
		return MobileFiveRegisterAppForm.CONFIRM_MOBILE_REGISTRATION_FORM_OLD;
	}

	@Override protected GetAuthDataOperation createGetAuthDataOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
	{
		changeConfirmOperationAndNotificationToPush(AuthenticationContext.getContext());
		return new MobileAppRegistrationOperation(data);
	}

	/**
	 * Обновить пришедшие с формы данные
	 * @param data - данные, пришедшие с формы
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	@Override protected void updateFormData(Map<String, Object> data) throws BusinessException
	{
	}

    protected Form getMobileRegisterForm() {
        return MobileFiveRegisterAppForm.START_MOBILE_FIVE_REGISTRATION_FORM;
    }

    protected Form getMobileRegisterCheckCapchaForm()
    {
       return MobileFiveRegisterAppForm.CHECK_CAPTCHA_MOBILE_FIVE_REGISTRATION_FORM;
    }
}
