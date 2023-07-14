package com.rssl.phizic.web.dictionaries.pfp.targets;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetCountService;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author akrenev
 * @ created 28.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTargetCountForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createEditForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TargetCountService.TARGET_COUNT_PROPERTY);
		fieldBuilder.setDescription("Количество целей");
		fieldBuilder.setType(LongType.INSTANCE.getName());

		NumericRangeValidator numericValidator= new NumericRangeValidator();
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99");
		numericValidator.setMessage("Поле Количество целей должно содержать число от 1 до 99.");

		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   numericValidator);

		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
