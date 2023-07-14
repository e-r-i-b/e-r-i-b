package com.rssl.phizic.web.advertising;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;

/**
 * @author lepihina
 * @ created 23.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListAdvertisingBlockForm extends ListFormBase
{
	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy";

	private static final String START_DATE_MESSAGE = "Введите корректную дату начала отображения в формате ДД.ММ.ГГГГ.";
	private static final String CANCEL_DATE_MESSAGE = "Введите корректную дату окончания отображения в формате ДД.ММ.ГГГГ.";

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);

        FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период с");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new DateFieldValidator(DATESTAMP_FORMAT, START_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", START_DATE_MESSAGE)
		);
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Период по");
		fieldBuilder.addValidators(
				new DateFieldValidator(DATESTAMP_FORMAT, CANCEL_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", CANCEL_DATE_MESSAGE)
		);
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		// Название
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,50}", "Размер поля Название должен быть не более 50 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		// Приоритет
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Приоритет");
		fieldBuilder.setName("orderIndex");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "Поле Приоритет должно содержать только цифры"));
		formBuilder.addField(fieldBuilder.build());

		// Статус
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		// Наименование департамента
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование департамента");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		// Подразделение
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
		formBuilder.addFormValidators(dateTimeCompareValidator);

		return formBuilder.build();
	}
}
