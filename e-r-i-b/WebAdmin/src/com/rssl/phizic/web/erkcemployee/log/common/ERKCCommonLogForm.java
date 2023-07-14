package com.rssl.phizic.web.erkcemployee.log.common;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.ext.sbrf.logging.DownloadCommonLogForm;

/**
 * @author akrenev
 * @ created 31.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCCommonLogForm extends DownloadCommonLogForm
{
	static final Form FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		addLogTypeFilters(formBuilder);

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("application");
		fieldBuilder.setDescription("Приложение");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("sessionId");
		fieldBuilder.setDescription("Идентификатор сессии");
		formBuilder.addField(fieldBuilder.build());

		addDateFilters(formBuilder);

		return formBuilder.build();
	}
}
