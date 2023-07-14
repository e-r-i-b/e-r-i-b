package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.utils.ListUtil;

/**
 * @author Erkin
 * @ created 29.04.2011
 * @ $Author$
 * @ $Revision$
 */
class AccountOperationsFilterFormBuilder extends FilterFormBuilder
{
	@SuppressWarnings({"OverlyLongMethod"})
	protected FormBuilder getFilterFormBuilder ()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typePeriod == 'period' ");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(format);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typePeriod");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "week", "month", "period" } ))
		);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("fromPeriod");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("toPeriod");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
	    formBuilder.addField(fieldBuilder.build());

		return formBuilder;
	}


	protected FormBuilder prepareFilterFormBuilder()
	{

		Expression periodDatesExpression = new RhinoExpression("form.typePeriod == 'period' ");
		FormBuilder formBuilder = getFilterFormBuilder();

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromPeriod");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toPeriod");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		return formBuilder;
	}
}
