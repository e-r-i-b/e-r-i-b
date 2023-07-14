package com.rssl.phizic.web.loanReport;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.loanreport.CreditBureauConstants;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма настройки идентификатора поставщика услуги для ОКБ
 * @author Rtischeva
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */
public class OKBProviderIdEditForm extends EditPropertiesFormBase
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
		fieldBuilder.setName(CreditBureauConstants.SERVICE_PROVIDER_OKB_ID);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Идентификатор поставщика услуг для ОКБ");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{32}", "Идентификатор поставщика услуг для ОКБ должен содержать 32 символа"), new RegexpFieldValidator("[0-9a-fA-F]*", "Идентификатор поставщика услуг для ОКБ должен содержать только буквы латинского алфавита и цифры"));
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
