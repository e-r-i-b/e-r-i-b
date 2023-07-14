package com.rssl.phizic.web.common.client.finances.ajax;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author komarov
 * @ created 06.05.2013 
 * @ $Author$
 * @ $Revision$
 */

public class SaveBudgetForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static final Form SAVE_BUDGET_FORM = createForm();
	public static final Form DELETE_BUDGET_FORM = createDeleteForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		DateParser dataParser = new DateParser(DATE_FORMAT);
		DateFieldValidator dateValidator = new DateFieldValidator(DATE_FORMAT);
		MoneyFieldValidator moneyValidator = new MoneyFieldValidator();
		moneyValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, "0");
		moneyValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE, "99999999999.99");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Бюджет");
		fieldBuilder.setName("budgetSumm");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators (
				new RequiredFieldValidator(),
				moneyValidator
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата");
		fieldBuilder.setName("date");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dateValidator);
		fieldBuilder.setParser(dataParser);
		fb.addField(fieldBuilder.build());

		fb.addFields(getCommonFields());

		return fb.build();
	}

	private static Form createDeleteForm()
	{
		FormBuilder fb = new FormBuilder();
		fb.addFields(getCommonFields());
		return fb.build();
	}

	private static List<Field> getCommonFields()
	{
		List<Field> fields = new ArrayList<Field>();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата, когда клиент зашел на страницу");
		fieldBuilder.setName("openPageDate");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATE_TIME_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator(DATE_TIME_FORMAT));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификаторы карт");
		fieldBuilder.setName("selectedCardIds");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		return fields;
	}
}
