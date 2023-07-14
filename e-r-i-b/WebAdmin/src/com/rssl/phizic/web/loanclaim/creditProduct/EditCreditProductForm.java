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
 * Форма редактирования кредитных продуктов.
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
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName(Constants.NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,25}", "Поле Hазвание должно содержать не более 25 символов.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код продукта");
		fieldBuilder.setName(Constants.CODE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d{1,3}", "Поле Код продукта должен содержать не болие трех цыфр.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Описания кода продукта");
		fieldBuilder.setName(Constants.DESCRIPTION);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Обеспечение");
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
			fieldBuilder.setDescription("Код RUB для ТБ:" + tb);
			fieldBuilder.setName(Constants.RUB + tb);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Код USD для ТБ:" + tb);
			fieldBuilder.setName(Constants.USD + tb);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Код EUR для ТБ:" + tb);
			fieldBuilder.setName(Constants.EUR + tb);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());
		}
		return fb.build();
	}
}
