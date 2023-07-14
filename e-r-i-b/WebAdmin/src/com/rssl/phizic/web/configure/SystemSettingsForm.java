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
		fieldBuilder.setDescription("Уровень иерархии подразделений");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DEPARTMENT_ADMINS_LIMIT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Лимит подключаемых к подразделению сотрудников со схемой прав \"Админинстратор\"");
		fieldBuilder.addValidators (new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLAIM_WORKING_LIFE);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Срок хранения заявок");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		NumericRangeValidator defaultExtendedLoggingTimeRangeValidator = new NumericRangeValidator();
		defaultExtendedLoggingTimeRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		defaultExtendedLoggingTimeRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99");
		defaultExtendedLoggingTimeRangeValidator.setMessage("Пожалуйста, укажите время действия расширенного логирования в часах в диапазоне 0..99.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BusinessSettingsConfig.DEFAULT_EXTENDED_LOGGING_TIME);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("Время действия расширенного логирования по-умолчанию");
		fieldBuilder.addValidators(
				new RequiredFieldValidator("Пожалуйста, укажите время действия расширенного логирования в часах."), 
				defaultExtendedLoggingTimeRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "2000000000");
		numericRangeValidator.setMessage("Пожалуйста, укажите время ожидания ответа от шлюза в миллисекундах в диапазоне 0..2000000000.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TimeoutHttpTransport.GATE_WRAPPER_CONNECTION_TIMEOUT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Время ожидания ответа от шлюзов");
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите время ожидания ответа от шлюза в миллисекундах."), numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(REPEAT_SEND_REQUEST_DOCUMENT_TIMEOUT);
		fieldBuilder.setDescription("Регулярность опроса статусов операций по ОМС");
		DateFieldValidator dateFieldValidator = new DateFieldValidator("HH:mm:ss", "Время опроса статусов операций по ОМС должно быть в формате чч:мм:сс");
		RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("\\d{2}+(\\:)+\\d{2}+(\\:)+\\d{2}", "Поле \"Регулярность опроса статусов операций по ОМС\" должно содержать время в формате чч:мм:сс.");
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите регулярность опроса статусов операций по ОМС."),
								   dateFieldValidator, regexpFieldValidator);
		fieldBuilder.setParser(new DateParser(DateHelper.TIME_FORMAT));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CATALOG_AGGREGATION_INTERVAL);
		fieldBuilder.setDescription("Регулярность запуска процедуры агрегации каталога услуг (чч:мм:сс)");
		dateFieldValidator = new DateFieldValidator("HH:mm:ss", "Регулярность запуска процедуры агрегации каталога услуг должна быть в формате чч:мм:сс");
		regexpFieldValidator = new RegexpFieldValidator("\\d{2}+(\\:)+\\d{2}+(\\:)+\\d{2}", "Поле \"Регулярность запуска процедуры агрегации каталога услуг\" должно содержать время в формате чч:мм:сс.");
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите регулярность запуска процедуры агрегации каталога услуг."),
				dateFieldValidator, regexpFieldValidator);
		fieldBuilder.setParser(new DateParser(DateHelper.TIME_FORMAT));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PAYMENT_JOB_SELECT_ORDER);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Тип сортировки выборки платежей для обработки шедулером");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAX_JOBS_COUNT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Максимальное количество экземпляров одновременно работающих джобов");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "Максимальное количество экземпляров одновременно работающих джобов должено содержать не более 5 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAX_ROWS_FROM_FETCH);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Максимальное количество записей для обработки джобом");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "Максимальное количество записей для обработки джобом должено содержать не более 5 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAX_JOBS_COUNT + "." + RESEND_ESB_PAYMENT_JOB);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество документов для обработки в несколько потоков");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "Количество документов для обработки в несколько потоков должно содержать не более 5 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAX_ROWS_FROM_FETCH + "." + RESEND_ESB_PAYMENT_JOB);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество документов для обработки");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "Количество документов для обработки должно содержать не более 5 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EXPIRED_DOCUMENT_HOUR_PREFIX + RESEND_ESB_PAYMENT_JOB);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Период выборки документов для обработки");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "Период выборки документов для обработки должен содержать не более 5 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NUMBER_OF_RESEND_ESB_PAYMENT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество попыток повторной обработки платежа");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,5})?$", "Количество попыток повторной обработки платежа должно содержать не более 5 цифр"));
		formBuilder.addField(fieldBuilder.build());
		
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PAYMENT_JOB_SELECT_ORDER + "." + RESEND_ESB_PAYMENT_JOB);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Способ выборки документов для обработки");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DENY_CUSTOM_RIGHTS);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Запрет установки сотрудникам индивидуальных прав");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EXPIRED_DOCUMENT_HOUR_PREFIX + DEPOCOD_PAYMENTS_EXEC_JOB);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("Время (часы), по истечению которого после отправки документ считается устаревшим");
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{0,3})?$", "Поле " + fieldBuilder.getDescription() + " должно содержать не более 3 цифр."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NUM_REQUESTS_OPERATION_PREFIX + DEPOCOD_PAYMENTS_EXEC_JOB);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("Максимальное количество запросов статуса операции");
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "Поле \" " + fieldBuilder.getDescription() + "\" должно содержать не больше 2 цифр."));
		formBuilder.addField(fieldBuilder.build());

		for (Integer i : PropertyHelper.getTableSettingNumbers(getFields(), MOBILE_PROVIDER_ID_KEY))
		{
			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("Код поставщика");
			fieldBuilder.setName(MOBILE_PROVIDER_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("[a-zA-Z\\d]{1,20}", "Поле \"Код поставщика\" должно содержать только буквы и цифры длины 20 символов"));
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("Код услуги");
			fieldBuilder.setName(MOBILE_SERVICE_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("[a-zA-Z\\d]{1,35}", "Поле \"Код услуги\" должно содержать только буквы и цифры длины 35 символов"));
			formBuilder.addField(fieldBuilder.build());
		}

		for (Integer i : PropertyHelper.getTableSettingNumbers(getFields(), OLD_DOC_ADAPTER_ID_KEY))
		{
			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("Название системы");
			fieldBuilder.setName(OLD_DOC_ADAPTER_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{2}[-][a-zA-Z,:]{1,}", "Формат поля \"Название системы\":  [TT-XXXXXXXX], где ТТ - код тербанка, XXXXXXXX - название системы. "));
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();

	}
}
