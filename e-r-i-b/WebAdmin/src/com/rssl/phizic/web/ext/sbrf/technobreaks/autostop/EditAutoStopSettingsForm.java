package com.rssl.phizic.web.ext.sbrf.technobreaks.autostop;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.List;

/**
 * Форма настройки предельного количества ошибок для внешних систем
 * @author Pankin
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditAutoStopSettingsForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	private List<String> systemTypes;

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	public List<String> getSystemTypes()
	{
		return systemTypes;
	}

	public void setSystemTypes(List<String> systemTypes)
	{
		this.systemTypes = systemTypes;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setName(AutoTechnoBreakConfig.ALLOW_OFFLINE_PAYMENTS);
		fieldBuilder.setDescription("Разрешить оффлайн-платежи");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setName(AutoTechnoBreakConfig.ALLOW_GFL_FOR_EACH_PRODUCT);
		fieldBuilder.setDescription("Разрешить запрос GFL по каждому из продуктов");
		formBuilder.addField(fieldBuilder.build());

		for (AutoStopSystemType systemType : AutoStopSystemType.values())
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.setName(AutoTechnoBreakConfig.ERROR_LIMIT_PREFIX + systemType.name());
			fieldBuilder.setDescription(systemType.getDescription());
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(AutoTechnoBreakConfig.BREAK_DURATION_PREFIX + systemType.name());
			fieldBuilder.setDescription(systemType.getDescription());
			fieldBuilder.setType(LongType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();
	}
}
