package com.rssl.phizic.web.common.client.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author gladishev
 * @ created 24.08.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListSentMailForm extends ListMailFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
    {
	    FormBuilder formBuilder = createFilter();

	    //Тип письма
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

        return formBuilder.build();
    }
}