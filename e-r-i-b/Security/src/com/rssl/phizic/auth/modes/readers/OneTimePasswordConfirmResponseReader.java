package com.rssl.phizic.auth.modes.readers;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.auth.modes.OneTimePasswordConfirmResponse;
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

public abstract class OneTimePasswordConfirmResponseReader extends ConfirmResponseReaderBase
{
	protected List<String> errors;
	protected FieldValuesSource valuesSource;
	protected OneTimePasswordConfirmResponse response;

	protected abstract OneTimePasswordConfirmResponse createPasswordResponse(String password);

	protected abstract String getConfirmPasswordField();

	protected abstract Form getForm();

	public boolean read()
	{
		FormProcessor processor = createFormProcessor(getForm(), valuesSource);

		boolean res = processor.process();
		if(res)
		{
			Map<String,Object> result = processor.getResult();
			String password = (String) result.get(getConfirmPasswordField());
			response = createPasswordResponse(password);
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

	public void setValuesSource(FieldValuesSource valuesSource)
	{
		this.valuesSource = valuesSource;
	}
}
