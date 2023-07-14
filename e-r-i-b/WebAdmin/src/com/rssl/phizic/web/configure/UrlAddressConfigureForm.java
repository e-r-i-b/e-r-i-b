package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.util.BankInfoUtil;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author lukina
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class UrlAddressConfigureForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BankInfoUtil.FNS_REGISTRATION_URL);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Адрес регистрации индивидуального частного предпринимателя на сайте ФНС");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "Адрес регистрации индивидуального частного предпринимателя на сайте ФНС не должен превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();

	}
}
