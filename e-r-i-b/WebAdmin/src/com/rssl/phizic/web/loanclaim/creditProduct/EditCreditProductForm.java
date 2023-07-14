package com.rssl.phizic.web.loanclaim.creditProduct;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loanclaim.Constants;

import java.util.Set;

/**
 * @author Moshenko
 * @ created 07.01.2014
 * @ $Author$
 * @ $Revision$
 * ����� �������������� ��������� ���������.
 */
public class EditCreditProductForm extends EditFormBase
{
	private Set<String> tbNumbers;

	public Set<String> getTbNumbers()
	{
		return tbNumbers;
	}

	public void setTbNumbers(Set<String> tbNumbers)
	{
		this.tbNumbers = tbNumbers;
	}

	public Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName(Constants.NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,25}", "���� H������� ������ ��������� �� ����� 25 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setName(Constants.CODE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{1,3}", "���� ��� �������� ������ ��������� �� ����� ���� ����.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ���� ��������");
		fieldBuilder.setName(Constants.DESCRIPTION);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����������");
		fieldBuilder.setName(Constants.ENSURING);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		for (String tb:tbNumbers)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(Constants.TB_NAME + tb);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("��� RUB ��� ��:" + tb);
			fieldBuilder.setName(Constants.RUB + tb);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("��� USD ��� ��:" + tb);
			fieldBuilder.setName(Constants.USD + tb);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("��� EUR ��� ��:" + tb);
			fieldBuilder.setName(Constants.EUR + tb);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());
		}
		return fb.build();
	}
}
