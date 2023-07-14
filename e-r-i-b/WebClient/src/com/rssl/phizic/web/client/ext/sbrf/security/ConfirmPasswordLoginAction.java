package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.operations.passwords.ChangePersonPasswordOperation;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.*;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.security.ConfirmLoginActionBase;
import com.rssl.phizic.web.security.LoginMessagesHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import org.apache.struts.action.*;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author eMakarov
 * @ created 05.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmPasswordLoginAction extends ConfirmLoginActionBase
{
	public static final String LOGIN_SERVER_RND_KEY  = "com.rssl.phizic.LoginServerRnd";

	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
	    map.put("button.confirmByPassword", "next");
		map.put("button.getPassword", "generateNewPassword");
        return map;
	}

	/**
	 * генерирует случайную строку и добавляет ее в сессию и форму
	 * @param form
	 */
	private void fillServerRandom(ActionForm form)
	{
		//создать случайное число
		byte[] serverRandom = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(serverRandom);

		String randomString = StringUtils.toHexString(serverRandom);
		setLoginServerRnd(randomString);

		LoginClosePasswordStageForm frm = (LoginClosePasswordStageForm) form;
		frm.setServerRandom(randomString);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Login login = (Login)getAuthenticationContext().getLogin();

		SecurityService securityService = new SecurityService();
		UserPassword userPassword = securityService.getPassword(login, null);

		if (userPassword.getNeedChange())
		{
			try
			{
				createNewPassword(login, userPassword);
			}
			catch (Exception e)
			{
				ActionMessages errors  = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(SecurityMessages.translateException(e), false));
				saveErrors(request.getSession(), errors);
			}
		}

		return forwardShow(mapping, form, request);
	}

	private ActionForward forwardShow(ActionMapping mapping, ActionForm form, HttpServletRequest request)
	{
		fillServerRandom(form);
		return mapping.findForward(FORWARD_SHOW);
	}

	private void createNewPassword(Login login, UserPassword userPassword) throws BusinessException, BusinessLogicException
	{
		ChangePersonPasswordOperation operation = createOperation(ChangePersonPasswordOperation.class);
		operation.initialize();

		UserPasswordService userPasswordService = new UserPasswordService();
		PasswordStrategyValidator pswValidator = new PasswordStrategyValidator();
		pswValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "client");
		String newPass = userPasswordService.generate(pswValidator);
		operation.setNewPassword(newPass);
		userPassword.setValue(newPass.toCharArray());

		try
		{
			operation.changePassword();

			MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
			MessageComposer messageComposer = new MessageComposer();
			IKFLMessage message = messageComposer.buildSimplePasswordMessage(login, newPass);
			messagingService.sendSms(message);
		}
		catch (PasswordValidationException ex)
		{
			throw new BusinessException("Ошибка при отправке пароля", ex);
		}
		catch (BusinessException ex)
		{
			throw new BusinessException("Ошибка при отправке пароля", ex);
		}
		catch (IKFLMessagingException ex)
		{
			throw new BusinessException("Ошибка при отправке пароля", ex);
		}
		catch (IKFLMessagingLogicException ex)
		{
			throw new BusinessLogicException("Ошибка при отправке пароля", ex);
		}
	}

	public ActionForward next ( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		Calendar start = GregorianCalendar.getInstance();
		Login    login = (Login)getAuthenticationContext().getLogin();

		LoginClosePasswordStageForm frm = (LoginClosePasswordStageForm)form;

		FormProcessor<ActionMessages,?> processor = createFormProcessor(new RequestValuesSource(request), buildForm());
		if(!processor.process())
		{
			ActionMessages errors = new ActionMessages();
			errors.add(processor.getErrors());
			return forwardShow(mapping, form, request);
		}
		UserPasswordValidator userPasswordValidator = createPasswordValidator(request, processor.getResult());//new UserPasswordValidator();

		if (frm.getPassword() == null)
		{
			// пришел пустой пароль с формы
			ActionMessages errors  = new ActionMessages();
			String message = "Ошибка регистрации. Введите пароль";
			writeToOperationLog(start, message);

			errors.add("login", new ActionMessage( message, false) );
			saveErrors(request.getSession(), errors);

			return forwardShow(mapping, form, request);
		}

		try
		{
			userPasswordValidator.validateLoginInfo(login.getUserId(), frm.getPassword().toCharArray());
		}
		catch (BlockedException e )
		{
			LoginMessagesHelper helper = new LoginMessagesHelper();
			ActionMessages errors  = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, helper.getLoginBlockMessage(login));
			saveErrors(request.getSession(), errors);
			return forwardShow(mapping, form, request);
		}
		catch (SecurityLogicException e)
		{
			writeToOperationLog(start, e.getMessage());
			ActionMessages errors  = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request.getSession(), errors);
			return forwardShow(mapping, form, request);
		}

		completeStage();

		return forwardShow(mapping, form, request);
	}

	public ActionForward generateNewPassword ( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		Login login = (Login)getAuthenticationContext().getLogin();
		ChangePersonPasswordOperation operation = createOperation(ChangePersonPasswordOperation.class); // проверяем может ли клиент менять пароль

		SecurityService securityService = new SecurityService();
		UserPassword userPassword = securityService.getPassword(login, null);
		userPassword.setNeedChange(Boolean.TRUE);
		SimpleService simpleService = new SimpleService();
		simpleService.update(userPassword); // устанавливаем признак необходимости смены пароля

		return start(mapping, form, request, response);
	}

	/**
	 * @return Случайная последовательность выданная пользователю для аутентификации
	 */
	public String getLoginServerRnd()
	{
		return (String) StoreManager.getCurrentStore().restore(LOGIN_SERVER_RND_KEY);
	}

	/**
	 * @param randomString Случайная последовательность выданная пользователю для аутентификации
	 */
	public void setLoginServerRnd(String randomString)
	{
		StoreManager.getCurrentStore().save(LOGIN_SERVER_RND_KEY, randomString);
	}

	private UserPasswordValidator createPasswordValidator(HttpServletRequest request, Map<String, Object> params)
	{
		WierdUserPasswordValidator validator = new WierdUserPasswordValidator();
		String clientRnd = (String)params.get("clientRandom");
		String serverRnd = getLoginServerRnd();

		validator.initialize(clientRnd, serverRnd);

		return validator;
	}

	public Form buildForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		//noinspection TooBroadScope
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("password");
		fieldBuilder.setDescription("Пароль");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accessType");
		fieldBuilder.setDescription("Тип доступа");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("clientRandom");
		fieldBuilder.setDescription("Пароль");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
