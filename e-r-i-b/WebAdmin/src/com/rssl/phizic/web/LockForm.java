package com.rssl.phizic.web;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.math.BigInteger;

/**
 * @author Egorova
 * @ created 23.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class LockForm extends ActionFormBase
{
	private static final BigInteger REASON_MAX_LENGTH = BigInteger.valueOf(1024);
	private static final String DATETIMESTAMP = "dd.MM.yyyy HH:mm:ss";

	public static final String BLOCK_START_DATE_FIELD_NAME = "blockStartDate";
	public static final String BLOCK_END_DATE_FIELD_NAME = "blockEndDate";
	public static final String BLOCK_REASON_FIELD_NAME = "blockReason";

	public static final Form LOCK_FORM = createForm();

	private static Field getDateField(String name, String description, boolean required, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATETIMESTAMP));
		fieldBuilder.clearValidators();
		if (required)
			fieldBuilder.addValidators(new RequiredFieldValidator());

		DateFieldValidator dateValidatorStartDate = new DateFieldValidator(DATETIMESTAMP);
		dateValidatorStartDate.setMessage("Введите корректную дату в поле " + description + " в формате ДД.ММ.ГГГГ ЧЧ:ММ:СС");
		fieldBuilder.addValidators(dateValidatorStartDate);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	public static Form createForm()
	{
        FormBuilder formBuilder   = new FormBuilder();

	    formBuilder.addField(getDateField(BLOCK_START_DATE_FIELD_NAME, "Дата начала блокировки", true));
		formBuilder.addField(getDateField(BLOCK_END_DATE_FIELD_NAME, "Дата окончания блокировки", false));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BLOCK_REASON_FIELD_NAME);
		fieldBuilder.setDescription("Причина блокировки");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new LengthFieldValidator(REASON_MAX_LENGTH));

	    formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, BLOCK_START_DATE_FIELD_NAME);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, BLOCK_START_DATE_FIELD_NAME);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, BLOCK_END_DATE_FIELD_NAME);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, BLOCK_END_DATE_FIELD_NAME);
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");

		formBuilder.addFormValidators(dateTimeCompareValidator);

		return formBuilder.build();
	}
}
