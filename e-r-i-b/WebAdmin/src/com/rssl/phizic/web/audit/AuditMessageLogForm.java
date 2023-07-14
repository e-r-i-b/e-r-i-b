package com.rssl.phizic.web.audit;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.log.MessageLogForm;

/**
 * @author akrenev
 * @ created 03.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class AuditMessageLogForm extends MessageLogForm
{
	public static Form MESSAGE_LOG_FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fio");
		fieldBuilder.setDescription("Клиент");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("birthday");
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("Дата рождения");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("series");
		fieldBuilder.setDescription("Серия");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,16}", "Поле серия документа не должно превышать 16 символов"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*","Поле серия документа не должно содержать спецсимволов")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("number");
		fieldBuilder.setDescription("Номер");
		fieldBuilder.addValidators(
            new RegexpFieldValidator("\\d*", "Поле номер документа должно содержать только цифры"),
            new RegexpFieldValidator(".{0,16}", "Поле номер документа не должно превышать 16 цифр")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("operationUID");
		fieldBuilder.setDescription("operationUID");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.addValidators(new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("Начальное время");
		fieldBuilder.setValidators(new FieldValidator[]{new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ")});
		fieldBuilder.setParser(timeParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.addValidators(new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("Конечное время");
		fieldBuilder.setValidators(new FieldValidator[]{new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ")});
		fieldBuilder.setParser(timeParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("application");
		fieldBuilder.setDescription("Приложение");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxRows");
		fieldBuilder.setDescription("Максимальное количество записей");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("requestWord");
		fieldBuilder.setDescription("Слово в запросе");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("requestresponceWord");
		fieldBuilder.setDescription("Слово в запросе/ответе");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("responceWord");
		fieldBuilder.setDescription("Слово в ответе");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("requestTag");
		fieldBuilder.setDescription("Тип запроса");

		formBuilder.addField(fieldBuilder.build());

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("system");
		fieldBuilder.setDescription("Система");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование департамента");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType("string");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentId");
		fieldBuilder.setDescription("Подразделение");

		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.setFormValidators(new MultiFieldsValidator[]{dateTimeCompareValidator});

		return formBuilder.build();
	}
}
