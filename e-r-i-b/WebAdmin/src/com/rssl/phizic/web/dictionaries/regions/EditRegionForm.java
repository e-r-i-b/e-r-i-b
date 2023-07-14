package com.rssl.phizic.web.dictionaries.regions;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.types.StringType;

/**
 * @author khudyakov
 * @ created 03.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class EditRegionForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private Long parentId;

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	private static Form createForm()
	{
		FormBuilder  formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор");
		fieldBuilder.setName("synchKey");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators
		(
			new RequiredFieldValidator(),
			new RegexpFieldValidator("[^а-яА-ЯёЁ]*", "Поле Идентификатор не должно содержать буквы русского алфавита"),
			new RegexpFieldValidator(".{1,20}", "Поле Идентификатор не должно превышать 20 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators
		(
			new RequiredFieldValidator(), new RegexpFieldValidator(".{1,128}", "Поле Наименование не должно превышать 128 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название родительского региона");
		fieldBuilder.setName("parent");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код ТБ");
		fieldBuilder.setName("codeTB");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.parent==null || form.parent==''"));
		fieldBuilder.addValidators(new RegexpFieldValidator(".{2}", "Поле Код ТБ должно состоять из 2 символов"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код для оплаты МП (mAPI)");
		fieldBuilder.setName("providerCodeMAPI");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators
		(
			new RegexpFieldValidator(".{1,128}", "Поле \"Код для оплаты МП (mAPI)\" не должно превышать 128 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код для оплаты УС(atmAPI)");
		fieldBuilder.setName("providerCodeATM");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators
		(
			new RegexpFieldValidator(".{1,128}", "Поле \"Код для оплаты УС(atmAPI)\" не должно превышать 128 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}