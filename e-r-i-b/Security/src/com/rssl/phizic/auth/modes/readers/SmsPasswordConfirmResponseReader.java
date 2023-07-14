package com.rssl.phizic.auth.modes.readers;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.auth.modes.OneTimePasswordConfirmResponse;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmResponse;
import com.rssl.phizic.security.config.Constants;

import java.util.List;
import java.util.Map;

/**
 * @author eMakarov
 * @ created 17.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsPasswordConfirmResponseReader extends OneTimePasswordConfirmResponseReader
{
	private static final Form form = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		fb.setName(Constants.CONFIRM_SMS_PASSWORD_FIELD);
		fb.setDescription("sms-пароль");
		fb.setValidators(new RequiredFieldValidator("¬ведите пароль, полученный через sms."));

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	protected String getConfirmPasswordField()
	{
		return Constants.CONFIRM_SMS_PASSWORD_FIELD;
	}

	protected Form getForm()
	{
		return form;
	}

	protected SmsPasswordConfirmResponse createPasswordResponse(String password)
	{
		return new SmsPasswordConfirmResponse(password);
	}
}
