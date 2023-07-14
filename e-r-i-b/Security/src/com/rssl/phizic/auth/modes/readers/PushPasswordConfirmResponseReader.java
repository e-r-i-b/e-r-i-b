package com.rssl.phizic.auth.modes.readers;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.auth.modes.PushPasswordConfirmResponse;
import com.rssl.phizic.security.config.Constants;

import java.util.List;
import java.util.Map;

/**
 * @author basharin
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushPasswordConfirmResponseReader extends OneTimePasswordConfirmResponseReader
{
	private static final Form form = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		fb.setName(Constants.CONFIRM_PUSH_PASSWORD_FIELD);
		fb.setDescription("push-пароль");
		fb.setValidators(new RequiredFieldValidator("Введите пароль, полученный через push-сообщение."));

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	protected String getConfirmPasswordField()
	{
		return Constants.CONFIRM_PUSH_PASSWORD_FIELD;
	}

	protected Form getForm()
	{
		return form;
	}

	protected PushPasswordConfirmResponse createPasswordResponse(String password)
	{
		return new PushPasswordConfirmResponse(password);
	}
}
