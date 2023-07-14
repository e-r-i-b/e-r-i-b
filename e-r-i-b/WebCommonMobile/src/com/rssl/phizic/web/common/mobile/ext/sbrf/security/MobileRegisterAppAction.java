package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.login.exceptions.MobileVersionException;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.business.login.exceptions.ResetMobileGUIDException;
import com.rssl.phizic.business.login.exceptions.WrongCodeConfirmException;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.security.*;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Pankin
 * @ created 20.08.2012
 * @ $Author$
 * @ $Revision$
 * Регистрация мобильного приложения
 */

public class MobileRegisterAppAction extends MobileLoginNamePasswordAction
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Web);
	private static final String FORWARD_SHOW_CAPTCHA = "ShowCaptcha";
	private static final String CAPTCHA_CODE_SESSION_KEY = "mobile_registration_captcha_code_key"; //ключ атрибута сессии, хранящего код капчи
	private static final String CONFIRM_PARAMS = "confirmParams";
	private static final String MGUID_PARAM = "mGUIDParam";
	private static final String ATTEMPTS_PARAM = "attemptsParam";
	private static final String TIMEOUT_PARAM = "timeoutParam";
	private static final String CREATION_TIME_PARAM = "creationTimeParam";
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	protected static final String FORWARD_SIM_ERROR = "simError";

    private static final String CARD_CHECK_MESSAGE = "Укажите 4 последние цифры карты";
    public static final String FORWARD_CARD_ERROR = "cardError";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>();
		keyMethodMap.put("register", "register");
		keyMethodMap.put("checkCaptcha", "checkCaptcha");
		keyMethodMap.put("refreshCaptcha", "refreshCaptcha");
		keyMethodMap.put("confirm", "confirm");
		keyMethodMap.put("createPIN", "createPIN");
		return keyMethodMap;
	}

	//первый шаг регистрации
	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RegisterAppForm frm = (RegisterAppForm) form;

		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), getMobileRegisterForm());

        if (forgottenCardLastFourDigits(form, request))
        {
            return saveCardCheckError(mapping, request);
        }

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}

		try
		{
			Map<String, Object> data = formProcessor.getResult();
			checkDeviceIdParameter(data);

			String appType = (String) data.get("appType");
			String version = (String) data.get("version");
			if (mobilePlatformService.isRegistrationUseCaptcha(appType, version))
			{
				return forwardShowCaptchaStage(mapping, form, data);
			}
			else
			{
				return forwardShowConfirmStage(mapping, frm, data);
			}
		}
		catch (ResetMobileGUIDException e)
		{
			log.error("Приложение не зарегистрировано.", e);
			return mapping.findForward(FORWARD_RESET_MGUID);
		}
		catch (MobileVersionException e)
		{
			saveError(request, new ActionMessage(e.getMessage(), false));
			return mapping.findForward(FORWARD_API_ERROR);
		}
		catch (MobileBankRegistrationNotFoundException e)
		{
			String message = getResourceMessage("securityBundle", "mobile.version.not.found.mobile.registration.error.message");
			return errorForward(mapping, request, message, e);
		}
		catch (FailureIdentificationException ignore)
		{
			//String message = getResourceMessage("securityBundle", "mobile.version.failed.identification.error.message");
			//return errorForward(mapping, request, message, e);
			//в случае несуществующего логина возвращаем фейковую страницу как для существующего (сделано по запросу CHG059905).
			log.debug("Логин не найден. Отправляем в ответ фейковую страницу.");
			String mGUID = RandomHelper.rand(32, "1234567890ABCDEF");
			createMockConfirmParameters(frm, mGUID);
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (SendSmsMessageException e)
		{
			return errorForward(mapping, request, e.getMessage(), e);
		}
		catch (CheckIMSIException e)
		{
			if(MobileApiUtil.isMobileApiGE(MobileAPIVersions.V8_00))
				return getSMSErrorForward(mapping, request, e.getPhones(), e);
			return errorForward(mapping, request, e.getMessage(), e);
		}
		catch (BackLogicException e)
		{
			return errorForward(mapping, request, e.getMessage(), e);
		}
		catch (BusinessException e)
		{
			return errorForward(mapping, request, Constants.COMMON_EXCEPTION_MESSAGE, e);
		}
		catch (BusinessLogicException e)
		{
			return errorForward(mapping, request, e.getMessage(), e);
		}
	}

    protected Form getMobileRegisterForm()
    {
       return RegisterAppForm.START_MOBILE_REGISTRATION_FORM;
    }

    protected Form getMobileRegisterCheckCapchaForm()
    {
       return RegisterAppForm.CHECK_CAPTCHA_MOBILE_REGISTRATION_FORM;
    }

	protected ActionForward getSMSErrorForward(ActionMapping mapping, HttpServletRequest request, String phones, Throwable e)
	{
		log.error("При регистрации в мобильном приложении по номеру телефона " + phones + " обнаружена смена SIM-карты", e);
		String message = "При отправке SMS обнаружена смена SIM-карты";
		saveError(request, new ActionMessage(message, false));
		return mapping.findForward(FORWARD_SIM_ERROR);
	}

	private void createMockConfirmParameters(RegisterAppForm frm, String mGUID) throws BusinessException
	{
		Random rand = new Random();
		Long timeout = ConfigFactory.getConfig(MobileApiConfig.class).getConfirmationTimeout() - rand.nextInt(2) - 2L;
		Long attempts = Long.valueOf(ConfigFactory.getConfig(MobileApiConfig.class).getConfirmationAttemptsCount());

		frm.setMguid(mGUID);
		frm.setSmsPasswordLifeTime(timeout);
		frm.setSmsPasswordAttemptsRemain(attempts);
		frm.setMinimumPINLength(Long.valueOf(ConfigFactory.getConfig(SecurityConfig.class).getMobilePINLength()));

		Map<String, Object> info = new HashMap<String, Object>();
		info.put(MGUID_PARAM, mGUID);
		info.put(ATTEMPTS_PARAM, attempts);
		info.put(TIMEOUT_PARAM, timeout);
		info.put(CREATION_TIME_PARAM, System.currentTimeMillis());

		Store store = StoreManager.getCurrentStore();
		synchronized (store)
		{
			store.save(CONFIRM_PARAMS, info);
		}
	}

	//проверка капчи
	public ActionForward checkCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RegisterAppForm frm = (RegisterAppForm) form;

		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), getMobileRegisterCheckCapchaForm());

        if (forgottenCardLastFourDigits(form, request))
        {
            return saveCardCheckError(mapping, request);
        }


		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}

		try
		{
			HttpSession session = request.getSession();
			String savedCaptcha = (String) session.getAttribute(CAPTCHA_CODE_SESSION_KEY);
			//сразу же удаляем из сессии - даем всего одну попытку ввода
			session.removeAttribute(CAPTCHA_CODE_SESSION_KEY);

			String captcha = (String) formProcessor.getResult().get("captcha");

			if (StringHelper.isNotEmpty(savedCaptcha) && savedCaptcha.equalsIgnoreCase(captcha))
			{
				//капча введена верно - переходим к подтверждению регистрации смс-паролем
				return forwardShowConfirmStage(mapping, frm, formProcessor.getResult());
			}
			else
			{
				//капча неверная - показываем новую капчу
				saveError(request, "Вы неправильно ввели код, изображенный на картинке. Попробуйте еще раз.");
				return forwardShowCaptchaStage(mapping, form, formProcessor.getResult());
			}
		}
		catch (FailureIdentificationException ignore)
		{
			//в случае несуществующего логина возвращаем фейковую страницу как для существующего (сделано по запросу CHG059905).
			log.debug("Логин не найден. Отправляем в ответ фейковую страницу.");
			String mGUID = RandomHelper.rand(32, "1234567890ABCDEF");
			createMockConfirmParameters(frm, mGUID);
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BackLogicException e)
		{
			return errorForward(mapping, request, e.getMessage(), e);
		}
		catch (ResetMobileGUIDException e)
		{
			log.error("Приложение не зарегистрировано.", e);
			return mapping.findForward(FORWARD_RESET_MGUID);
		}
		catch (MobileVersionException e)
		{
			saveError(request, new ActionMessage(e.getMessage(), false));
			return mapping.findForward(FORWARD_API_ERROR);
		}
		catch (BusinessException e)
		{
			return errorForward(mapping, request, Constants.COMMON_EXCEPTION_MESSAGE, e);
		}
		catch (BusinessLogicException e)
		{
			return errorForward(mapping, request, e.getMessage(), e);
		}
	}

	protected ActionForward errorForward(ActionMapping mapping, HttpServletRequest request, String message, Throwable e)
	{
		log.error(message, e);
		saveError(request, new ActionMessage(message, false));
		return mapping.findForward(FORWARD_SHOW);
	}

	//обновление капчи
	public ActionForward refreshCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return forwardShowCaptchaStage(mapping, form, null);
	}

	//подтверждение одноразовым паролем
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RegisterAppForm frm = (RegisterAppForm) form;

		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), RegisterAppForm.CONFIRM_MOBILE_REGISTRATION_FORM);

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}

		try
		{
			Map<String, Object> result = formProcessor.getResult();
			if (isMockConfirmParams(request, frm, result))
			{
				return mapping.findForward(FORWARD_SHOW);
			}
			new ConfirmMobileRegistrationOperation(result);
			updateConfirmForm(frm, result);
		}
		catch (ResetMobileGUIDException e)
		{
			log.error("Приложение не зарегистрировано.", e);
			return mapping.findForward(FORWARD_RESET_MGUID);
		}
		catch (MobileVersionException e)
		{
			saveError(request, new ActionMessage(e.getMessage(), false));
			return mapping.findForward(FORWARD_API_ERROR);
		}
		catch (RegistrationErrorException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage("error.registration.failed"));
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (WrongCodeConfirmException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage(e.getMessage(), false));

			frm.setSmsPasswordLifeTime(Long.valueOf(e.getTime()));
			frm.setSmsPasswordAttemptsRemain(Long.valueOf(e.getAttempts()));

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage(Constants.COMMON_EXCEPTION_MESSAGE, false));
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage(e.getMessage(), false));
			return mapping.findForward(FORWARD_SHOW);
		}
		return mapping.findForward(FORWARD_SHOW);
	}

	protected boolean isMockConfirmParams(HttpServletRequest request, RegisterAppForm frm, Map<String, Object> result) throws BusinessLogicException
	{
		Store store = StoreManager.getCurrentStore();
		Map<String, Object> mockConfirmParams = (Map<String, Object>) store.restore(CONFIRM_PARAMS);
		if (mockConfirmParams != null && StringHelper.equalsNullIgnore(
				(String) mockConfirmParams.get(MGUID_PARAM), (String) result.get("mGUID")))
		{
			Long attempts = (Long) mockConfirmParams.get(ATTEMPTS_PARAM);
			Long timeout = (Long) mockConfirmParams.get(TIMEOUT_PARAM);
			timeout = timeout - (System.currentTimeMillis() - (Long) mockConfirmParams.get(CREATION_TIME_PARAM)) / 1000;
			if (attempts > 0 && timeout > 0)
			{
				attempts--;
				saveError(request, new ActionMessage(ConfigFactory.getConfig(DocumentConfig.class).getInvalidConfirmCodeRequest(), false));
				frm.setSmsPasswordLifeTime(timeout);
				frm.setSmsPasswordAttemptsRemain(attempts);
				mockConfirmParams.put(ATTEMPTS_PARAM, attempts);
				return true;
			}
			throw new BusinessLogicException(BackLogicException.DEFAULT_ERROR_MESSAGE);
		}
		return false;
	}

	@Override
	protected Form getLoginForm()
	{
		return RegisterAppForm.SET_PIN_MOBILE_REGISTRATION_FORM;
	}

	@Override
	protected GetAuthDataOperation createGetAuthDataOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
	{
		changeConfirmOperationAndNotificationToPush(AuthenticationContext.getContext());
		return new MobileAppFinishRegistrationOperation(data);
	}

	private void updateRegistrationForm(RegisterAppForm frm, AuthParamsContainer container, Map<String, Object> data) throws BusinessException
	{
		try
		{
			frm.setMguid(container.getParameter(RequestConstants.OUID_TAG));
			frm.setSmsPasswordLifeTime(Long.parseLong(container.getParameter(RequestConstants.TIMEOUT_TAG)));
			frm.setSmsPasswordAttemptsRemain(Long.parseLong(container.getParameter(RequestConstants.ATTEMPTS_TAG)));
			frm.setVersion(VersionNumber.fromString((String) data.get("version")).toString());

			SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
			frm.setMinimumPINLength(Long.valueOf(securityConfig.getMobilePINLength()));
		}
		catch (MalformedVersionFormatException e)
		{
			throw new BusinessException(e);
		}
	}

	@Override
	protected void updateAuthenticationContext(AuthenticationContext context, Map<String, Object> data, AuthData authData) throws BusinessException
	{
		super.updateAuthenticationContext(context, data, authData);
		context.setMobileAppScheme(authData.getMobileAppScheme());
	}

	private void updateConfirmForm(RegisterAppForm frm, Map<String, Object> data) throws BusinessException
	{
		try
		{
			frm.setVersion(VersionNumber.fromString((String) data.get("version")).toString());
		}
		catch (MalformedVersionFormatException e)
		{
			throw new BusinessException(e);
		}
	}

	//Установка PIN-кода для входа в мобильное приложение
	public ActionForward createPIN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return login(mapping, form, request, response);
	}

	private ActionForward forwardShowCaptchaStage(ActionMapping mapping, ActionForm form, Map<String, Object> result) throws BusinessException, BusinessLogicException
	{
		RegisterAppForm frm = (RegisterAppForm) form;
		ShowCaptchaMobileRegistrationOperation operation = new ShowCaptchaMobileRegistrationOperation(result);

		HttpSession session = currentRequest().getSession();
		session.setAttribute(CAPTCHA_CODE_SESSION_KEY, operation.getCaptchaCode());
		frm.setCaptchaBase64String(operation.getCaptchaBase64String());
		return mapping.findForward(FORWARD_SHOW_CAPTCHA);
	}

	private ActionForward forwardShowConfirmStage(ActionMapping mapping, RegisterAppForm frm, Map<String, Object> result) throws BusinessLogicException, BusinessException, BackLogicException, MalformedVersionFormatException
	{
		StartMobileRegistrationOperation operation = new StartMobileRegistrationOperation(result);
		updateRegistrationForm(frm, operation.getAuthParams(), result);
		return mapping.findForward(FORWARD_SHOW);
	}

    private ActionForward saveCardCheckError(ActionMapping mapping, HttpServletRequest request)
    {
        ActionMessages actionErrors = new ActionMessages();
        actionErrors.add(CARD_CHECK_MESSAGE, new ActionMessage(CARD_CHECK_MESSAGE, false));
        saveErrors(request, actionErrors);
        return mapping.findForward(FORWARD_CARD_ERROR);
    }

    private boolean forgottenCardLastFourDigits(ActionForm form, HttpServletRequest request) throws TemporalDocumentException
    {
        RegisterAppForm frm = (RegisterAppForm) form;

        if (!ConfigFactory.getConfig(MobileApiConfig.class).isMobileApiRegistrationCheckCardNum())
			return false;

        String version = frm.getVersion();
        String card = request.getParameter("card");

        if (StringHelper.isEmpty(card))
        {
            try
            {
                VersionNumber vNumber = VersionNumber.fromString(version);
                if (vNumber.ge(MobileAPIVersions.V8_00))
                {
                    return true;
                }
            }
            catch (MalformedVersionFormatException e)
            {
                throw new TemporalDocumentException(e);
            }
        }

        return false;
    }
}
