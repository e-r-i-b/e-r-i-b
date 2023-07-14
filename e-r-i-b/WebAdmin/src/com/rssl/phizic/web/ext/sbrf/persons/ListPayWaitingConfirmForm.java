package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentListFormBase;

/**
 * @author niculichev
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListPayWaitingConfirmForm extends ViewDocumentListFormBase
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static String TIMESTAMP = "HH:mm:ss";

	public static Form FILTER_FORM = createFilterForm();

	private Long person;
	private ActivePerson activePerson;
	private Boolean modified = false;

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}


	private static Form createFilterForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP);
		DateParser timeParser = new DateParser(TIMESTAMP);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromDate");
		fb.setDescription("Дата");
		fb.setValidators(new DateFieldValidator(DATESTAMP, "Дата должна быть в формате дд.мм.гггг"));
		fb.addValidators(new RequiredFieldValidator("Введите начальную дату"));

		fb.setParser(dateParser);
		formBuilder.addField(fb.build());


		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromTime");
		fb.setDescription("Время");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(TIMESTAMP, "Время должно быть в формате чч:мм:сс"));
		fb.setParser(timeParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toDate");
		fb.setDescription("Дата");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(DATESTAMP, "Дата должна быть в формате дд.мм.гггг"));
		fb.addValidators(new RequiredFieldValidator("Введите конечную дату"));
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toTime");
		fb.setDescription("Время");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator(TIMESTAMP, "Время должно быть в формате чч:мм:сс"));
		fb.setParser(timeParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("formName");
		fb.setDescription("Вид операции");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("receiverName");
		fb.setDescription("Получатель");
		fb.addValidators(new RegexpFieldValidator(".{0,100}", "Размер поля Получатель должен быть не более 100 символов."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("state");
		fb.setDescription("Статус операции");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(LongType.INSTANCE.getName());
		fb.setName("number");
		fb.setDescription("№ платежа/заявки");
		fb.addValidators(new RegexpFieldValidator(".{0,10}", "Размер поля № платежа/заявки должен быть не более 10 символов."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("additionConfirm");
		fb.setDescription("Подтвержден");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName("confirmEmployee");
		fb.setDescription("Подтвердил сотрудник");
		fb.addValidators(new RegexpFieldValidator(".{0,50}", "Размер поля Подтвердил сотрудник должен быть не более 50 символов."));
		formBuilder.addField(fb.build());

		// ФОРМ ВАРИДАТОРЫ

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "toDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "toTime");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "fromDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "fromTime");
		dateTimeValidator.setMessage("Дата в поле \"с\" должна быть меньше даты в поле \"по\"");
		formBuilder.addFormValidators(dateTimeValidator);

		DateTimePeriodMultiFieldValidator hourPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		hourPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.MONTH_TYPE_PERIOD);
		hourPeriodMultiFieldValidator.setLengthPeriod(1);
		hourPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 1 месяца.");
		formBuilder.addFormValidators(hourPeriodMultiFieldValidator);

		return formBuilder.build();
	}

}
