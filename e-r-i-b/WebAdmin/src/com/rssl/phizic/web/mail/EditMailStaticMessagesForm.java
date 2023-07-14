package com.rssl.phizic.web.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.SessionDataParameterForm;

/**
 * @author gladishev
 * @ created 22.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditMailStaticMessagesForm extends SessionDataParameterForm
{
	public static final String FORM_TEXT_FIELD = "formText";
	public static final String MESSAGE_TEXT_FIELD = "messageText";
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст формы обращения клиента");
		fieldBuilder.setName(FORM_TEXT_FIELD);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0,1000}", "Текст подтверждения об отправке сообщения должен быть не более 1000 символов")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст подтверждения об отправке сообщения");
		fieldBuilder.setName(MESSAGE_TEXT_FIELD);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0,500}", "Текст подтверждения об отправке сообщения должен быть не более 500 символов")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
