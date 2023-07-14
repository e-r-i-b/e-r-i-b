package com.rssl.phizic.web.client.registration;

import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.NoMoreAttemptsRegistrationException;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.passwords.PasswordValidationConfig;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.SelfRegistrationHelper;
import com.rssl.phizic.business.payments.InvalidUserIdBusinessException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.UserIdClientTypes;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.registration.RegistrationFailureReasonOperation;
import com.rssl.phizic.operations.registration.SelfRegistrationOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.PageTokenUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн самосто€тельной регистрации.
 *
 * @author bogdanov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class SelfRegistrationAction extends OperationalActionBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String FORWARD_BACK = "Back";
	private static final String FORWARD_REASON_SUCCESS = "ReasonSuccess";
	private static final String FORWARD_COMPLETE = "Complete";
	protected static final String DEFAULT_CAPTCHA_SERVLET_NAME  = "selfRegistrationCaptchaServlet";
	public static final int NUM_ANOTHER_REASON = 5;

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.confirmSMS", "changeToSMS");
		map.put("button.confirmCard", "showCardsConfirm");
		map.put("confirmBySelectedCard","changeToCard");
		map.put("button.confirm", "confirm");
		map.put("cancelReason", "cancelReason");
		return map;
	}

	protected void updateForm(SelfRegistrationOperation operation, SelfRegistrationForm form) throws BusinessException
	{
		form.setHardRegistrationMode(SelfRegistrationHelper.getIt().getHardRegistrationMode());
		form.setNeedShowEmailAddress(operation.needEmailAddress());
		form.setConfirmRequest(operation.getRequest());
		form.setConfirmStrategy(operation.getConfirmStrategy());
		form.setConfirmStrategyType(operation.getStrategyType());
		form.setConfirmableObject(operation.getConfirmableObject());
		form.setCheckLoginMaxCount(SelfRegistrationHelper.getMaximinCheckLoginCount());
		form.setAnotherStrategyAvailable(operation.isAnotherStrategy());
		form.setUserOptionType(operation.getUserOptionType());
		form.setFormMessage(ConfigFactory.getConfig(SelfRegistrationConfig.class).getFormMessage());
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if ((AuthenticationContext.getContext().getLoginType() == LoginType.TERMINAL
				&& SelfRegistrationHelper.getIt().getRegistrationStatus() != RegistrationStatus.OFF
				&& SelfRegistrationHelper.getIt().getRegistrationMode() != RegistrationMode.OFF) || (AuthenticationContext.getContext().getLoginType() == LoginType.DISPOSABLE))
		{
			SelfRegistrationOperation op = createOperation(SelfRegistrationOperation.class);
			ConfirmationManager.sendRequest(op);
			SelfRegistrationForm selfRegistrationForm = (SelfRegistrationForm) form;
			updateForm(op, selfRegistrationForm);
            //вставл€ем по-умолчанию алиас, созданный клиентом в старой ÷—ј
			AuthenticationContext context = AuthenticationContext.getContext();
			String oldCsaAlias = context.getUserAlias();
			if (StringHelper.isNotEmpty(oldCsaAlias) && !oldCsaAlias.equals(context.getUserId()))
				selfRegistrationForm.setField("login", oldCsaAlias);

			SelfRegistrationConfig selfRegistrationConfig = ConfigFactory.getConfig(SelfRegistrationConfig.class);
			if (selfRegistrationConfig.isNewSelfRegistrationDesign())
			{
				PasswordValidationConfig passwordConfig = ConfigFactory.getConfig(PasswordValidationConfig.class);
				selfRegistrationForm.setMinLoginLength(passwordConfig.getMinimunLoginLength());
				selfRegistrationForm.setHintDelay(selfRegistrationConfig.getHintDelay());
				selfRegistrationForm.setPageToken(PageTokenUtil.getToken(request.getSession(false), true));
				if (SelfRegistrationHelper.getIt().needShowCaptcha())
					CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
				else
					CaptchaServlet.resetActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			}
			
			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_BACK);
	}

	public ActionForward cancelReason(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationForm frm = (SelfRegistrationForm) form;
		Long num = frm.getReason();
		String reason = null;
		if (num != null && num != NUM_ANOTHER_REASON)
			reason = StrutsUtils.getMessage("registration.reason." + num, "securityBundle");
		else
			reason = (String) frm.getAnother();

		if (StringHelper.isNotEmpty(reason))
		{
			RegistrationFailureReasonOperation registrationFailureReasonOperation = createOperation(RegistrationFailureReasonOperation.class);
			registrationFailureReasonOperation.initialize(reason);
			try
			{
				registrationFailureReasonOperation.save();
			}
			catch (Exception e)
			{
				log.error("во врем€ сохранени€ причины отказа произошла ошибка", e);
			}
		}

		SelfRegistrationHelper.getIt().incRegistrationShowedCount();

		return mapping.findForward(FORWARD_REASON_SUCCESS);
	}

	protected boolean validateForm(SelfRegistrationForm form, HttpServletRequest request, SelfRegistrationOperation operation) throws BusinessException
	{
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(form.getFields()), form.createForm(operation));
		if (!processor.process())
		{
			updateForm(operation, form);
			form.setField("confirmCardId", null);
			form.setField("confirmByCard", false);

			saveErrors(request, processor.getErrors(), form);
			return false;
		}

		return true;
	}

	protected void saveErrors(HttpServletRequest request, ActionMessages errors, SelfRegistrationForm form)
	{
		saveErrors(request, errors);
	}

	/**
	 * ќтобразить пользователю список карт по чекам с которых может быть подтверждена оперци€.
	 * ≈сли в списке только одна карта то список не отображаетс€ и переходим на следующий шаг.
	 */
	public ActionForward showCardsConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationOperation operation = createOperation(SelfRegistrationOperation.class);
		ConfirmationManager.sendRequest(operation);
		SelfRegistrationForm frm = (SelfRegistrationForm) form;
		frm.setField("confirmCardId", null);
		frm.setField("confirmByCard", false);
		frm.setField("confirmCard", null);
		frm.setField("confirmUserId", null);
		if (!validateForm(frm, request, operation)) {
			return mapping.findForward(FORWARD_START);
		}

		frm.setField("confirmCardId", null);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<CardLink> cardLinks = personData.getCards();
		List<CardLink> result = new ArrayList<CardLink>();
		for(CardLink link:cardLinks)
		{
			if(link.isMain() && link.isActive())
				result.add(link);
		}
		//если ошибок не было и только одна карта по коорой возможно подтверждение чеком не отображаем формы с выбором карт.
		if (result.size() == 1 && frm.getField("cardConfirmError") == null)
		{
			frm.setField("confirmCardId", result.get(0).getId().toString());
			try
			{
				return changeToCard(mapping, frm, request, response);
			}
			catch (SecurityLogicException ex)
			{
				updateForm(operation, frm);
				ConfirmHelper.saveConfirmErrors(operation.getRequest(), Arrays.asList(ex.getMessage()));
			}
		}

		operation.setUserStrategyType(ConfirmStrategyType.card);
		operation.resetConfirmStrategy();
		ConfirmationManager.sendRequest(operation);
		updateForm(operation, frm);
		frm.setField("confirmCards", result);
		frm.setField("confirmByCard",true);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward changeToCard( ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SelfRegistrationOperation operation = createOperation(SelfRegistrationOperation.class);
		SelfRegistrationForm form = (SelfRegistrationForm) frm;
		ConfirmRequest confirmRequest;
		try
		{
			form.setField("confirmByCard",true);
			operation.setStrategyType();
			addConfirmCardParameter(form, operation, true);
			operation.setUserStrategyType(ConfirmStrategyType.card);
			operation.startRegistration(
				(String) form.getField(SelfRegistrationForm.LOGIN_FIELD_NAME),
				(String) form.getField(SelfRegistrationForm.PASSWORD_FIELD_NAME),
				(String) form.getField(SelfRegistrationForm.EMAIL_FIELD_NAME)
			);
			confirmRequest = sendChangeToCardRequest(operation, form);
		}
		catch (NoMoreAttemptsRegistrationException ignore)
		{
			saveErrors(request, Arrays.asList("¬ы превысили количество попыток регистрации в системе. ѕожалуйста, повторите попытку позже."));
			form.setField("confirmCardId", null);
			form.setField("confirmByCard", false);
			return mapping.findForward(FORWARD_START);
		}
		catch (BackLogicException e)
		{
			form.setField("cardConfirmError", e.getMessage());
			form.setField("confirmCardId", null);//занул€ем значение, чтобы в случае отказа по одной карте можно было выбрать другую.
			return showCardsConfirm(mapping,form,request,response);
		}
		catch (BusinessLogicException e)
		{
			form.setField("cardConfirmError", e.getMessage());
			form.setField("confirmCardId", null);//занул€ем значение, чтобы в случае отказа по одной карте можно было выбрать другую.
			return showCardsConfirm(mapping,form,request,response);
		}
		confirmRequest.setPreConfirm(true);
		if(confirmRequest instanceof iPasPasswordCardConfirmRequest)
		{
			iPasPasswordCardConfirmRequest iPasReq = (iPasPasswordCardConfirmRequest) confirmRequest;
			iPasReq.setAdditionInfo(ConfirmHelper.getPasswordCardConfirmStrategyAdditionalInfo(iPasReq.getPasswordsLeft()));
		}
		if (confirmRequest.getAdditionInfo() != null)
		{
			for (String str : confirmRequest.getAdditionInfo())
			{
				if (!StringHelper.isEmpty(str))
					confirmRequest.addMessage(str);
			}
		}

		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.cap)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			saveErrors(request, Arrays.asList(operation.getWarning().getMessage()));
			form.setField("confirmCardId", null);
			form.setField("confirmByCard", false);
			form.setField("confirmCard", null);
			form.setField("confirmUserId", null);
			return start(mapping, frm, request, response);
		}

		updateForm(operation, form);
		saveOperation(request, operation);

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ¬ случае подтверждени€ по чеку. UserId в запросе дл€ iPasPasswordCardConfirmStrategy
	 * необходимо указывать дл€ выбранной карты клиента, полученный из ћЅ.
	 * @param frm - форма
	 * @param operation - текуща€ операци€
	 * @param useStoredValue - признак использовани€ при поиске значений из нашей Ѕƒ(кардлинка)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	private void addConfirmCardParameter(SelfRegistrationForm frm, SelfRegistrationOperation operation, boolean useStoredValue) throws BusinessException, BusinessLogicException
	{
		String confirmCardNumberId = (String) frm.getField("confirmCardId");
		//если передан идентифакатор карты подтверждени€, то подтверждать будем по ней.
		if (!StringHelper.isEmpty(confirmCardNumberId))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
			Pair<UserIdClientTypes, String> userId = ConfirmHelper.getUserIdByConfirmCard(Long.valueOf(confirmCardNumberId), login, useStoredValue);
			if (operation.getPreConfirm() == null)
			{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("confirmUserId", userId);
				operation.setPreConfirm(new PreConfirmObject(params));
			}
			else
				operation.getPreConfirm().getPreConfimParamMap().put("confirmUserId", userId);

			operation.doPreFraudControl();

			//устанавливаем поле "карта подтверждени€" дл€ отображени€ на странице подтверждени€
			frm.setField("confirmCard", PersonContext.getPersonDataProvider().getPersonData().getCard(Long.valueOf(confirmCardNumberId)).getNumber());
		}
	}

	private ConfirmRequest sendChangeToCardRequest(SelfRegistrationOperation operation, SelfRegistrationForm frm) throws BusinessException, BusinessLogicException
	{
		try
		{
			ConfirmationManager.sendRequest(operation);
		}
		catch (InvalidUserIdBusinessException ignore)
		{
			//в случае если по запросу вернулась ошибка неверного userId полученого по карте из базы.
			//јктуализируеим значение userId через ћЅ и отправл€ем запрос снова.
			addConfirmCardParameter(frm, operation, false);
			ConfirmationManager.sendRequest(operation);
		}
		finally
		{
			frm.setField("confirmCardId", null);//занул€ем значение, чтобы в случае отказа по одной карте можно было выбрать другую.	
		}
		return operation.getRequest();
	}

	private ActionForward preConfirm(ActionMapping mapping, SelfRegistrationForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationOperation op = createOperation(SelfRegistrationOperation.class);
		try
		{
			op.startRegistration(
					(String) form.getField(SelfRegistrationForm.LOGIN_FIELD_NAME),
					(String) form.getField(SelfRegistrationForm.PASSWORD_FIELD_NAME),
					(String) form.getField(SelfRegistrationForm.EMAIL_FIELD_NAME)
			);
			op.setStrategyType();
			op.setUserStrategyType(ConfirmStrategyType.sms);
			ConfirmationManager.sendRequest(op);
			op.getRequest().setPreConfirm(true);
			saveOperation(request, op);

			form.setField("confirmCardId", null);
			form.setField("confirmByCard", false);
			updateForm(op, form);

			CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
			callBackHandler.setAdditionalCheck();
			callBackHandler.setConfirmableObject(op.getConfirmableObject());
			callBackHandler.setOperationType(OperationType.REGISTRATION_OPERATION);
			callBackHandler.setLogin(op.getPersonData().getPerson().getLogin());
			PreConfirmObject preConfirmObject = op.preConfirm(callBackHandler);
			op.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
		}
		catch (SecurityLogicException ex)
		{
			ConfirmHelper.saveConfirmErrors(op.getRequest(), Arrays.asList(ex.getMessage()));
		}
		catch (NoMoreAttemptsRegistrationException ignore)
		{
			saveErrors(request, Arrays.asList("¬ы превысили количество попыток регистрации в системе. ѕожалуйста, повторите попытку позже."));
			return mapping.findForward(FORWARD_START);
		}
		catch (BackLogicException ex)
		{
			saveErrors(request, Arrays.asList(ex.getMessage()));
			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ѕодтверждение по —ћ—,
	 */
	public ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationOperation operation = createOperation(SelfRegistrationOperation.class);
		SelfRegistrationForm frm = (SelfRegistrationForm) form;
		frm.setField("confirmCardId", null);
		frm.setField("confirmByCard", false);
		frm.setField("confirmCard", null);
		frm.setField("confirmUserId", null);
		ConfirmationManager.sendRequest(operation);

		if (!validateForm(frm, request, operation))
			return mapping.findForward(FORWARD_START);

		return preConfirm(mapping, frm, request, response);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationOperation op = getOperation(request);
		SelfRegistrationForm form = (SelfRegistrationForm) frm;

		List<String> errors = ConfirmationManager.readResponse(op, new RequestValuesSource(request));
		updateForm(op, form);
		ConfirmHelper.clearConfirmErrors(op.getRequest());

		if (!errors.isEmpty())
		{
			ConfirmHelper.saveConfirmErrors(op.getRequest(), errors);
			return mapping.findForward(FORWARD_START);
		}
		try
		{
			op.confirm();
			SelfRegistrationHelper.getIt().setWindowShowed(true);
			resetOperation(request);

			return mapping.findForward(FORWARD_COMPLETE);
		}
		catch (BusinessLogicException ex)
		{
			op.getRequest().setPreConfirm(false);
			ConfirmHelper.saveConfirmErrors(op.getRequest(), Arrays.asList(ex.getMessage()));
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityLogicException ex)
		{
			op.getRequest().setPreConfirm(true);
			ConfirmHelper.saveConfirmErrors(op.getRequest(), Arrays.asList(ex.getMessage()));
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityException e) //упал сервис
	    {
		    op.getRequest().setPreConfirm(true);
		    log.error(e.getMessage(), e);
		    ConfirmHelper.saveConfirmErrors(op.getRequest(), Arrays.asList("—ервис временно недоступен, попробуйте позже"));
		    return mapping.findForward(FORWARD_START);
	    }
	}
}
