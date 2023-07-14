package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EmailFieldValidator;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.persons.validators.*;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * форма редактирования email-адреса пользователя
 * @author lukina
 * @ created 27.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditEmailForm extends EditFormBase
{
	public static final Form FORM = createForm();
	private ConfirmableObject confirmableObject;
	private ConfirmStrategy confirmStrategy;

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		LeadingEndingSpacesValidator leadingEndingSpacesValidator = new LeadingEndingSpacesValidator();
		DoubleSpacesValidator doubleSpacesValidator = new DoubleSpacesValidator();
		WordSpaceHyphenValidator wordSpaceHyphenValidator = new WordSpaceHyphenValidator();

		FieldValidator[] standartValidators = new FieldValidator[]{
				new DoubleHyphenValidator(), doubleSpacesValidator,
				wordSpaceHyphenValidator, leadingEndingSpacesValidator
		};
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("email");
		fieldBuilder.setDescription("E-mail");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(standartValidators);
		fieldBuilder.addValidators(
				new RequiredFieldValidator("Пожалуйста, укажите Ваш E-mail."),
				new EmailFieldValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("mailFormat");
		fieldBuilder.setDescription("Формат отправки оповещений");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new EnumFieldValidator<MailFormat>(MailFormat.class));
		fieldBuilder.setParser(new EnumParser<MailFormat>(MailFormat.class));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
