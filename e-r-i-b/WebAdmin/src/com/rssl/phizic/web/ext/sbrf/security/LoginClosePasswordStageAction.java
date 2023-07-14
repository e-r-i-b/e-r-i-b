package com.rssl.phizic.web.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.MultiInstanceSecurityService;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.password.NamePasswordValidator;
import com.rssl.phizic.security.password.WierdUserPasswordValidator;
import com.rssl.phizic.security.util.SecurityUtil;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.security.LoginPasswordStageAction;
import org.apache.struts.action.*;

import java.security.SecureRandom;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author eMakarov
 * @ created 05.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class LoginClosePasswordStageAction extends LoginPasswordStageAction
{
	public static final String LOGIN_SERVER_RND_KEY  = "com.rssl.phizic.LoginServerRnd";
	private SecurityService securityService = new SecurityService();
	private String TEXT_MESSAGE = "Доступ в систему запрещен администратором.";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CSAAdminGateConfig csaAdminConfig = ConfigFactory.getConfig(CSAAdminGateConfig.class);
		//если приложение работает в многоблочном режиме, то отправляем на аутентификацию в ЦСА Админ
		if (csaAdminConfig.isMultiBlockMode())
			return new ActionRedirect(csaAdminConfig.getLoginUrl());
		return super.start(mapping, form, request, response);
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FormProcessor<ActionMessages,?> processor = createFormProcessor(new RequestValuesSource(request), buildForm());

		ActionMessages errors = new ActionMessages();
		ActionForward forward;
		if(!processor.process())
		{
			errors.add(processor.getErrors());
			forward = forwardShow(mapping,form,request);
		}
		else
		{
			Map<String, Object> result = processor.getResult();

			String     userId     = (String) result.get("login");
			String     password   = (String) result.get("password");

			blockingByLongInactivity(userId, errors);
			if(errors.isEmpty())
			{
				password = password == null ? "" : password;
				NamePasswordValidator validator = createPasswordValidator(request, result);
				validateLoginPassword(validator, userId, password, errors);
			}

			if(errors.isEmpty())
				forward = null; //определяется следующим stage
			else
				forward = forwardShow(mapping,form,request);
		}

		saveErrors(request, errors);

		return forward;
	}

	private void blockingByLongInactivity(String userId, ActionMessages errors) throws SecurityDbException
	{
		CommonLogin login = securityService.getLogin(userId, MultiInstanceSecurityService.SCOPE_EMPLOYEE);
		if(login != null && SecurityUtil.needBlockByLongInactivity(login.getLastLogonDate()))
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(TEXT_MESSAGE, false));
		}
	}

	protected void fillParams(ActionForm form, HttpServletRequest request)
	{
		fillServerRandom(form);
	}

	/**
	 * генерирует случайную строку и добавляет ее в сессию и форму
	 * @param form  форма
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
		frm.setMaxLengthLogins(ConfigFactory.getConfig(BusinessSettingsConfig.class).getMaxLengthLogins());
	}

	/**
	 * Создает валидатор, для проверки пароля по алгоритмы СБРФ
	 * @param request запрос
	 * @param params Мап со значениями от пользователя
	 * @return валидатор
	 */
	protected NamePasswordValidator createPasswordValidator(HttpServletRequest request, Map<String, Object> params)
	{
		WierdUserPasswordValidator validator = new WierdUserPasswordValidator(SecurityService.SCOPE_EMPLOYEE);
		String clientRnd = (String)params.get("clientRandom");
		String serverRnd = getLoginServerRnd();

		validator.initialize(clientRnd,serverRnd);

		return validator;
	}

	public Form buildForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		//noinspection TooBroadScope
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("login");
		fieldBuilder.setDescription("Логин");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("password");
		fieldBuilder.setDescription("Пароль");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("clientRandom");
		fieldBuilder.setDescription("Пароль");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	/**
	 * @return Случайная последовательность выданная пользователю для аутентификации
	 */
	public String getLoginServerRnd()
	{
		return (String) StoreManager.getCurrentStore().restore(LoginClosePasswordStageAction.LOGIN_SERVER_RND_KEY);
	}

	/**
	 * @param randomString Случайная последовательность выданная пользователю для аутентификации
	 */
	public void setLoginServerRnd(String randomString)
	{
		StoreManager.getCurrentStore().save(LoginClosePasswordStageAction.LOGIN_SERVER_RND_KEY, randomString);
	}
}
