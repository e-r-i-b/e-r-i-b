package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import static com.rssl.phizic.business.payments.job.JobRefreshConfig.*;
import static com.rssl.phizic.security.config.Constants.*;

/**
 * @author egorova
 * @ created 29.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class SystemSettingsForm extends EditPropertiesFormBase
{
	@Override
	public Form getForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DEPARTMENTS_ALLOWED_LEVEL);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("������� �������� �������������");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DEPARTMENT_ADMINS_LIMIT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("����� ������������ � ������������� ����������� �� ������ ���� \"��������������\"");
		fieldBuilder.addValidators (new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLAIM_WORKING_LIFE);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("���� �������� ������");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		NumericRangeValidator defaultExtendedLoggingTimeRangeValidator = new NumericRangeValidator();
		defaultExtendedLoggingTimeRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		defaultExtendedLoggingTimeRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99");
		defaultExtendedLoggingTimeRangeValidator.setMessage("����������, ������� ����� �������� ������������ ����������� � ����� � ��������� 0..99.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BusinessSettingsConfig.DEFAULT_EXTENDED_LOGGING_TIME);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("����� �������� ������������ ����������� ��-���������");
		fieldBuilder.addValidators(
				new RequiredFieldValidator("����������, ������� ����� �������� ������������ ����������� � �����."), 
				defaultExtendedLoggingTimeRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "2000000000");
		numericRangeValidator.setMessage("����������, ������� ����� �������� ������ �� ����� � ������������� � ��������� 0..2000000000.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TimeoutHttpTransport.GATE_WRAPPER_CONNECTION_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("����� �������� ������ �� ������");
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ����� �������� ������ �� ����� � �������������."), numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPEAT_SEND_REQUEST_DOCUMENT_TIMEOUT);
		fieldBuilder.setDescription("������������ ������ �������� �������� �� ���");
		DateFieldValidator dateFieldValidator = new DateFieldValidator("HH:mm:ss", "����� ������ �������� �������� �� ��� ������ ���� � ������� ��:��:��");
		RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("\\d{2}+(\\:)+\\d{2}+(\\:)+\\d{2}", "���� \"������������ ������ �������� �������� �� ���\" ������ ��������� ����� � ������� ��:��:��.");
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ������������ ������ �������� �������� �� ���."),
								   dateFieldValidator, regexpFieldValidator);
		fieldBuilder.setParser(new DateParser(DateHelper.TIME_FORMAT));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CATALOG_AGGREGATION_INTERVAL);
		fieldBuilder.setDescription("������������ ������� ��������� ��������� �������� ����� (��:��:��)");
		dateFieldValidator = new DateFieldValidator("HH:mm:ss", "������������ ������� ��������� ��������� �������� ����� ������ ���� � ������� ��:��:��");
		regexpFieldValidator = new RegexpFieldValidator("\\d{2}+(\\:)+\\d{2}+(\\:)+\\d{2}", "���� \"������������ ������� ��������� ��������� �������� �����\" ������ ��������� ����� � ������� ��:��:��.");
		fieldBuilder.addValidators(new RequiredFieldValidator("����������, ������� ������������ ������� ��������� ��������� �������� �����."),
				dateFieldValidator, regexpFieldValidator);
		fieldBuilder.setParser(new DateParser(DateHelper.TIME_FORMAT));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PAYMENT_JOB_SELECT_ORDER);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("��� ���������� ������� �������� ��� ��������� ���������");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAX_JOBS_COUNT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("������������ ���������� ����������� ������������ ���������� ������");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "������������ ���������� ����������� ������������ ���������� ������ ������� ��������� �� ����� 5 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAX_ROWS_FROM_FETCH);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("������������ ���������� ������� ��� ��������� ������");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "������������ ���������� ������� ��� ��������� ������ ������� ��������� �� ����� 5 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAX_JOBS_COUNT + "." + RESEND_ESB_PAYMENT_JOB);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("���������� ���������� ��� ��������� � ��������� �������");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "���������� ���������� ��� ��������� � ��������� ������� ������ ��������� �� ����� 5 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAX_ROWS_FROM_FETCH + "." + RESEND_ESB_PAYMENT_JOB);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("���������� ���������� ��� ���������");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "���������� ���������� ��� ��������� ������ ��������� �� ����� 5 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EXPIRED_DOCUMENT_HOUR_PREFIX + RESEND_ESB_PAYMENT_JOB);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("������ ������� ���������� ��� ���������");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "������ ������� ���������� ��� ��������� ������ ��������� �� ����� 5 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NUMBER_OF_RESEND_ESB_PAYMENT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("���������� ������� ��������� ��������� �������");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "���������� ������� ��������� ��������� ������� ������ ��������� �� ����� 5 ����"));
		formBuilder.addField(fieldBuilder.build());
		
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PAYMENT_JOB_SELECT_ORDER + "." + RESEND_ESB_PAYMENT_JOB);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("������ ������� ���������� ��� ���������");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DENY_CUSTOM_RIGHTS);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("������ ��������� ����������� �������������� ����");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EXPIRED_DOCUMENT_HOUR_PREFIX + DEPOCOD_PAYMENTS_EXEC_JOB);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("����� (����), �� ��������� �������� ����� �������� �������� ��������� ����������");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,3})?$", "���� " + fieldBuilder.getDescription() + " ������ ��������� �� ����� 3 ����."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NUM_REQUESTS_OPERATION_PREFIX + DEPOCOD_PAYMENTS_EXEC_JOB);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("������������ ���������� �������� ������� ��������");
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "���� \" " + fieldBuilder.getDescription() + "\" ������ ��������� �� ������ 2 ����."));
		formBuilder.addField(fieldBuilder.build());

		for (Integer i : PropertyHelper.getTableSettingNumbers(getFields(), MOBILE_PROVIDER_ID_KEY))
		{
			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("��� ����������");
			fieldBuilder.setName(MOBILE_PROVIDER_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("[a-zA-Z\\d]{1,20}", "���� \"��� ����������\" ������ ��������� ������ ����� � ����� ����� 20 ��������"));
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("��� ������");
			fieldBuilder.setName(MOBILE_SERVICE_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("[a-zA-Z\\d]{1,35}", "���� \"��� ������\" ������ ��������� ������ ����� � ����� ����� 35 ��������"));
			formBuilder.addField(fieldBuilder.build());
		}

		for (Integer i : PropertyHelper.getTableSettingNumbers(getFields(), OLD_DOC_ADAPTER_ID_KEY))
		{
			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("�������� �������");
			fieldBuilder.setName(OLD_DOC_ADAPTER_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{2}[-][a-zA-Z,:]{1,}", "������ ���� \"�������� �������\":  [TT-XXXXXXXX], ��� �� - ��� ��������, XXXXXXXX - �������� �������. "));
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();

	}
}
