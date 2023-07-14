package com.rssl.phizic.web.common.client.mail;


import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author Gainanov
 * @ created 26.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class ListMailForm extends ListMailFormBase
{
	public static final String CATEGORY_INBOX = "i";
	public static final String CATEGORY_OUTBOX = "o";

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

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("important");
		fieldBuilder.setDescription("Обязательно для прочтения");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("state");
		fieldBuilder.setDescription("статус");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
