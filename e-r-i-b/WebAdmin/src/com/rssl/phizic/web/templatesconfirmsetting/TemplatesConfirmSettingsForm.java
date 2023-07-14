package com.rssl.phizic.web.templatesconfirmsetting;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.departments.Department;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.web.ext.sbrf.DepartmentViewUtil;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author vagin
 * @ created 02.10.2012
 * @ $Author$
 * @ $Revision$
 * Форма задания настройки подтверждения операций по шаблну СМС-паролем
 */
public class TemplatesConfirmSettingsForm extends EditPropertiesFormBase
{
	private String region;

	public Form getForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Подтверждать операции по шаблонам смс-паролем");
		fieldBuilder.setName(DocumentConfig.generateKeyForTemplatesConfirmSetting(getRegion()));
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public String getRegion()
	{
		return region;
	}
}
