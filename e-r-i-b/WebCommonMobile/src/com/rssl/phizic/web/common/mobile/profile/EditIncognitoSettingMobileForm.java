package com.rssl.phizic.web.common.mobile.profile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author lepihina
 * @ created 16.06.14
 * $Author$
 * $Revision$
 * Форма для установки/получения статуса «инкогнито» в мобильном приложении
 */
public class EditIncognitoSettingMobileForm extends ActionFormBase
{
	public static final Form EDIT_FORM = createForm();

	private Boolean incognito;

	/**
	 * @return настройка инкогнито
	 */
	public Boolean getIncognito()
	{
		return incognito;
	}

	/**
	 * @param incognito настройка инкогнито
	 */
	public void setIncognito(Boolean incognito)
	{
		this.incognito = incognito;
	}

	private static Form createForm()
	{
		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("incognitoSetting");
		fieldBuilder.setDescription("Настройка инкогнито");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
