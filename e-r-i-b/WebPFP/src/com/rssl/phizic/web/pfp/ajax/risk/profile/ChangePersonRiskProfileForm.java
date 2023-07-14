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
 * ����� ��������� ������������� ������� � ��������
 */

public class ChangePersonRiskProfileForm extends EditFormBase
{
	/**
	 * ������� ���������� ����� �� ������ ����-�������
	 * @param riskProfile ����-�������
	 * @return ���������� �����
	 */
	public static Form createForm(RiskProfile riskProfile)
	{
		FormBuilder fb = new FormBuilder();

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("(\\d{0,2})|100", "�� ����������� ������� ����������� ��������� � ��������. ����������, ������� ����� �� 0 �� 100.");

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
