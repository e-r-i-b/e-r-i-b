package com.rssl.phizic.web.departments.templates;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Настройка использования кратности суммы при оплате по шаблону в full-версии mAPI
 * @author Dorzhinov
 * @ created 15.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditUseTemplateFactorInFullMAPIForm extends EditPropertiesFormBase
{
	@Override
	public Form getForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Проверять кратность суммы");
		fieldBuilder.setName(DocumentConfig.generateKeyForUseTemplateFactorInFullMAPI(getId()));
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
