package com.rssl.phizic.web.finances;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.ips.IPSConstants;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма настроек категории «Переводы между своими картами»
 * @author lepihina
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditInternalOperationsSettingsForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("MCC-коды для операций карта-карта");
		fieldBuilder.setName(IPSConstants.CARD_TO_CARD_OPERATIONS_MCC_CODES);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				requiredValidator,
				new RegexpFieldValidator(".{1,1000}", "Поле «MCC-коды для операций карта-карта» должно содержать не более 1000 символов."),
				new RegexpFieldValidator("\\d+((, ){1,1}\\d+)*", "Неправильный формат. Mcc-коды должны перечисляться через запятую. Например: 6012, 6532, 6536")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время между переводами не больше");
		fieldBuilder.setName(IPSConstants.INTERNAL_OPERATIONS_MAX_TIME);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(
				requiredValidator,
				new RegexpFieldValidator("^(\\d{1,3})?$", "Поле «Время между переводами» должно содержать не более 3 цифр.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
