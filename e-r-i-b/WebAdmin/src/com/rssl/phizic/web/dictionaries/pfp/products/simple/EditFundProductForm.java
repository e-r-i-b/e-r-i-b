package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;

/**
 * @author akrenev
 * @ created 12.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditFundProductForm extends EditProductForm
{
	public static Form getEditForm(ProductTypeParameters productTypeParameters)
	{
		FormBuilder formBuilder = EditProductForm.getFormBuilder(productTypeParameters);
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("sumFactor");
		fieldBuilder.setDescription("Максимальная сумма");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.forSomebodyComplex == true"));
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,250}", "Поле Название должно содержать не более 250 символов."));
		formBuilder.addField(fieldBuilder.build());

		for (PortfolioType type: PortfolioType.values())
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(type.name().concat("minSum"));
			fieldBuilder.setDescription("Минимальная сумма инвестиций для портфеля \"" + type.getDescription() + "\"");
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fieldBuilder.build());
		}

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setEnabledExpression(new RhinoExpression("form.forSomebodyComplex != true"));
		fieldBuilder.addValidators(validator);
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".*", "Пожалуйста, введите описание ПИФа без использования клавиши Enter."),
				new RegexpFieldValidator(".{0,500}", "Пожалуйста, введите описание ПИФа не более 500 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("riskId");
		fieldBuilder.setDescription("Риск");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("periodId");
		fieldBuilder.setDescription("Горизонт");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
