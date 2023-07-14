package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;

/**
 * @author bogdanov
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditProviderSummRestrictionForm extends EditServiceProviderFormBase
{
	public static final Form SUMM_RESTRICTION_FORM = createForm();

	public static final String MIN_SUM_RESTR = "minSum";
	public static final String MAX_SUM_RESTR = "maxSum";

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		//������������� ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("id");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		// ����������� �����
		MoneyFieldValidator minAmountValidator = new MoneyFieldValidator();
		minAmountValidator.setParameter("maxValue", "9999999.99");
		minAmountValidator.setMessage("������� ����������� ����� �������� � ���������� �������: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� �����");
		fieldBuilder.setName(MIN_SUM_RESTR);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(minAmountValidator);
		fb.addField(fieldBuilder.build());

		// ������������ �����
		MoneyFieldValidator maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999.99");
		maxAmountValidator.setMessage("������� ������������ ����� �������� � ���������� �������: #######.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ �����");
		fieldBuilder.setName(MAX_SUM_RESTR);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(maxAmountValidator);
		fb.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareValidator.setBinding(CompareValidator.FIELD_O1, MIN_SUM_RESTR);
		compareValidator.setBinding(CompareValidator.FIELD_O2, MAX_SUM_RESTR);
		compareValidator.setMessage("������������ ����� �������� ������ ���� ������ ����������� ����� ��������.");
		fb.addFormValidators(compareValidator);

		return fb.build();
	}
}
