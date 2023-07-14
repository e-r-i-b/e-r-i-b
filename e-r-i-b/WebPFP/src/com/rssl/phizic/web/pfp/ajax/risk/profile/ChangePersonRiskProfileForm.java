package com.rssl.phizic.web.pfp.ajax.risk.profile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author komarov
 * @ created 26.06.2013 
 * @ $Author$
 * @ $Revision$
 *
 * Форма изменения распределения средств в портфеле
 */

public class ChangePersonRiskProfileForm extends EditFormBase
{
	/**
	 * создает логическую форму на основе риск-профиля
	 * @param riskProfile риск-профиль
	 * @return логическая форма
	 */
	public static Form createForm(RiskProfile riskProfile)
	{
		FormBuilder fb = new FormBuilder();

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("(\\d{0,2})|100", "Вы неправильно указали соотношение продуктов в портфеле. Пожалуйста, введите цифры от 0 до 100.");

		for(ProductType type : riskProfile.getProductsWeights().keySet())
		{
			FieldBuilder fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription(type.getDescription());
			fieldBuilder.setName(type.name());
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.addValidators (
					requiredFieldValidator,
					regexpFieldValidator
			);
			fb.addField(fieldBuilder.build());
		}

		return fb.build();
	}
}
