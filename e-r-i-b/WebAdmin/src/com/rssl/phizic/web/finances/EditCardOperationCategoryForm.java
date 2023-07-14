package com.rssl.phizic.web.finances;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.ListLongParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Koptyaev
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditCardOperationCategoryForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		ListLongParser validateParser = new ListLongParser("Пожалуйста, введите корректный список MCC-кодов.");
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		RequiredFieldValidator requiredColorFieldValidator = new RequiredFieldValidator("Введите цвет категории");
		requiredColorFieldValidator.setEnabledExpression(new RhinoExpression("form.visibility == true"));

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{1,128}", "Название должно быть не более 128 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("По-умолчанию");
		fieldBuilder.setName("isDefault");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Переводы между своими картами.");
		fieldBuilder.setName("forInternalOperations");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Перевод");
		fieldBuilder.setName("transfer");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип");
		fieldBuilder.setName("type");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.isDefault != true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Видимость");
		fieldBuilder.setName("visibility");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ID в mAPI");
		fieldBuilder.setName("idInmAPI");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,30}", "ID в mAPI должно быть не более 30 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("MCC-коды");
		fieldBuilder.setName("mcc");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				validateParser
		);
		fieldBuilder.setParser(validateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.isDefault !=true && form.forInternalOperations !=true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Цвет категории");
		fieldBuilder.setName("color");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredColorFieldValidator, new RegexpFieldValidator("^[a-fA-F0-9]{6}$", "Укажите цвет категории в формате #FFFFFF."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
