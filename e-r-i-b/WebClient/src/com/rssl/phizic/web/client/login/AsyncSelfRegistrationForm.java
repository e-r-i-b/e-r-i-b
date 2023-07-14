package com.rssl.phizic.web.client.login;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * форма для асинхронной передачи данных для перелогина
 * @author basharin
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncSelfRegistrationForm extends EditFormBase
{
	public static final Form FORM = buildForm();

	private String nameFieldError;
	private String textError;

	private String redirect;

	public static Form buildForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("AuthLoginInput");
		fieldBuilder.setDescription("Логин");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("AuthPasswordInput");
		fieldBuilder.setDescription("Пароль");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public String getNameFieldError()
	{
		return nameFieldError;
	}

	public void setNameFieldError(String nameFieldError)
	{
		this.nameFieldError = nameFieldError;
	}

	public String getTextError()
	{
		return textError;
	}

	public void setTextError(String textError)
	{
		this.textError = textError;
	}

	public String getRedirect()
	{
		return redirect;
	}

	public void setRedirect(String redirect)
	{
		this.redirect = redirect;
	}
}
