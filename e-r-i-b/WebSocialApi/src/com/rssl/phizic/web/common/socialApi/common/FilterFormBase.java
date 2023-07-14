package com.rssl.phizic.web.common.socialApi.common;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.FilterDateCountValidator;
import com.rssl.common.forms.validators.FilterDateValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author egorova
 * @ created 01.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class FilterFormBase extends EditFormBase
{
	public static final String FROM_DATE_NAME = "from";
	public static final String TO_DATE_NAME = "to";
	public static final String COUNT_NAME = "count";

	public static final Form FILTER_DATE_COUNT_FORM =  filterDateCountForm();
	public static final Form FILTER_DATE_FORM =  filterDateForm();

	private String from;
	private String to;
	private String count;

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getCount()
	{
		return count;
	}

	public void setCount(String count)
	{
		this.count = count;
	}

	private static Form filterDateCountForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		buildDateFields(formBuilder);

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Количество последних записей");
		fieldBuilder.setName(COUNT_NAME);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		FilterDateCountValidator filterDateCountValidator =  new FilterDateCountValidator();
		filterDateCountValidator.setBinding(FilterDateCountValidator.COUNT, COUNT_NAME);
		filterDateCountValidator.setBinding(FilterDateCountValidator.FROM_DATE, FROM_DATE_NAME);
		filterDateCountValidator.setBinding(FilterDateCountValidator.TO_DATE, TO_DATE_NAME);

		formBuilder.addFormValidators(filterDateCountValidator);

		return formBuilder.build();
	}

	private static Form filterDateForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		buildDateFields(formBuilder);

		FilterDateValidator filterDateValidator =  new FilterDateValidator();
		filterDateValidator.setBinding(FilterDateCountValidator.FROM_DATE, FROM_DATE_NAME);
		filterDateValidator.setBinding(FilterDateCountValidator.TO_DATE, TO_DATE_NAME);

		formBuilder.addFormValidators(filterDateValidator);

		return formBuilder.build();
	}

	private static void buildDateFields(FormBuilder formBuilder)
	{
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата начала периода");
		fieldBuilder.setName(FROM_DATE_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата окончания периода");
		fieldBuilder.setName(TO_DATE_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
	}
}
