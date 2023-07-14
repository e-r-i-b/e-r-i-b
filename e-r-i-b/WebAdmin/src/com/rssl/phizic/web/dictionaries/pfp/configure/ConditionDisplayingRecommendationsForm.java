package com.rssl.phizic.web.dictionaries.pfp.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.pfp.PFPConfigurationHelper;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author komarov
 * @ created 15.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ConditionDisplayingRecommendationsForm extends EditPropertiesFormBase
{
	private static final Form CONDITION_DISPLAYING_RECOMMENDATIONS_FORM = createForm();

	@Override
	public Form getForm()
	{
		return CONDITION_DISPLAYING_RECOMMENDATIONS_FORM;
	}

	private static Form createForm()
	{
		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PFPConfigurationHelper.RECOMMENDATIONS_START_CAPITAL);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Рекомендация: Увеличение доходности портфеля «Стартовый капитал»");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				requiredValidator,
				new RegexpFieldValidator("^(\\d*((\\.|,)\\d{1,2})?)$", "Пожалуйста, введите увеличение доходности портфеля «Стартовый капитал» в верном формате. Например, 12.55"),
				new RegexpFieldValidator("^(100|100\\.00|(\\d{1,2}((\\.|,)\\d{1,2})?))$", "Пожалуйста, укажите увеличение доходности портфеля «Стартовый капитал» не более 100 процентов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PFPConfigurationHelper.RECOMMENDATIONS_QUARTERLY_INVEST);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Рекомендация: Увеличение доходности портфеля «Ежеквартальные вложения»");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				requiredValidator,
				new RegexpFieldValidator("^(\\d*((\\.|,)\\d{1,2})?)$", "Пожалуйста, введите увеличение доходности портфеля «Ежеквартальные вложения» в верном формате. Например, 12.55"),
				new RegexpFieldValidator("^(100|100\\.00|(\\d{1,2}((\\.|,)\\d{1,2})?))$", "Пожалуйста, укажите увеличение доходности портфеля «Ежеквартальные вложения» не более 100 процентов.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
