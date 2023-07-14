package com.rssl.phizic.web.configure.currency.rates.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.MultiLineTextValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.math.BigInteger;

/**
 * Форрма редиктирования многоязычных текстовок
 * @author komarov
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateConfigureListResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = createForm();

	public static final String PRIVILEGED_RATE_MESSAGE = "privilegedRateMessage";

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PRIVILEGED_RATE_MESSAGE);
		fieldBuilder.setDescription("Сообщение, отображаемое при использовании льготного курса");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new MultiLineTextValidator("Сообщение, отображаемое при использовании льготного курса не должно превышать", 100)
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();

	}
}
