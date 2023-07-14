package com.rssl.phizic.web.configure.addressBookSettings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма настройки синхронизации УАК
 *
 * @author shapin
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */
public class EditSynchronizationSettingsForm extends EditPropertiesFormBase
{
	private static Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AddressBookConfig.TIME_FOR_START_SYNCHRONIZATION);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Время начала работы");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{2}\\:\\d{2}", "Время должно быть в формате ЧЧ:ММ")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AddressBookConfig.SHOW_SB_CLIENT_ATTRIBUTE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Отображать признак клиента Сбербанка в АК");
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
