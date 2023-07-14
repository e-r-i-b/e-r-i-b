package com.rssl.phizic.web.client.calendars;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.phizic.web.common.FilterActionForm;

/**
 * Форма для получение списка рабочих дней
 * @author basharin
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */

public class AsyncGetWorkDaysForm extends FilterActionForm
{
	public static final String FROM_DATE_FIELD = "fromDate";
	public static final String TO_DATE_FIELD = "toDate";
	public static final String TB_FIELD = "tb";

	public static Form filetrForm = createForm();

	private String jsonDays;

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

	    FieldBuilder fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName(FROM_DATE_FIELD);
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName(TO_DATE_FIELD);
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(StringType.INSTANCE.getName());
		fb.setName(TB_FIELD);
		formBuilder.addField(fb.build());

	    return formBuilder.build();
    }

	public String getJsonDays()
	{
		return jsonDays;
	}

	public void setJsonDays(String jsonDays)
	{
		this.jsonDays = jsonDays;
	}
}
