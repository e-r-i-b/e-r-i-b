package com.rssl.phizic.web.common.client.ext.sbrf.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.client.mail.ListMailFormBase;

/**
 * @author kligina
 * @ created 20.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveMailForm extends ListMailFormBase
{
	public static final Form FILTER_FORM = createForm();

		private static Form createForm()
		{
			FormBuilder formBuilder = createFilter();

			FieldBuilder fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("mailType");
			fieldBuilder.setType("string");
			fieldBuilder.setDescription("Тип письма");
			formBuilder.addField(fieldBuilder.build());

			return formBuilder.build();
		}
}
