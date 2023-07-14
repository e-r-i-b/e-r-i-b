package com.rssl.phizic.web.templatesfactor;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author vagin
 * @ created 06.06.2012
 * @ $Author$
 * @ $Revision$
 * Ёкшен дл€ задани€ кратности суммы шаблонов
 */
public class TemplatesFactorForm extends EditPropertiesFormBase
{
	private String region;

	@Override
	public Form getForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(" ратность суммы дл€ шаблонов");
		fieldBuilder.setName(DocumentConfig.generateKeyForTemplatesFactor(getRegion()));
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d*","«начение должно содержать только цифры.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}
}
