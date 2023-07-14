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
 * ����� ��� �������������� ���������� ������� �������.
 *
 * @author bogdanov
 * @ created 11.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConcreteExternalSystemConfigForm extends EditPropertiesFormBase
{
	/**
	 * ���������� �����.
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
	 * @return ��� ������� �������.
	 */
	public String getSystemName()
	{
		return systemName;
	}

	/**
	 * @param systemName ��� ������� �������.
	 */
	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}

	/**
	 * @return ������������ ��� ������� �������.
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
	 * @return ������ ����������� �������� ����
	 */
	public List<ServiceSettings> getEsbServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return esbServices;
	}

	private static Form createFormESB()
	{
		FormBuilder fb = new FormBuilder();
		//���� com.rssl.gate.connection.timeout
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ���������� � ���");
		fieldBuilder.setName(ESBEribConfig.ESB_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� ���������� � ���\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		FieldValidator modeValidator = new ChooseValueValidator("WS", "MQ");
		modeValidator.setMessage("������� ����� ��������������.");
		FieldValidator timeoutValidator = new NumericRangeValidator(BigDecimal.ZERO, new BigDecimal(9999999999L), "������� ������� �������� ������ �� MQ.");
		for (ServiceSettings service : esbServices)
		{
			String modeName = "com.rssl.es.integration.service.mode." + service.getName();
			fb.addField(FieldBuilder.buildStringField(modeName, service.getName(), modeValidator));
			String mqTimeoutName = "com.rssl.es.integration.service.timeout.mq." + service.getName();
			fb.addField(FieldBuilder.buildStringField(mqTimeoutName, service.getName(), timeoutValidator));
			if (service.getMode() == ServiceSettings.Mode.serviceProvider)
				fb.addField(FieldBuilder.buildStringField("com.rssl.es.integration.service.providers.mq." + service.getName(), "����������"));
		}

		return fb.build();
	}

	private static Form createFormiPas()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;
		//���� csa.ipas.service.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ���������� � �� \"iPas\"");
		fieldBuilder.setName(CSARefreshConfig.CSA_IPAS_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� ���������� � �� \"iPas\"\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createFormCSA()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;
		//���� csa.auth.service.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ���������� � ���");
		fieldBuilder.setName(CSARefreshConfig.CSA_AUTH_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� ���������� � ���\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� csa.webservice.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� web-������� �� \"CSA\"");
		fieldBuilder.setName(CSARefreshConfig.CSA_SERVICE_URL);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� web-������� �� \"CSA\"\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createFormCSA_FRONT()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		//���� com.rssl.auth.csa.front.config.use.captcha.restrict
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����� ��� �����");
		fieldBuilder.setName(USE_CAPTCHA_RESTRICT_PROPERTY_NAME);
		fieldBuilder.setType("string");
		FieldValidatorBase validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� \"��������� ����� ��� �����\" �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� CSA_FRONT.com.rssl.auth.csa.front.config.choose.region.mode
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������� ��������");
		fieldBuilder.setName(CHOOSE_REGION_MODE_PROPERTY_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.setInitalValueExpression(new ConstantExpression(false));
		validator.setMessage("��� ���� \"����� ��������� ��������\" �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� CSA_FRONT.com.rssl.auth.csa.front.config.access.registration.external
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��������������� ����������� � ������� ������");
		fieldBuilder.setName(ACCESS_EXTERNAL_REGISTRATION_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator.setMessage("��� ���� \"����������� ��������������� ����������� � ������� ������\" �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� CSA_FRONT.com.rssl.auth.csa.front.config.access.registration.external
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��������������� ����������� � ���������� ������");
		fieldBuilder.setName(ACCESS_INTERNAL_REGISTRATION_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator.setMessage("��� ���� \"����������� ��������������� ����������� � ���������� ������\" �������� ��� ���������");
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
		//���� csa.back.webservice.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� web-������� �� \"CSA-BACK\"");
		fieldBuilder.setName(CSAConfig.CSA_BACK_URL);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"����� web-������� �� \"CSA-BACK\"\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.ipas.csa.back.config.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ���������� � �� \"iPas\"");
		fieldBuilder.setName(IPAS_CSA_BACK_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� ���������� � �� \"iPas\"\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.integration.ipas.url
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("URL ���-������� IPas");
		fieldBuilder.setName(IPAS_URL_PROPERTY_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"URL ���-������� IPas\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.common.confirmation.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� �������� ���� �������������");
		fieldBuilder.setName(CONFIRMATION_TIMEOUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� �������� ���� �������������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.common.confirmation.code.length
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���� ������������� � ���");
		fieldBuilder.setName(CONFIRMATION_CODE_LENGTH_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����� ���� ������������� � ���\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ���������� �������� ��� ���� �������������");
		fieldBuilder.setName(CONFIRMATION_CODE_ALLOWED_CHARS_PROPERTY_NAME);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"������ ���������� �������� ��� ���� �������������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.common.session.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ����� ����� ������ � �����");
		fieldBuilder.setName(SESSION_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,5}", "���� \"������������ ����� ����� ������ � �����\" ������� �������� �� ���� � �� ������� ���� �������, ��� 5 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.registration.user.deny-multiple
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��������� �����������");
		fieldBuilder.setName(DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME);
		fieldBuilder.setType("string");
		FieldValidatorBase validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� \"����������� ��������� �����������\" �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.iccs.registration.user.deny-multiple
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��������� �����������");
		fieldBuilder.setName(SELF_DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� \"����������� ��������� �����������\" �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.iccs.ban.ipas.login
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ����� � �������/������� iPAS");
		fieldBuilder.setName(BAN_IPAS_LOGIN_KEY);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� \"������ ����� � �������/������� iPAS\" �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.registration.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� �����������");
		fieldBuilder.setName(REGISTRATION_TIMEOUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� �����������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REGISTRATION_MODE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("����� ��������������� ����������� ������� ����");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SELF_REGISTRATION_DESIGN_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("������ ��������������� ����������� ������� ����");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SHOW_LOGIN_SELF_REGISTRATION_SCREEN_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("����������� �������� ����� �� ������������ ������ � ������");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������� ������������ ���� � ������ ������ ����������� ��� ���������� ������� � ����� CSA");
		fieldBuilder.setName(SELF_REGISTRATION_SOFT_MODE_NOT_EXIST_MESSAGE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "���� \"" + fieldBuilder.getDescription() + "\" �� ������ ��������� 500 ��������")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������� ������������ ���� � ������� ������ ����������� ��� ���������� ������� � ����� CSA");
		fieldBuilder.setName(SELF_REGISTRATION_HARD_MODE_NOT_EXIST_MESSAGE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "���� \"" + fieldBuilder.getDescription() + "\" �� ������ ��������� 500 ��������")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������� ������������ ���� � ������ ������ ����������� ��� ������� ������� � ����� CSA");
		fieldBuilder.setName(SELF_REGISTRATION_SOFT_MODE_EXIST_MESSAGE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "���� \"" + fieldBuilder.getDescription() + "\" �� ������ ��������� 500 ��������")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������� ������������ ���� � ������� ������ ����������� ��� ������� ������� � ����� CSA");
		fieldBuilder.setName(SELF_REGISTRATION_HARD_MODE_EXIST_MESSAGE_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "���� \"" + fieldBuilder.getDescription() + "\" �� ������ ��������� 500 ��������")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ������� �� ����� �����������");
		fieldBuilder.setName(SELF_REGISTRATION_FORM_MESSAGE);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "���� \"" + fieldBuilder.getDescription() + "\" �� ������ ��������� 500 ��������")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��������� �������");
		fieldBuilder.setName(SELF_REGISTRATION_WINDOW_TITLE);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,500}", "���� \"" + fieldBuilder.getDescription() + "\" �� ������ ��������� 500 ��������")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.authentication.blocking.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���������� ������� ����� ���������� ��������������");
		fieldBuilder.setName(AUTHENTICATION_FAILED_BLOCKING_TIME_OUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����� ���������� ������� ����� ���������� ��������������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.authentication.failed.limit
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ��������� ������� ��������������, ����� ������� ���������� ���������� ������");
		fieldBuilder.setName(MAX_AUTHENTICATION_FAILED_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,5}", "���� \"���������� ��������� ������� ��������������, ����� ������� ���������� ���������� ������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 5 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.authentication.ipas.password.store.allowed
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���������� ������� iPas");
		fieldBuilder.setName(IPAS_PASSWORD_STORE_ALLOWED_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� \"����� ���������� ������� iPas\" �������� ��� ���������");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.integration.ipas.allowed
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ������� ����� iPas");
		fieldBuilder.setName(IPAS_AUTHENTICATION_ALLOWED_PROPERTY_NAME);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� \"�������� ������� ����� iPas\" �������� ��� ���������");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.password-restoration.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� �������������� ������");
		fieldBuilder.setName(RESTORE_PASSWORD_TIMEOUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� �������������� ������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.mobile.registration.timeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ����������� ���������� ����������");
		fieldBuilder.setName(MOBILE_REGISTRATION_TIMEOUT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"����-��� ����������� ���������� ����������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.mobile.registration.max.connectors
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ���������� ������������������ ��������� ��������� �� �������");
		fieldBuilder.setName(MAX_MOBILE_CONNECTORS_COUNT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,5}", "���� \"������������ ���������� ������������������ ��������� ��������� �� �������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 5 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.mobile.registration.request.limit
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ���������� ���������� ���������������� �������� �� �����������");
		fieldBuilder.setName(MAX_MOBILE_REGISTRATION_REQUEST_COUNT_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"������������ ���������� ���������� ���������������� �������� �� �����������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ������ ���������������� �������� �� �����������");
		fieldBuilder.setName(MOBILE_REGISTRATION_REQUEST_CHECK_INTERVAL_PROPERTY_NAME);
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{0,10}", "���� \"������� ������ ���������������� �������� �� �����������\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� mb.system.id
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������� CSA-BACK � ���");
		fieldBuilder.setName(MobileBankConfig.MB_SYSTEM_ID);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"������������� ������� CSA-BACK � ���\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.systemLog.level.Core
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"����\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Core");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.systemLog.level.Gate
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"����\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Gate");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.systemLog.level.Scheduler
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"���������� ����������\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Scheduler");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.systemLog.level.Cache
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"������� �����������\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Cache");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.systemLog.level.Web
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������� ��������� �������� ��� ������ \"���\"");
		fieldBuilder.setName("com.rssl.phizic.logging.systemLog.level.Web");
		fieldBuilder.setType("integer");
		validator = new RangeFieldValidator("0", "1", "2", "3", "4", "5");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
				validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.SystemLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ����������� ������� ��������� ��������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ���������� ���������� ��� ����������� ����������� ��������� ��������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"������������� ���������� ���������� ��� ����������� ����������� ��������� ��������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������� ��� ������������ ����������� ��������� ��������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"������������� ������� ��� ������������ ����������� ��������� ��������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������� ��� ������������ ����������� ��������� ���������.");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"������������� ������� ��� ������������ ����������� ��������� ���������.\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.SystemLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ���������� ����������� ������� ��������� �������� ");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.MessageLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ����������� ������� ���������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter");
		fieldBuilder.setType("string");
        validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ���������� ���������� ��� ����������� ����������� ������� ���������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"������������� ���������� ���������� ��� ����������� ����������� ������� ���������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������� ��� ������������ ����������� ������� ���������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"������������� ������� ��� ������������ ����������� ������� ���������\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������� ��� ������������ ����������� ������� ���������.");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "���� \"������������� ������� ��� ������������ ����������� ������� ���������.\" ������� ���� �� �������, ��� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.MessageLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ���������� ����������� ������� ���������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.messagesLog.level.jdbc
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��������� �������������� CSA-BACK � ���");
		fieldBuilder.setName("com.rssl.phizic.logging.messagesLog.level.jdbc");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("on", "off");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.messagesLog.level.iPas
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ��������� �������������� CSA-BACK � iPas");
		fieldBuilder.setName("com.rssl.phizic.logging.messagesLog.level.iPas");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("on", "off");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.iccs.multiple.registration.part.visible
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ������ ����� ������������������");
		fieldBuilder.setName(MULTIPLE_REGISTRATION_PART_VISIBLE);
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� \"����������� ������ ����� ������������������\" �������� ��� ���������");
		fieldBuilder.addValidators(
			validator
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������� �������");
		fieldBuilder.setName(GUEST_ENTRY_MODE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� " + fieldBuilder.getDescription() + " �������� ��� ���������");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ���������� ������� ��������� ��������� ��� �������� ����������� � ������ IP-������");
		fieldBuilder.setName(GUEST_ENTRY_PHONES_LIMIT);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,3}");
		validator.setMessage(fieldBuilder.getDescription() + " ������ �������� �� �� ����� ��� ��� ����");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���������� IP-������");
		fieldBuilder.setName(GUEST_ENTRY_PHONES_LIMIT_COOLDOWN);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "���� \"" + fieldBuilder.getDescription() + "\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ���������� ������� ����� ������");
		fieldBuilder.setName(GUEST_ENTRY_SMS_COUNT);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "���� \"" + fieldBuilder.getDescription() + "\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ������� ����� SMS-������ �� ����������");
		fieldBuilder.setName(GUEST_ENTRY_SMS_COUNT_GLOBAL);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "���� \"" + fieldBuilder.getDescription() + "\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���������� �������");
		fieldBuilder.setName(GUEST_ENTRY_SMS_COOLDOWN);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "���� \"" + fieldBuilder.getDescription() + "\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �������� ����� ��������� ����������� �� ����������� ����� CAPTCHA");
		fieldBuilder.setName(COMMON_GUEST_CAPTCHA_DELAY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "���� \"" + fieldBuilder.getDescription() + "\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ����� �������� ����� ��������� ����������� �� ���������� ����� CAPTCHA");
		fieldBuilder.setName(UNTRUSTED_GUEST_CAPTCHA_DELAY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "���� \"" + fieldBuilder.getDescription() + "\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� CAPTCHA ������");
		fieldBuilder.setName(CAPTCHA_CONTROL_ENABLED_PROPERTY_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		validator = new RangeFieldValidator("true", "false");
		validator.setMessage("��� ���� " + fieldBuilder.getDescription() + " �������� ��� ���������");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ����������� ������");
		fieldBuilder.setName(GUEST_ENTRY_CLAIMS_SHOW_PERIOD);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		validator = new RegexpFieldValidator("\\d{0,10}", "���� \"" + fieldBuilder.getDescription() + "\" ������� �������� �� ���� � �� ������� ���� �������, ��� 10 ��������.");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	private static Form createFormRSA()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ������ � �� ��");
		fieldBuilder.setName(RSA_ACTIVITY_STATE);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RangeFieldValidator(Arrays.asList("ACTIVE_INTERACTION", "ACTIVE_ONLY_SEND", "NOT_ACTIVE"), "��� ���� \"����� ������ � �� ��\" �������� ��� ���������.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� web-������� �� ��");
		fieldBuilder.setName(RSA_CONNECTION_URL);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,500}", "���� \"����� web-������� �� ��\" ������� ���� �� �������, ��� 500 ��������.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����-��� ���������� � �������� ����-�����������");
		fieldBuilder.setName(RSA_CONNECTION_TIMEOUT);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{0,2}", "���� \"����-��� ���������� � �������� ����-�����������\" ������ �������� �� ����� ��� �� 2� ����.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ������������ �� ��");
		fieldBuilder.setName(RSA_CONNECTION_LOGIN);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "���� \"����� ������������ �� ��\" ������� ���� �� �������, ��� 50 ��������.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ������������ �� ��");
		fieldBuilder.setName(RSA_CONNECTION_PASSWORD);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,50}", "���� \"������ ������������ �� ��\" ������� ���� �� �������, ��� 50 ��������.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �������� �������������� ����������");
		fieldBuilder.setName(RSA_JOB_UNLOADING_TIME);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,150}", "���� \"����� �������� �������������� ����������\" ������� ���� �� �������, ��� 50 ��������.")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ������� ������������� ���������");
		fieldBuilder.setName(RSA_VERDICT_COMMENT_ACTIVITY);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RangeFieldValidator(Arrays.asList("true", "false"), "��� ���� \"��������� ������� ������������� ���������\" �������� ��� ���������.")
			);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
