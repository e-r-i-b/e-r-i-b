package com.rssl.phizic.web.client.ext.sbrf.loans;

import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.parsers.DateParser;

import java.util.Map;
import java.util.HashMap;

/**
 * @author mihaylov
 * @ created 02.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class PrintLoanPaymentsForm extends PrintLoanInfoForm
{
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final Form FILTER_FORM     = PrintLoanPaymentsForm.createFilterForm();

	private ScheduleAbstract scheduleAbstract;
	private Map<String,Object> filter = new HashMap<String, Object>();

	public ScheduleAbstract getScheduleAbstract()
	{
		return scheduleAbstract;
	}

	public void setScheduleAbstract(ScheduleAbstract scheduleAbstract)
	{
		this.scheduleAbstract = scheduleAbstract;
	}

	public Map<String, Object> getFilter()
	{
		return filter;
	}

	public void setFilter(Map<String, Object> filter)
	{
		this.filter = filter;
	}

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("fromPeriod");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(new DateParser(DATE_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									new DateFieldValidator(DATE_FORMAT, "Введите дату в поле Период в формате "+DATE_FORMAT+"."));
		formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("toPeriod");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(new DateParser(DATE_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									new DateFieldValidator(DATE_FORMAT, "Введите дату в поле Период в формате "+DATE_FORMAT+"."));
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromPeriod");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toPeriod");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}
}
