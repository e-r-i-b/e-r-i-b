package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.log.MessageLogForm;

/**
 * @author tisov
 * @ created 23.07.15
 * @ $Author$
 * @ $Revision$
 * Форма просмотра записей о гостевых входах
 */
public class ViewCSAGuestEntriesLogForm extends MessageLogForm
{

	public static final Form CSA_MESSAGE_LOG_FILTER_FORM = createFilterForm();
	private static final String BIRTH_DATE_FIELD_NAME = "birthday";

	protected static Form createFilterForm()
	{

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("logUID");
		fieldBuilder.setDescription("Идентификатор операции логирования.");

		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Начальная дата");
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("Введите начальную дату");
		DateFieldValidator dateFieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ");
		fieldBuilder.addValidators(requiredFieldValidator, dateFieldValidator);
		fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("Начальное время");
		dateFieldValidator = new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ");
		fieldBuilder.setValidators(new FieldValidator[]{dateFieldValidator});
		fieldBuilder.setParser(timeParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Конечная дата");
		requiredFieldValidator = new RequiredFieldValidator("Введите конечную дату");
		dateFieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ");
		fieldBuilder.addValidators(requiredFieldValidator, dateFieldValidator);
		fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("Конечное время");
		dateFieldValidator = new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ");
		fieldBuilder.setValidators(new FieldValidator[]{dateFieldValidator});
		fieldBuilder.setParser(timeParser);

		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");

		formBuilder.setFormValidators(dateTimeCompareValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(GUEST_LOG_NAME);
		fieldBuilder.setType("boolean");
		fieldBuilder.setDescription("Гостевой СБОЛ");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PHONE_NUMBER_NAME);
		fieldBuilder.setDescription("Номер телефона");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("^7.*", "Номер телефона должен начинаться с 7"),
				new RegexpFieldValidator("\\d{11}", "Номер телефона должен состоять из 11 цифр")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
		fieldBuilder.setDescription("Клиент");
		fieldBuilder.addValidators(new RegexpFieldValidator("[а-яА-ЯёЁa-zA-Z- ]{3,255}","Пожалуйста, укажите ФИО клиента не менее 3 символов, содержащее буквы русского или латинского алфавита, дефис или пробел"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BIRTH_DATE_FIELD_NAME);
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("Дата рождения");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("TB");
		fieldBuilder.setDescription("Код ТБ");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("ipAddres");
		fieldBuilder.setDescription("IP-адрес");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d{0,3}.\\d{0,3}.\\d{0,3}.\\d{0,3}$","Неверный формат ip-адреса"));
		formBuilder.addField(fieldBuilder.build());

		RequiredAllMultiFieldStringValidator fioAndBirthdayRequiredValidator = new RequiredAllMultiFieldStringValidator();
		fioAndBirthdayRequiredValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		fioAndBirthdayRequiredValidator.setBinding(BIRTH_DATE_FIELD_NAME, BIRTH_DATE_FIELD_NAME);

		RequiredAllMultiFieldStringValidator phoneRequiredValidator = new RequiredAllMultiFieldStringValidator();
		phoneRequiredValidator.setBinding(PHONE_NUMBER_NAME, PHONE_NUMBER_NAME);

		DisjunctionLogicValidator disjunctionValidator = new DisjunctionLogicValidator();
		disjunctionValidator.addValidator(fioAndBirthdayRequiredValidator);
		disjunctionValidator.addValidator(phoneRequiredValidator);

		DateTimePeriodMultiFieldValidator monthPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		monthPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.MONTH_TYPE_PERIOD);
		monthPeriodMultiFieldValidator.setLengthPeriod(1);
		monthPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 1 месяца.");

		formBuilder.addFormValidators(disjunctionValidator, monthPeriodMultiFieldValidator);

		return formBuilder.build();
	}
}
