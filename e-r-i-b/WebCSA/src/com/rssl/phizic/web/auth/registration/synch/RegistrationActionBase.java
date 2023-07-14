package com.rssl.phizic.web.auth.registration.synch;

import com.rssl.auth.csa.front.exceptions.InterruptStageException;
import com.rssl.auth.csa.front.exceptions.UserAlreadyRegisteredInterruptStageException;
import com.rssl.auth.csa.front.exceptions.ValidateException;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.RegistrationOperationInfo;
import com.rssl.auth.csa.front.security.*;
import com.rssl.auth.security.SecurityManager;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.config.RegistrationAccessType;
import com.rssl.phizic.web.auth.AuthStageActionBase;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import com.rssl.phizic.web.tag.popup.PopupCollection;
import com.rssl.phizic.web.tag.popup.PopupInfo;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Самостоятельная регистрация синхронными запросами
 * @author niculichev
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationActionBase extends AuthStageActionBase
{
	private static final String SHOW_CAPTCHA_MESSAGE = "Для продолжения работы с системой закройте это окно и введите код, изображенный на картинке.";

	protected static final String REGISTRATION_CAPTCHA_SERVLET = "registrationCaptchaServlet";
	protected static final String LOGIN_REDIRECT = "login-form";

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(FrontSettingHelper.getRegistrationAccessType() != RegistrationAccessType.page)
			throw new UnsupportedOperationException();

		return super.doExecute(mapping, form, request, response);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward("start-reg");
	}

	public ActionForward commonCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		showPopup(request, null, SHOW_CAPTCHA_MESSAGE);
		return start(mapping, form, request, response);
	}

	protected void updateForm(OperationInfo operationInfo, AuthStageFormBase frm, boolean isFirstShowForm)
	{
		super.updateForm(operationInfo, frm, isFirstShowForm);

		RegistrationOperationInfo info = (RegistrationOperationInfo) operationInfo;
		if(info.getCurrentStage() == Stages.EXIST_REG)
		{
			showPopup(currentRequest(), "Registered", DEFAULT_ERROR_MESSAGE);
		}
		else if(info.getCurrentStage() == Stages.FINISH_REG)
		{
			if(!NewRegistrationSecurityManager.getIt().userTrusted(info.getKeyByUserInfo()))
			{
				CaptchaServlet.setActiveCaptha(currentRequest(), REGISTRATION_CAPTCHA_SERVLET);
				showPopup(currentRequest(), "CaptchaMessage", "");
			}
		}
	}

	protected ActionForward doBeginStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		try
		{
			return super.doBeginStage(mapping, frm, request, info);
		}
		catch(ValidateException e)
		{
			saveErrors(request, e.getMessages());
			return mapping.findForward(info.getCurrentName());
		}
		catch (FrontLogicException e)
		{
			saveError(request, e.getMessage());
			return mapping.findForward(info.getCurrentName());
		}
	}

	protected ActionForward doNextStage(ActionMapping mapping, AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		try
		{
			return super.doNextStage(mapping, frm, request, info);
		}
		catch (UserAlreadyRegisteredInterruptStageException e)
		{
			showPopup(request, "alreadyRegRestrict", e.getMessage());
			return mapping.findForward(info.getCurrentName());
		}
		catch (InterruptStageException e)
		{
			resetOperationInfo(request);
			showPopup(request, null, e.getMessage());
			return mapping.findForward(info.getCurrentName());
		}
	}

	protected ActionForward createCompleteForward(ActionMapping mapping, HttpServletRequest request)
	{
		showPopup(request, "Complete", "Вы успешно зарегистрированы в системе Сбербанк Онлайн.");
		return mapping.findForward(COMPLETE_FORWARD);
	}

	@Override
	protected OperationInfo getOperationInfo()
	{
		return new RegistrationOperationInfo(Stages.START_REG);
	}

	protected void showPopup(HttpServletRequest request, String id, String defaultMessage)
	{
		request.setAttribute(PopupCollection.SHOW_POPUP_ATTRIBUTE_NAME, new PopupInfo(id, defaultMessage));
	}

	protected boolean processSpecificCaptha(ActionForm form, HttpServletRequest request, String operationName) throws Exception
	{
		if(!"next".equals(operationName))
			return false;

		RegistrationOperationInfo operationInfo = null;
		try
		{
			operationInfo = (RegistrationOperationInfo) getOperationInfo(request);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}

		if(operationInfo == null)
			return false;

		if(operationInfo.getCurrentStage() != Stages.FINISH_REG)
			return false;

		SecurityManager manager = NewRegistrationSecurityManager.getIt();
		if(isRequiredShowCaptcha(request, REGISTRATION_CAPTCHA_SERVLET, manager, operationInfo.getKeyByUserInfo()))
		{
			CaptchaServlet.setActiveCaptha(request, REGISTRATION_CAPTCHA_SERVLET);
			return true;
		}
		else
		{
			CaptchaServlet.resetActiveCaptha(request, REGISTRATION_CAPTCHA_SERVLET);
			return false;
		}
	}

	protected boolean useFullErrorPage()
	{
		return true;
	}
}
