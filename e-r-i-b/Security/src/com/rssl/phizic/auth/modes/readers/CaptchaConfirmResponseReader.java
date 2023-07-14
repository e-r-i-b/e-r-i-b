package com.rssl.phizic.auth.modes.readers;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.CaptchaConfirmResponse;
import com.rssl.phizic.auth.modes.ConfirmResponse;

import java.util.List;
import java.util.Map;

/**
 * @author Krenev
 * @ created 02.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class CaptchaConfirmResponseReader extends ConfirmResponseReaderBase
{
	private FieldValuesSource valuesSource;
	private ConfirmResponse response;
	private static final Form form = createForm();

	private List<String>              errors;
	private static final String CONFIRM_CAPTCA_CODE_FIELD = "$$confirmCaptchaCode";

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
			String code = (String) result.get(CONFIRM_CAPTCA_CODE_FIELD);
			response = new CaptchaConfirmResponse(code);
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

		fb.setName(CONFIRM_CAPTCA_CODE_FIELD);
		fb.setDescription("Код");
		fb.setValidators(new RequiredFieldValidator("Введите введите символы, представленные на картинке"));

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
