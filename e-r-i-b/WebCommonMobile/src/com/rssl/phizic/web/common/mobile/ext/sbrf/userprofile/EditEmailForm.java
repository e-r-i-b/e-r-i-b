package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author EgorovaA
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования email клиента
 */
public class EditEmailForm extends EditFormBase
{
	private String email;
	private String mailFormat;

	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("email");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new EmailFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("mailFormat");
		fieldBuilder.setDescription("Формат отправки оповещений");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("Отсутствует значение формата отправки оповещений."),
				new EnumFieldValidator<MailFormat>(MailFormat.class, "Некорректное значение формата отправки оповещений."));
		fieldBuilder.setParser(new EnumParser<MailFormat>(MailFormat.class));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMailFormat()
	{
		return mailFormat;
	}

	public void setMailFormat(String mailFormat)
	{
		this.mailFormat = mailFormat;
	}
}
