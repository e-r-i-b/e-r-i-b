package com.rssl.phizic.web.mapi.auth.register;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csamapi.operations.ConfirmRegistrationOperation;
import com.rssl.auth.csamapi.operations.FinishRegistrationOperation;
import com.rssl.auth.csamapi.operations.ShowCaptchaRegistrationOperation;
import com.rssl.auth.csamapi.operations.StartRegistrationOperation;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.CSAMAPIConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.auth.ActionFormBase;
import com.rssl.phizic.web.auth.LookupDispatchAction;
import com.rssl.phizic.web.mapi.auth.OperationalActionBase;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author osminin
 * @ created 03.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Регистрация мобильного приложения
 */
public class RegisterAppAction extends LookupDispatchAction
{
	private static final String FORWARD_SHOW_CAPTCHA = "showCaptcha";
	private static final String CAPTCHA_CODE_SESSION_KEY = "mobile_registration_captcha_code_key"; //ключ атрибута сессии, хранящего код капчи
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

    private static final String CARD_CHECK_MESSAGE = "Укажите 4 последние цифры карты";
    public static final String FORWARD_CARD_ERROR = "cardError";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>(3);
		map.put("register", "register");
		map.put("confirm", "confirm");
		map.put("checkCaptcha", "checkCaptcha");
		map.put("refreshCaptcha", "refreshCaptcha");
		map.put("createPIN", "createPIN");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(OperationalActionBase.FORWARD_START);
	}

	/**
	 * Регистрация приложения
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return forward
	 * @throws Exception
	 */
	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OperationalActionBase action;

		boolean useCaptcha = mobilePlatformService.isRegistrationUseCaptcha(request.getParameter(Constants.APP_TYPE_FIELD), request.getParameter(Constants.VERSION_FIELD));

        if (forgottenCardLastFourDigits(form, request))
        {
            return saveCardCheckError(mapping, request);
        }

		if (useCaptcha)
		{
			//требуется ввести капчу
			action = new OperationalActionBase()
			{
				protected Form getForm()
				{
					return RegisterAppForm.REGISTER_APP_FORM;
				}

				protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
				{
					ShowCaptchaRegistrationOperation operation = new ShowCaptchaRegistrationOperation();
					operation.initialize(data);
					return operation;
				}

				protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
				{
					updateFormOnCaptchaStage(operation, form);
				}

				protected String getForwardName()
				{
					return FORWARD_SHOW_CAPTCHA;
				}
			};
		}
		else
		{
			//капча не нужна - переходим к подтверждению регистрации смс-паролем
			action = new OperationalActionBase()
			{
				protected Form getForm()
				{
					return RegisterAppForm.REGISTER_APP_FORM;
				}

				protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
				{
					StartRegistrationOperation operation = new StartRegistrationOperation();
					operation.initialize(data);
					return operation;
				}

				protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
				{
					updateFormOnStartRegistration(operation, form);
				}
			};
		}
		return action.start(mapping, form, request, response);
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

    /**
	 * Проверка капчи
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return forward
	 * @throws Exception
	 */
	public ActionForward checkCaptcha(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OperationalActionBase action;

		HttpSession session = request.getSession();
		String savedCaptcha = (String) session.getAttribute(CAPTCHA_CODE_SESSION_KEY);
		String captcha = request.getParameter("captcha");
		//сразу же удаляем из сессии - даем всего одну попытку ввода
		session.removeAttribute(CAPTCHA_CODE_SESSION_KEY);
		boolean captchaPassed = StringHelper.isNotEmpty(savedCaptcha) && savedCaptcha.equalsIgnoreCase(captcha);

        if (forgottenCardLastFourDigits(form, request))
        {
            return saveCardCheckError(mapping, request);
        }

		if (captchaPassed)
		{
			//капча введена верно - переходим к подтверждению регистрации смс-паролем
			action = new OperationalActionBase()
			{
				protected Form getForm()
				{
					return RegisterAppForm.CHECK_CAPTCHA_REGISTRATION_FORM;
				}

				protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
				{
					StartRegistrationOperation operation = new StartRegistrationOperation();
					operation.initialize(data);
					return operation;
				}

				protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
				{
					updateFormOnStartRegistration(operation, form);
				}
			};
		}
		else
		{
			//капча неверная - показываем новую капчу
			action = new OperationalActionBase()
			{
				protected Form getForm()
				{
					return RegisterAppForm.CHECK_CAPTCHA_REGISTRATION_FORM;
				}

				protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
				{
					ShowCaptchaRegistrationOperation operation = new ShowCaptchaRegistrationOperation();
					operation.initialize(data);
					return operation;
				}

				protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
				{
					saveError(request, "Вы неправильно ввели код, изображенный на картинке. Попробуйте еще раз.");
					updateFormOnCaptchaStage(operation, form);
				}

				protected String getForwardName()
				{
					return FORWARD_SHOW_CAPTCHA;
				}
			};
		}
		return action.start(mapping, form, request, response);
	}

	/**
	 * Обновление капчи
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return forward
	 * @throws Exception
	 */
	public ActionForward refreshCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OperationalActionBase action = new OperationalActionBase()
		{
			protected Form getForm()
			{
				return FormBuilder.EMPTY_FORM;
			}

			protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
			{
				ShowCaptchaRegistrationOperation operation = new ShowCaptchaRegistrationOperation();
				operation.initialize(data);
				return operation;
			}

			protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
			{
				updateFormOnCaptchaStage(operation, form);
			}

			protected String getForwardName()
			{
				return FORWARD_SHOW_CAPTCHA;
			}
		};
		return action.start(mapping, form, request, response);
	}

	/**
	 * подтверждение регистрации приложения
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return forward
	 * @throws Exception
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OperationalActionBase action = new OperationalActionBase()
		{
			@Override
			protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
			{
			}

			@Override
			protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
			{
				ConfirmRegistrationOperation operation = new ConfirmRegistrationOperation();
				operation.initialize(data);
				return operation;
			}

			@Override
			protected Form getForm()
			{
				return RegisterAppForm.CONFIRM_REGISTRATION_FORM;
			}

			@Override
			public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
			{
				FormProcessor<ActionMessages, ?> formProcessor =
						FormHelper.newInstance(new RequestValuesSource(currentRequest()), getForm());

				if (!formProcessor.process())
				{
					saveErrors(request, formProcessor.getErrors());
					return mapping.findForward(FORWARD_SHOW);
				}

				Map<String, Object> result = formProcessor.getResult();
				try
				{
					if (isMockConfirmParams(request, (RegisterAppForm) form, result))
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
				return super.start(mapping, form, request, response);
			}

			private boolean isMockConfirmParams(HttpServletRequest request, RegisterAppForm frm, Map<String, Object> result) throws BusinessLogicException
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
		};

		return action.start(mapping, form, request, response);
	}

	/**
	 * Завершение регистрации приложения и автоматический вход
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return forward
	 * @throws Exception
	 */
	public ActionForward createPIN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OperationalActionBase action = new OperationalActionBase()
		{
			@Override
			protected void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException
			{
				FinishRegistrationOperation op = (FinishRegistrationOperation) operation;
				RegisterAppForm frm = (RegisterAppForm) form;

				frm.setHost(op.getHost());
				frm.setToken(op.getToken());
			}

			@Override
			protected Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException
			{
				FinishRegistrationOperation operation = new FinishRegistrationOperation();
				operation.initialize(data);
				return operation;
			}

			@Override
			protected Form getForm()
			{
				return RegisterAppForm.FINISH_REGISTRATION_FORM;
			}
		};

		return action.start(mapping, form, request, response);
	}

	private void updateFormOnStartRegistration(Operation operation, ActionFormBase form) throws FrontException
	{
		try
		{
			StartRegistrationOperation op = (StartRegistrationOperation) operation;
			RegisterAppForm frm = (RegisterAppForm) form;
			AuthParamsContainer container = op.getAuthParams();

			frm.setMguid(container.getParameter(RequestConstants.OUID_TAG));
			frm.setSmsPasswordLifeTime(Long.parseLong(container.getParameter(RequestConstants.TIMEOUT_TAG)));
			frm.setSmsPasswordAttemptsRemain(Long.parseLong(container.getParameter(RequestConstants.ATTEMPTS_TAG)));
			frm.setVersion(VersionNumber.fromString(container.getParameter(Constants.VERSION_FIELD)).toString());

			frm.setMinimumPINLength(Long.valueOf(ConfigFactory.getConfig(CSAMAPIConfig.class).getMobilePINLength()));
		}
		catch (MalformedVersionFormatException e)
		{
			throw new FrontException(e);
		}
	}

	private void updateFormOnCaptchaStage(Operation operation, ActionFormBase form)
	{
		ShowCaptchaRegistrationOperation op = (ShowCaptchaRegistrationOperation) operation;
		RegisterAppForm frm = (RegisterAppForm) form;

		HttpSession session = currentRequest().getSession();
		session.setAttribute(CAPTCHA_CODE_SESSION_KEY, op.getCaptchaCode());
		frm.setCaptchaBase64String(op.getCaptchaBase64String());
	}
}
