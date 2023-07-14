package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.authgate.csa.CSAConfig;
import com.rssl.phizic.authgate.csa.CSARefreshConfig;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.jmx.MobileBankConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigDecimal;
import java.util.*;

import static com.rssl.phizic.config.csa.Constants.*;
import static com.rssl.phizic.self.registration.SelfRegistrationConfig.*;

/**
 * Форма для редактирования параметров внешней системы.
 *
 * @author bogdanov
 * @ created 11.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConcreteExternalSystemConfigForm extends EditPropertiesFormBase
{
	/**
	 * логические формы.
	 */
	private static final Map<ExternalSystemType, Form> systemToForm = new HashMap<ExternalSystemType, Form>();

	private static final List<ServiceSettings> esbServices = new ArrayList<ServiceSettings>(18);

	static
	{
		esbServices.add(new ServiceSettings("AccountToAccountTransfer",                  ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("AccountToCardTransfer",                     ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("AccountToIMATransfer",                      ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardToAccountTransfer",                     ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("IMAToAccountTransfer",                      ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardToAccountPhizIntraBankTransfer",        ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardToAccountPhizOurBankTransfer",          ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("AccountToAccountIntraBankPaymentLongOffer", ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("AccountToAccountOurBankPaymentLongOffer",   ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("RefuseLongOffer",                           ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("StopDepositOperationClaim",                 ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("BlockingCardClaim",                         ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardToCardTransfer",                        ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardToCardIntraBankTransfer",               ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardToCardIntraBankTransferCommission",     ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardToCardOurBankTransfer",                 ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardToCardOurBankTransferCommission",       ServiceSettings.Mode.modeAndTimeout));
		esbServices.add(new ServiceSettings("CardPaymentSystemPayment",                  ServiceSettings.Mode.serviceProvider));

		systemToForm.put(ExternalSystemType.ESB, createFormESB());
		systemToForm.put(ExternalSystemType.IPAS, createFormiPas());
		systemToForm.put(ExternalSystemType.CSA, createFormCSA());
		systemToForm.put(ExternalSystemType.CSA_FRONT, createFormCSA_FRONT());
		systemToForm.put(ExternalSystemType.CSA_BACK, createFormCSA_BACK());
		systemToForm.put(ExternalSystemType.RSA, createFormRSA());
	}

	private String systemName;

	/**
	 * @return тип внешней системы.
	 */
	public String getSystemName()
	{
		return systemName;
	}

	/**
	 * @param systemName тип внешней системы.
	 */
	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}

	/**
	 * @return отображаемое имя внешней системы.
	 */
	public String getDisplayedSystemName()
	{
		return ExternalSystemType.valueOf(systemName).getDescription();
	}

	public Form getForm()
	{
		return systemToForm.get(ExternalSystemType.valueOf(systemName));
	}

	/**
	 * @return список специфичных сервисов шины
	 */
	public List<ServiceSettings> getEsbServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return esbServices;
	}

	private static Form createFormESB()
	{
		FormBuilder fb = new FormBuilder();
		//Поле com.rssl.gate.connection.timeout
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут соединения с КСШ");
		fieldBuilder.setName(ESBEribConfig.ESB_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут соединения с КСШ\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		FieldValidator modeValidator = new ChooseValueValidator("WS", "MQ");
		modeValidator.setMessage("Укажите режим взаимодействия.");
		FieldValidator timeoutValidator = new NumericRangeValidator(BigDecimal.ZERO, new BigDecimal(9999999999L), "Укажите таймаут ожидания ответа от MQ.");
		for (ServiceSettings service : esbServices)
		{
			String modeName = "com.rssl.es.integration.service.mode." + service.getName();
			fb.addField(FieldBuilder.buildStringField(modeName, service.getName(), modeValidator));
			String mqTimeoutName = "com.rssl.es.integration.service.timeout.mq." + service.getName();
			fb.addField(FieldBuilder.buildStringField(mqTimeoutName, service.getName(), timeoutValidator));
			if (service.getMode() == ServiceSettings.Mode.serviceProvider)
				fb.addField(FieldBuilder.buildStringField("com.rssl.es.integration.service.providers.mq." + service.getName(), "поставщики"));
		}

		return fb.build();
	}

	private static Form createFormiPas()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;
		//Поле csa.ipas.service.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут соединения с АС \"iPas\"");
		fieldBuilder.setName(CSARefreshConfig.CSA_IPAS_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут соединения с АС \"iPas\"\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createFormCSA()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;
		//Поле csa.auth.service.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут соединения с ЦСА");
		fieldBuilder.setName(CSARefreshConfig.CSA_AUTH_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут соединения с ЦСА\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле csa.webservice.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес web-сервиса АС \"CSA\"");
		fieldBuilder.setName(CSARefreshConfig.CSA_SERVICE_URL);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес web-сервиса АС \"CSA\"\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createFormCSA_FRONT()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		//Поле com.rssl.auth.csa.front.config.use.captcha.restrict
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Включение капчи ЦСА Фронт");
		fieldBuilder.setName(USE_CAPTCHA_RESTRICT_PROPERTY_NAME);
		fieldBuilder.setType("string");
		FieldValidatorBase validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля \"Включение капчи ЦСА Фронт\" значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле CSA_FRONT.com.rssl.auth.csa.front.config.choose.region.mode
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Режим поддержки регионов");
		fieldBuilder.setName(CHOOSE_REGION_MODE_PROPERTY_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.setInitalValueExpression(new ConstantExpression(false));
		validator.setMessage("Для поля \"Режим поддержки регионов\" значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле CSA_FRONT.com.rssl.auth.csa.front.config.access.registration.external
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Возможность самостоятельной регистрации с внешней ссылки");
		fieldBuilder.setName(ACCESS_EXTERNAL_REGISTRATION_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator.setMessage("Для поля \"Возможность самостоятельной регистрации с внешней ссылки\" значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле CSA_FRONT.com.rssl.auth.csa.front.config.access.registration.external
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Возможность самостоятельной регистрации с внутренней ссылки");
		fieldBuilder.setName(ACCESS_INTERNAL_REGISTRATION_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator.setMessage("Для поля \"Возможность самостоятельной регистрации с внутренней ссылки\" значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createFormCSA_BACK()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;
		//Поле csa.back.webservice.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес web-сервиса АС \"CSA-BACK\"");
		fieldBuilder.setName(CSAConfig.CSA_BACK_URL);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Адрес web-сервиса АС \"CSA-BACK\"\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.ipas.csa.back.config.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут соединения с АС \"iPas\"");
		fieldBuilder.setName(IPAS_CSA_BACK_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут соединения с АС \"iPas\"\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.integration.ipas.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("URL веб-сервиса IPas");
		fieldBuilder.setName(IPAS_URL_PROPERTY_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"URL веб-сервиса IPas\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.common.confirmation.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут ожидания кода подтверждения");
		fieldBuilder.setName(CONFIRMATION_TIMEOUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут ожидания кода подтверждения\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.common.confirmation.code.length
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Длина кода подтверждения в СМС");
		fieldBuilder.setName(CONFIRMATION_CODE_LENGTH_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Длина кода подтверждения в СМС\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Список допустимых символов для кода подтверждения");
		fieldBuilder.setName(CONFIRMATION_CODE_ALLOWED_CHARS_PROPERTY_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Список допустимых символов для кода подтверждения\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.common.session.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальное время жизни сессии в часах");
		fieldBuilder.setName(SESSION_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,5}", "Поле \"Максимальное время жизни сессии в часах\" должено состоять из цифр и не должено быть длиннее, чем 5 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.registration.user.deny-multiple
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Возможность повторной регистрации");
		fieldBuilder.setName(DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME);
		fieldBuilder.setType("string");
		FieldValidatorBase validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля \"Возможность повторной регистрации\" значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.iccs.registration.user.deny-multiple
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Возможность повторной регистрации");
		fieldBuilder.setName(SELF_DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля \"Возможность повторной регистрации\" значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.iccs.ban.ipas.login
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Запрет входа с логином/алиасом iPAS");
		fieldBuilder.setName(BAN_IPAS_LOGIN_KEY);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля \"Запрет входа с логином/алиасом iPAS\" значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.registration.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут регистрации");
		fieldBuilder.setName(REGISTRATION_TIMEOUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут регистрации\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REGISTRATION_MODE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Режим самостоятельной регистрации изнутри СБОЛ");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SELF_REGISTRATION_DESIGN_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Дизайн самостоятельной регистрации изнутри СБОЛ");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SHOW_LOGIN_SELF_REGISTRATION_SCREEN_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Отображение страницы входа по собственному логину и паролю");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст сообщения всплывающего окна в мягком режиме регистрации при отсутствии профиля в новой CSA");
		fieldBuilder.setName(SELF_REGISTRATION_SOFT_MODE_NOT_EXIST_MESSAGE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "Поле \"" + fieldBuilder.getDescription() + "\" не должно превышать 500 символов")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст сообщения всплывающего окна в жестком режиме регистрации при отсутствии профиля в новой CSA");
		fieldBuilder.setName(SELF_REGISTRATION_HARD_MODE_NOT_EXIST_MESSAGE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "Поле \"" + fieldBuilder.getDescription() + "\" не должно превышать 500 символов")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст сообщения всплывающего окна в мягком режиме регистрации при наличии профиля в новой CSA");
		fieldBuilder.setName(SELF_REGISTRATION_SOFT_MODE_EXIST_MESSAGE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "Поле \"" + fieldBuilder.getDescription() + "\" не должно превышать 500 символов")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст сообщения всплывающего окна в жестком режиме регистрации при наличии профиля в новой CSA");
		fieldBuilder.setName(SELF_REGISTRATION_HARD_MODE_EXIST_MESSAGE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "Поле \"" + fieldBuilder.getDescription() + "\" не должно превышать 500 символов")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сообщение клиенту на форме регистрации");
		fieldBuilder.setName(SELF_REGISTRATION_FORM_MESSAGE);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "Поле \"" + fieldBuilder.getDescription() + "\" не должно превышать 500 символов")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Заголовок сообщения клиенту");
		fieldBuilder.setName(SELF_REGISTRATION_WINDOW_TITLE);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "Поле \"" + fieldBuilder.getDescription() + "\" не должно превышать 500 символов")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.authentication.blocking.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время блокировки профиля после неуспешной аутентификации");
		fieldBuilder.setName(AUTHENTICATION_FAILED_BLOCKING_TIME_OUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Время блокировки профиля после неуспешной аутентификации\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.authentication.failed.limit
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Количество неудачных попыток аутентификации, после которых происходит блокировка логина");
		fieldBuilder.setName(MAX_AUTHENTICATION_FAILED_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,5}", "Поле \"Количество неудачных попыток аутентификации, после которых происходит блокировка логина\" должено состоять из цифр и не должено быть длиннее, чем 5 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.authentication.ipas.password.store.allowed
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Режим сохранения паролей iPas");
		fieldBuilder.setName(IPAS_PASSWORD_STORE_ALLOWED_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля \"Режим сохранения паролей iPas\" значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.integration.ipas.allowed
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Проверка паролей через iPas");
		fieldBuilder.setName(IPAS_AUTHENTICATION_ALLOWED_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля \"Проверка паролей через iPas\" значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.password-restoration.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут восстановления пароля");
		fieldBuilder.setName(RESTORE_PASSWORD_TIMEOUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут восстановления пароля\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.mobile.registration.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут регистрации мобильного приложения");
		fieldBuilder.setName(MOBILE_REGISTRATION_TIMEOUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Тайм-аут регистрации мобильного приложения\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.mobile.registration.max.connectors
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальное количество зарегистрированных мобильных устройств на профиль");
		fieldBuilder.setName(MAX_MOBILE_CONNECTORS_COUNT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,5}", "Поле \"Максимальное количество зарегистрированных мобильных устройств на профиль\" должено состоять из цифр и не должено быть длиннее, чем 5 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.mobile.registration.request.limit
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальное допустимое количество неподтвержденных запросов на регистрацию");
		fieldBuilder.setName(MAX_MOBILE_REGISTRATION_REQUEST_COUNT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Максимальное допустимое количество неподтвержденных запросов на регистрацию\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Глубина поиска неподтвержденных запросов на регистрацию");
		fieldBuilder.setName(MOBILE_REGISTRATION_REQUEST_CHECK_INTERVAL_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "Поле \"Глубина поиска неподтвержденных запросов на регистрацию\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле mb.system.id
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор системы CSA-BACK в МБК");
		fieldBuilder.setName(MobileBankConfig.MB_SYSTEM_ID);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Идентификатор системы CSA-BACK в МБК\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.systemLog.level.Core
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Ядро\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Core");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.systemLog.level.Gate
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Шлюз\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Gate");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.systemLog.level.Scheduler
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Обработчик расписания\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Scheduler");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.systemLog.level.Cache
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Система кеширования\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Cache");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.systemLog.level.Web
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации журнала системных действий для модуля \"Веб\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Web");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.SystemLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места логирования журнала системных действий");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор экземпляра приложения для синхронного логирования системных действий");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Идентификатор экземпляра приложения для синхронного логирования системных действий\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор очереди для асинхронного логирования системных действий");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Идентификатор очереди для асинхронного логирования системных действий\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор фабрики для асинхронного логирования системных сообщений.");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Идентификатор фабрики для асинхронного логирования системных сообщений.\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.SystemLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места резервного логирования журнала системных действий ");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.MessageLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места логирования журнала сообщений");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter");
		fieldBuilder.setType("string");
        validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор экземпляра приложения для синхронного логирования журнала сообщений");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Идентификатор экземпляра приложения для синхронного логирования журнала сообщений\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор очереди для асинхронного логирования журнала сообщений");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Идентификатор очереди для асинхронного логирования журнала сообщений\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор фабрики для асинхронного логирования журнала сообщений.");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Идентификатор фабрики для асинхронного логирования журнала сообщений.\" должено быть не длиннее, чем 100 символов.")
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.MessageLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места резервного логирования журнала сообщений");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.messagesLog.level.jdbc
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логирование сообщений взаимодействия CSA-BACK и МБК");
		fieldBuilder.setName("com.rssl.phizic.logging.messagesLog.level.jdbc");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("on", "off");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.messagesLog.level.iPas
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логирование сообщений взаимодействия CSA-BACK и iPas");
		fieldBuilder.setName("com.rssl.phizic.logging.messagesLog.level.iPas");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("on", "off");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.iccs.multiple.registration.part.visible
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Отображение текста «Либо зарегистрируйтесь»");
		fieldBuilder.setName(MULTIPLE_REGISTRATION_PART_VISIBLE);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля \"Отображение текста «Либо зарегистрируйтесь»\" значение вне диапазона");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Режим гостевого доступа");
		fieldBuilder.setName(GUEST_ENTRY_MODE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля " + fieldBuilder.getDescription() + " значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальное количество номеров мобильных телефонов для гостевой регистрации с одного IP-адреса");
		fieldBuilder.setName(GUEST_ENTRY_PHONES_LIMIT);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,3}");
		validator.setMessage(fieldBuilder.getDescription() + " должно состоять из не более чем трёх цифр");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время блокировки IP-адреса");
		fieldBuilder.setName(GUEST_ENTRY_PHONES_LIMIT_COOLDOWN);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "Поле \"" + fieldBuilder.getDescription() + "\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Количество неуспешных попыток ввода пароля");
		fieldBuilder.setName(GUEST_ENTRY_SMS_COUNT);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "Поле \"" + fieldBuilder.getDescription() + "\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Количество попыток ввода SMS-пароля до блокировки");
		fieldBuilder.setName(GUEST_ENTRY_SMS_COUNT_GLOBAL);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "Поле \"" + fieldBuilder.getDescription() + "\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время блокировки клиента");
		fieldBuilder.setName(GUEST_ENTRY_SMS_COOLDOWN);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "Поле \"" + fieldBuilder.getDescription() + "\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время задержки между попытками регистрации до отображения теста CAPTCHA");
		fieldBuilder.setName(COMMON_GUEST_CAPTCHA_DELAY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "Поле \"" + fieldBuilder.getDescription() + "\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Минимальное время задержки между попытками регистрации до отключения теста CAPTCHA");
		fieldBuilder.setName(UNTRUSTED_GUEST_CAPTCHA_DELAY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "Поле \"" + fieldBuilder.getDescription() + "\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Включение CAPTCHA всегда");
		fieldBuilder.setName(CAPTCHA_CONTROL_ENABLED_PROPERTY_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("Для поля " + fieldBuilder.getDescription() + " значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период отображения заявок");
		fieldBuilder.setName(GUEST_ENTRY_CLAIMS_SHOW_PERIOD);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "Поле \"" + fieldBuilder.getDescription() + "\" должено состоять из цифр и не должено быть длиннее, чем 10 символов.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createFormRSA()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Режим работы с АС ВМ");
		fieldBuilder.setName(RSA_ACTIVITY_STATE);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RangeFieldValidator(Arrays.asList("ACTIVE_INTERACTION", "ACTIVE_ONLY_SEND", "NOT_ACTIVE"), "Для поля \"Режим работы с АС ВМ\" значение вне диапазона.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес web-сервиса АС ВМ");
		fieldBuilder.setName(RSA_CONNECTION_URL);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,500}", "Поле \"Адрес web-сервиса АС ВМ\" должено быть не длиннее, чем 500 символов.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тайм-аут соединения с системой фрод-мониторинга");
		fieldBuilder.setName(RSA_CONNECTION_TIMEOUT);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{0,2}", "Поле \"Тайм-аут соединения с системой фрод-мониторинга\" должно состоять не более чем из 2х цифр.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логин пользователя АС ВМ");
		fieldBuilder.setName(RSA_CONNECTION_LOGIN);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "Поле \"Логин пользователя АС ВМ\" должено быть не длиннее, чем 50 символов.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Пароль пользователя АС ВМ");
		fieldBuilder.setName(RSA_CONNECTION_PASSWORD);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "Поле \"Пароль пользователя АС ВМ\" должено быть не длиннее, чем 50 символов.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время выгрузки неотправленных транзакций");
		fieldBuilder.setName(RSA_JOB_UNLOADING_TIME);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,150}", "Поле \"Время выгрузки неотправленных транзакций\" должено быть не длиннее, чем 50 символов.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Заполнять причину подтверждения документа");
		fieldBuilder.setName(RSA_VERDICT_COMMENT_ACTIVITY);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RangeFieldValidator(Arrays.asList("true", "false"), "Для поля \"Заполнять причину подтверждения документа\" значение вне диапазона.")
			);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
