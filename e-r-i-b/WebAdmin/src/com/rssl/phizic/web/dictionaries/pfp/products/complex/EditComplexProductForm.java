package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MultiLineTextValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPProductEditFormHelper;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditComplexProductForm extends EditProductFormBase
{
	private static final ConstantExpression TRUE_EXPRESSION = new ConstantExpression(true);

	protected String getDescription()
	{
		return "����������, ������� �������� ��������.";
	}

	protected FormBuilder createEditFormBuilder(ProductTypeParameters productTypeParameters)
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(getDescription()),
								   new MultiLineTextValidator( "�� ����������� ������� �������� ��������. ����������, ������� �� �����", 500));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accountId");
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "�������� �����."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accountName");
		fieldBuilder.setDescription("������������ ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("�������� �����."));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());
		formBuilder.addFields(PFPProductEditFormHelper.getTargetGroupFields());
		formBuilder.addFields(getBaseFormFields());
		formBuilder.addFields(PFPProductEditFormHelper.getTableParametersFields(productTypeParameters.getTableParameters(), TRUE_EXPRESSION));

		formBuilder.addFormValidators(PFPProductEditFormHelper.getTargetGroupValidator(TRUE_EXPRESSION));
		return formBuilder;
	}

	public Form createEditForm(ProductTypeParameters productTypeParameters)
	{
		return createEditFormBuilder(productTypeParameters).build();
	}
}
