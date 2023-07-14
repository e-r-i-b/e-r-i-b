package com.rssl.phizic.web.dictionaries.pfp.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.phizic.business.dictionaries.pfp.configure.DefaultPeriodSettingService;
import com.rssl.phizic.business.dictionaries.pfp.configure.PFPConfigHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author akrenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditDefaultPeriodSettingForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createEditForm();

	private static final String MIN_VALUE = "0";
	private static final String MAX_VALUE = "40"; //при переносе формы в модуль читать из pfp.properties свойство max.planning.year

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createEditForm()
	{
		String defaultPeriodValueFieldDescription = StrutsUtils.getMessage("period.default.label.edit.field.value", "pfpConfigureBundle");
		String numericValidatorMessage = StrutsUtils.getMessage("period.default.label.edit.field.value.validator.numeric.message", "pfpConfigureBundle", MIN_VALUE, MAX_VALUE);

		NumericRangeValidator numericValidator= new NumericRangeValidator();
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, MIN_VALUE);
		numericValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, MAX_VALUE);
		numericValidator.setMessage(numericValidatorMessage);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PFPConfigHelper.PROPERTY_PREFIX.concat(DefaultPeriodSettingService.DEFAULT_PERIOD_PROPERTY));
		fieldBuilder.setDescription(defaultPeriodValueFieldDescription);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(numericValidator);

		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
