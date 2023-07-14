package com.rssl.phizic.web.common;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.phizic.utils.ListUtil;

/**
 * @author gladishev
 * @ created 27.09.2012
 * @ $Author $
 * @ $Revision $
 */
public abstract class FilterPeriodFormBase extends EditFormBase
{
	public static final Form FILTER_FORM = createFilterForm();

	protected static Form createFilterForm()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeDate == 'period'");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(format);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typeDate");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "week", "month", "period" } ))
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период с");
	    fieldBuilder.setName("fromDate");
	    fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период по");
	    fieldBuilder.setName("toDate");
	    fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
	    formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}
}
