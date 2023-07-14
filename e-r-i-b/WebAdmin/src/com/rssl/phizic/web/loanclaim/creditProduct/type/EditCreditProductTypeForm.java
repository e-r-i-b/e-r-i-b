package com.rssl.phizic.web.loanclaim.creditProduct.type;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loanclaim.Constants;

/**
 * @author Moshenko
 * @ created 27.12.2013
 * @ $Author$
 * @ $Revision$
 * Форма редактирования типов кредитных продуктов.
 */
public class EditCreditProductTypeForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
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
		fieldBuilder.setDescription("Код типа продукта");
		fieldBuilder.setName(Constants.CODE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1}", "Поле Код типа продукта должно содержать только одну цыфру.")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}


}
