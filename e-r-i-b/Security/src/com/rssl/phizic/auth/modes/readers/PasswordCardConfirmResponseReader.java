package com.rssl.phizic.auth.modes.readers;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.auth.modes.PasswordCardConfirmResponse;
import com.rssl.phizic.security.config.Constants;

import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: rydvanskiy $
 * @ $Revision: 28840 $
 */

public class PasswordCardConfirmResponseReader extends ConfirmResponseReaderBase
{
	private static final Form form = createForm();

	private FieldValuesSource           valuesSource;
	private PasswordCardConfirmResponse response;
	private List<String>                errors;

	public void setValuesSource(FieldValuesSource valuesSource)
	{
		this.valuesSource = valuesSource;
	}

	public boolean read()
	{
		FormProcessor processor = createFormProcessor(form, valuesSource);

		boolean res = processor.process();
		if(res)
		{
			Map<String,Object> result = processor.getResult();
			String password = (String) result.get(Constants.CONFIRM_CARD_PASSWORD_FIELD);
			response = new PasswordCardConfirmResponse(password);
		}
		else
		{
			errors = (List<String>) processor.getErrors();
		}

		return res;
	}

	public ConfirmResponse getResponse()
	{
		return response;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		fb.setName(Constants.CONFIRM_CARD_PASSWORD_FIELD);
		fb.setDescription(" люч");
		fb.setValidators(new RequiredFieldValidator("¬ведите одноразовый пароль с чека."));

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}