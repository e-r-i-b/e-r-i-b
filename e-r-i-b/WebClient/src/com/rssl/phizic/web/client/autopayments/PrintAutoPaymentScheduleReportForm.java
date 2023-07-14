package com.rssl.phizic.web.client.autopayments;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author osminin
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoPaymentScheduleReportForm extends EditFormBase
{
	private AutoPaymentLink link;
	private List<ScheduleItem> scheduleItems;
	private String fromDateString;
	private String toDateString;
	private Department currentDepartment;
	private Department topLevelDepartment;

	public static final Form PRINT_FORM = createPrintForm();

	private static Form createPrintForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		String format = "dd/MM/yyyy";
		DateParser parser = new DateParser(format);

		DateFieldValidator dateValidator = new DateFieldValidator(format);
		dateValidator.setMessage("Введите дату в формате " + format + ".");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.setName("fromDateString");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(parser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dateValidator, new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.setName("toDateString");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(parser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dateValidator, new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public AutoPaymentLink getLink()
	{
		return link;
	}

	public void setLink(AutoPaymentLink link)
	{
		this.link = link;
	}

	public List<ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(List<ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}

	public String getFromDateString()
	{
		return fromDateString;
	}

	public void setFromDateString(String fromDateString)
	{
		this.fromDateString = fromDateString;
	}

	public String getToDateString()
	{
		return toDateString;
	}

	public void setToDateString(String toDateString)
	{
		this.toDateString = toDateString;
	}

	public Department getCurrentDepartment()
	{
		return currentDepartment;
	}

	public void setCurrentDepartment(Department currentDepartment)
	{
		this.currentDepartment = currentDepartment;
	}

	public Department getTopLevelDepartment()
	{
		return topLevelDepartment;
	}

	public void setTopLevelDepartment(Department topLevelDepartment)
	{
		this.topLevelDepartment = topLevelDepartment;
	}
}
