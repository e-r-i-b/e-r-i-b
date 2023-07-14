package com.rssl.phizic.web.client.contacts;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;

/**
 * Просмотр списка мобильных контактов
 * @author Dorzhinov
 * @ created 28.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMobileContactForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Имя");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();

	}
}
