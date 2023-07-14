package com.rssl.phizic.web.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.SessionDataParameterForm;

/**
 * @author Gainanov
 * @ created 26.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class EditMailForm extends SessionDataParameterForm
{
	public static final Form MAIL_FORM     = createForm();

	public String recipientId;
	public String mailId;

	public String getMailId()
	{
		return mailId;
	}

	public void setMailId(String mailId)
	{
		this.mailId = mailId;
	}

	public String getRecipientId()
	{
		return recipientId;
	}

	public void setRecipientId(String recipientId)
	{
		this.recipientId = recipientId;
	}

	protected static FormBuilder fieldsBuid()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Номер
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер");
		fieldBuilder.setName("num");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators();
		fb.addField(fieldBuilder.build());

		// Тема
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тема");
		fieldBuilder.setName("subject");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,40}", "Заголовок должен быть не более 40 символов")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Получатель");
		fieldBuilder.setName("recipient");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		// Важность
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Важность");
		fieldBuilder.setName("important");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		return fb;
	}

	private static Form createForm()
	{
		FormBuilder fb = fieldsBuid();;
		return fb.build();
	}

}
