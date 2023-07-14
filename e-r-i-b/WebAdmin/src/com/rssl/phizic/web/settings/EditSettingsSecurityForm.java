package com.rssl.phizic.web.settings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import static com.rssl.phizic.config.limits.ClientSecurityLevelConfig.*;

/**
 * @author osminin
 * @ created 14.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * форма редактирования настроек уровня безопасности
 */
public class EditSettingsSecurityForm extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();

	@Override
	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(START_DATE_PROPERTY_KEY);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("дата начала действия");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LESS_COUNT_DAYS_PROPERTY_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Телефон изменился менее n дней");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANGE_PHONE_BY_ATM_PROPERTY_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("устройство самообслуживания");
		formBuilder.addField(fieldBuilder.build());
		
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANGE_PHONE_BY_ERIB_PROPERTY_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("ЕРИБ");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANGE_PHONE_BY_VSP_PROPERTY_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("ВСП");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANGE_PHONE_BY_CC_PROPERTY_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("КЦ");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
