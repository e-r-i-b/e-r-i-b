package com.rssl.phizic.web.client.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author bogdanov
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class CheckOldPasswordForm extends EditFormBase
{
	public static final Form CREATE_FORM = createForm();

	private String pswd;
	private String pswd2;
	private boolean showPassword;
	private String pageToken;
	private String nameFieldError;
	private String textError;
	private int hintDelay;

	public String getPswd2()
	{
		return pswd2;
	}

	public void setPswd2(String pswd2)
	{
		this.pswd2 = pswd2;
	}

	public String getPswd()
	{
		return pswd;
	}

	public void setPswd(String pswd)
	{
		this.pswd = pswd;
	}

	public boolean isShowPassword()
	{
		return showPassword;
	}

	public void setShowPassword(boolean showPassword)
	{
		this.showPassword = showPassword;
	}

	public String getPageToken()
	{
		return pageToken;
	}

	public void setPageToken(String pageToken)
	{
		this.pageToken = pageToken;
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

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("pswd");
		fieldBuilder.setDescription("Введите новый пароль");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new PasswordStrategyValidator("csa_client_password"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("pswd2");
		fieldBuilder.setDescription("Повторите новый пароль");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "pswd");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "pswd2");
		compareValidator.setMessage("Введенные пароли должны совпадать!");

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}

	public void setHintDelay(int hintDelay)
	{
		this.hintDelay = hintDelay;
	}

	public int getHintDelay()
	{
		return hintDelay;
	}
}
