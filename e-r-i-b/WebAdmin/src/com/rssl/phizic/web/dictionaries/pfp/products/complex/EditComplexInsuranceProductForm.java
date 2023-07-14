package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditComplexInsuranceProductForm extends EditComplexProductForm
{
	private Long[] insuranceProductIds = new Long[]{};   //�������������� ��������� ���������

	public Long[] getInsuranceProductIds()
	{
		return insuranceProductIds;
	}

	public void setInsuranceProductIds(Long[] insuranceProductIds)
	{
		this.insuranceProductIds = insuranceProductIds;
	}

	protected String getDescription()
	{
		return "����������, ������� �������� ���������� ��������.";
	}

	protected FormBuilder createEditFormBuilder(ProductTypeParameters productTypeParameters)
	{
		FormBuilder formBuilder = super.createEditFormBuilder(productTypeParameters);

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minSum");
		fieldBuilder.setDescription("����������� �����");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		for (Long id: getInsuranceProductIds())
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("productDescrtiptionFor" + id);
			fieldBuilder.setDescription("������������ ��������");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());
		}

		RequiredFieldValidator validator = new RequiredFieldValidator("���������� �������� ���� �� ���� ��������� �������.");
		validator.setEnabledExpression(new ConstantExpression(ArrayUtils.isEmpty(insuranceProductIds)));
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("productDescrtiptions");
		fieldBuilder.setDescription("��������� �������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(validator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minSumInsurance");
		fieldBuilder.setDescription("����������� ����� ���������� ������");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder;
	}
}
