package com.rssl.phizic.web.client.registration;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareStringValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.forms.validators.password.RepeatOldPasswordValidator;
import com.rssl.phizic.operations.registration.SelfRegistrationOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.forms.validators.login.ExistsLoginValidator;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * Форма самостоятельной регистрации.
 *
 * @author bogdanov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class SelfRegistrationForm extends EditFormBase
{
	public static final String CONFIRM_PASSWORD_FIELD = "confirmPassword";
	public static final String LOGIN_FIELD_NAME = "login";
	public static final String PASSWORD_FIELD_NAME = "password";
	public static final String EMAIL_FIELD_NAME = "email";

	private boolean hardRegistrationMode;
	private boolean needShowEmailAddress;
	private ConfirmRequest confirmRequest;
	private ConfirmStrategy confirmStrategy;
	private ConfirmableObject confirmableObject;
	private ConfirmStrategyType confirmStrategyType;
	private boolean anotherStrategyAvailable = false;
	private int checkLoginMaxCount;
	private boolean loginExists;
	private String login;
	private String userOptionType;
	private String formMessage;

	private String operation;
	private Long reason;
	private String another;
	private String pageToken;
	private int hintDelay;
	private int minLoginLength;

	/**
	 * @return логин для проверки существования.
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @param login логин для проверки существования.
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return существует ли логин.
	 */
	public boolean getLoginExists()
	{
		return loginExists;
	}

	/**
	 * @param loginExists существует ли логин.
	 */
	public void setLoginExists(boolean loginExists)
	{
		this.loginExists = loginExists;
	}

	/**
	 * @return максимальное количество попыток проверки логина.
	 */
	public int getCheckLoginMaxCount()
	{
		return checkLoginMaxCount;
	}

	/**
	 * @param checkLoginMaxCount максимальное количество попыток проверки логина.
	 */
	public void setCheckLoginMaxCount(int checkLoginMaxCount)
	{
		this.checkLoginMaxCount = checkLoginMaxCount;
	}

	public boolean isAnotherStrategyAvailable()
	{
		return anotherStrategyAvailable;
	}

	/**
	 * @return запрос подтверждения.
	 */
	public ConfirmRequest getConfirmRequest()
	{
		return confirmRequest;
	}

	/**
	 * @param confirmRequest запрос подтверждения.
	 */
	public void setConfirmRequest(ConfirmRequest confirmRequest)
	{
		this.confirmRequest = confirmRequest;
	}

	/**
	 * @return стратегия подтверждения.
	 */
	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	/**
	 * @param confirmStrategy стратегия подтверждения.
	 */
	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	/**
	 * @return включен ли режим "жесткой" регистрации.
	 */
	public boolean getHardRegistrationMode()
	{
		return hardRegistrationMode;
	}

	/**
	 * @param hardRegistrationMode включен ли режим "жесткой" регистрации.
	 */
	public void setHardRegistrationMode(boolean hardRegistrationMode)
	{
		this.hardRegistrationMode = hardRegistrationMode;
	}

		/**
	 * @return сообщение клиенту на форме регистрации.
	 */
	public String getFormMessage()
	{
		return formMessage;
	}

	/**
	 * @param formMessage сообщение клиенту на форме регистрации.
	 */
	public void setFormMessage(String formMessage)
	{
		this.formMessage = formMessage;
	}

	/**
	 * @return необходимо ли отображать поле для ввода e-mail.
	 */
	public boolean getNeedShowEmailAddress()
	{
		return needShowEmailAddress;
	}

	/**
	 * @param needShowEmailAddress необходимо ли отображать поле для ввода e-mail.
	 */
	public void setNeedShowEmailAddress(boolean needShowEmailAddress)
	{
		this.needShowEmailAddress = needShowEmailAddress;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public void setAnotherStrategyAvailable(boolean anotherStrategyAvailable)
	{
		this.anotherStrategyAvailable = anotherStrategyAvailable;
	}

	public String getUserOptionType()
	{
		return userOptionType;
	}

	public void setUserOptionType(String userOptionType)
	{
		this.userOptionType = userOptionType;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public Long getReason()
	{
		return reason;
	}

	public void setReason(Long reason)
	{
		this.reason = reason;
	}

	public String getAnother()
	{
		return another;
	}

	public void setAnother(String another)
	{
		this.another = another;
	}

	public String getPageToken()
	{
		return pageToken;
	}

	public void setPageToken(String pageToken)
	{
		this.pageToken = pageToken;
	}

	public int getHintDelay()
	{
		return hintDelay;
	}

	public void setHintDelay(int customHintDelay)
	{
		this.hintDelay = customHintDelay;
	}

	public int getMinLoginLength()
	{
		return minLoginLength;
	}

	public void setMinLoginLength(int minLoginLength)
	{
		this.minLoginLength = minLoginLength;
	}

	public Form createForm(SelfRegistrationOperation operation) throws BusinessException
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(LOGIN_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Логин");
		fb.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите идентификатор или логин пользователя."),
				new PasswordStrategyValidator("csa_client_login"),
				new ExistsLoginValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(PASSWORD_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Пароль");
		fb.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите пароль."),
				new PasswordStrategyValidator("csa_client_password"),
				new RepeatOldPasswordValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(CONFIRM_PASSWORD_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Повторите пароль");
		fb.addValidators(new RequiredFieldValidator("Введите значение в поле Повторите пароль"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(EMAIL_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Электронная почта");
		if (operation.needEmailAddress())
			fb.addValidators(new RequiredFieldValidator("Введите значение в поле Электронная почта"));
		fb.addValidators(new RegexpFieldValidator("[A-Za-z0-9_\\.-]*@[A-Za-z0-9_\\.-]*\\.[A-Za-z0-9_]*", "Адрес электронной почты может состоять из букв латинского алфавита, цифр, дефиса  и символа подчеркивания, а также в нем должен присутствовать символ \"@\" и \".\"."));
		formBuilder.addField(fb.build());

		CompareStringValidator compareValidator = new CompareStringValidator(true, false);
		compareValidator.setMessage("Логин не должен совпадать с паролем");
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, LOGIN_FIELD_NAME);
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, PASSWORD_FIELD_NAME);
		formBuilder.addFormValidators(compareValidator);

		compareValidator = new CompareStringValidator(false, false);
		compareValidator.setMessage("Пароли не совпадают");
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, PASSWORD_FIELD_NAME);
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, CONFIRM_PASSWORD_FIELD);
		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}


}
