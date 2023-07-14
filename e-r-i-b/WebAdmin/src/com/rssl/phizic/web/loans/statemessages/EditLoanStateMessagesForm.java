package com.rssl.phizic.web.loans.statemessages;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanStateMessagesForm extends EditFormBase
{
	private String key;
	public static final Form EDIT_FORM     = createForm();

	public String getKey()
	{
		return key;
	}

	public void setKey(String tmpKey)
	{
		this.key = tmpKey;
	}	

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст сообщения");
		fieldBuilder.setName("value");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,256}", "Текст сообщения должен быть не более 256 символов")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
