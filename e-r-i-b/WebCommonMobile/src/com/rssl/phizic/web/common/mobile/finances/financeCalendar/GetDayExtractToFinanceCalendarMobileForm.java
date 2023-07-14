package com.rssl.phizic.web.common.mobile.finances.financeCalendar;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.client.finances.financeCalendar.GetDayExtractToFinanceCalendarForm;

/**
 * @author EgorovaA
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 */
public class GetDayExtractToFinanceCalendarMobileForm extends GetDayExtractToFinanceCalendarForm
{
	public static final Form DAY_EXTRACT_FORM = createForm();

	private static final String DATE_FORMAT = "dd.MM.yyyy";
	private String onDate;
	private String selectedCardIds;

	public String getOnDate()
	{
		return onDate;
	}

	public void setOnDate(String onDate)
	{
		this.onDate = onDate;
	}

	public String getSelectedCardIds()
	{
		return selectedCardIds;
	}

	public void setSelectedCardIds(String selectedCardIds)
	{
		this.selectedCardIds = selectedCardIds;
	}

	protected static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		DateParser dataParser = new DateParser(DATE_FORMAT);

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onDate");
		fieldBuilder.setDescription("Месяц");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedCardIds");
		fieldBuilder.setDescription("Идентификаторы карт");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
