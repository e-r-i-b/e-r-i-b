package com.rssl.phizic.auth.modes.readers;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.auth.modes.CryptoConfirmResponse;

import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: krenev $
 * @ $Revision: 18744 $
 */

public class CryptoConfirmResponseReader extends ConfirmResponseReaderBase
{
	private static final String SIGNATURE_FIELD = "$$cryptoSignature";
	private static final Form form = createForm();

	private FieldValuesSource     valuesSource;
	private CryptoConfirmResponse response;
	private List<String>          errors;

	public void setValuesSource(FieldValuesSource valuesSource)
	{
		this.valuesSource = valuesSource;
	}

	public boolean read()
	{
		FormProcessor processor = createFormProcessor(form, valuesSource);

		boolean res = processor.process();
		if (res)
		{
			Map<String, Object> result = processor.getResult();
			String signature = (String) result.get(SIGNATURE_FIELD);
			response = new CryptoConfirmResponse(signature);
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

		fb.setName(SIGNATURE_FIELD);
		fb.setDescription("Подпись");
		fb.setValidators(new RequiredFieldValidator("Ошибка получения подписи"));

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}