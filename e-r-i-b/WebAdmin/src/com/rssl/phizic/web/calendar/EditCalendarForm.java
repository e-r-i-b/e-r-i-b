package com.rssl.phizic.web.calendar;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Gainanov
 * @ created 26.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditCalendarForm extends EditFormBase
{
	public static final Form EDIT_CALENDAR_FORM = createForm();
	private String workdays;
	private String holidays;
	/**
	 * Является ли текущий сотрудник сотрудником ЦА
	 */
	private boolean caAdmin;

	public boolean isCaAdmin()
	{
		return caAdmin;
	}

	public void setCaAdmin(boolean caAdmin)
	{
		this.caAdmin = caAdmin;
	}

	public String getWorkdays()
	{
		return workdays;
	}

	public void setWorkdays(String workdays)
	{
		this.workdays = workdays;
	}

	public String getHolidays()
	{
		return holidays;
	}

	public void setHolidays(String holidays)
	{
		this.holidays = holidays;
	}

	public String getCurrentDate()
	{
		String workday = String.format("%1$te.%1$tm.%1$tY", DateHelper.getCurrentDate().getTime());
		if(workday.substring(0, workday.indexOf(".")).length() == 1)
			workday = "0" + workday;
		return workday;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators (
				new RequiredFieldValidator("Пожалуйста, введите значение в поле \"Название календаря\"."),
				new RegexpFieldValidator(".{0,256}", "Название календаря должно содержать не более 256 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ТБ");
		fieldBuilder.setName("departmentId");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
