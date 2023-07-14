package com.rssl.phizic.web.quick.pay;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author komarov
 * @ created 12.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListQuickPaymentPanelForm  extends ListFormBase
{
	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy";

	private static final String START_DATE_MESSAGE = "Введите корректную дату начала отображения в формате ДД.ММ.ГГГГ.";
	private static final String CANCEL_DATE_MESSAGE = "Введите корректную дату окончания отображения в формате ДД.ММ.ГГГГ.";

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();


        FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период с");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new DateFieldValidator(DATESTAMP_FORMAT, START_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", START_DATE_MESSAGE)
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Период по");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new DateFieldValidator(DATESTAMP_FORMAT, CANCEL_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", CANCEL_DATE_MESSAGE)
		);

		formBuilder.addField(fieldBuilder.build());

		// Название
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,50}", "Размер поля Название должен быть не более 50 символов")
		);
		formBuilder.addField(fieldBuilder.build());


		// Статус
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		// Наименование департамента
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование департамента");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType(StringType.INSTANCE.getName());
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
