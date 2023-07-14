package com.rssl.phizic.operations.ext.sbrf.readers;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.auth.modes.readers.ConfirmResponseReaderBase;
import com.rssl.phizic.operations.ext.sbrf.strategy.iPasCapConfirmResponse;
import com.rssl.phizic.security.config.Constants;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Moshenko
 * Date: 15.05.12
 * Time: 18:39
 * –идер дл€ стратегии iPasCapConfrimStrategy
 */
public class CapConfirmResponseReader extends ConfirmResponseReaderBase
{
	private static final Form form = createForm();

	private FieldValuesSource      valuesSource;
	private List<String>           errors;
	private iPasCapConfirmResponse response;

	public List<String> getErrors()
	{
		return errors;
	}

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
			String password = (String) result.get(Constants.CONFIRM_CAP_PASSWORD_FIELD);
			response = new iPasCapConfirmResponse(password);
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

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		fb.setName(Constants.CONFIRM_CAP_PASSWORD_FIELD);
		fb.setDescription("CAP пароль");
		fb.setValidators(new RequiredFieldValidator("¬ведите одноразовый CAP пароль с карты."));

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
