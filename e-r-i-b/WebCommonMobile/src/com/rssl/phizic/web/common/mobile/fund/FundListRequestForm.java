package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author usachev
 * @ created 05.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма для просмотра списка исходящих запросов
 */
public class FundListRequestForm extends ListFormBase
{
	private final static String FROM_DATE = "from_date";

	public static Form FORM = create();

	public void setFromDate(String fromDate)
	{
		getFilters().put(FROM_DATE, fromDate);
	}

	private static Form create()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FROM_DATE);
		fieldBuilder.setDescription("Дата начала выборки");
		fieldBuilder.setType("date");

		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
