package com.rssl.phizic.web.dictionaries.pfp.products.simple.trustManaging;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPProductEditFormHelper;

/**
 * @author akrenev
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class EditTrustManagingProductForm extends EditProductFormBase
{

	private static final ConstantExpression TRUE_EXPRESSION = new ConstantExpression(true);

	public static Form getEditForm(ProductTypeParameters productTypeParameters)
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

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
			fieldBuilder.setDescription("Минимальная сумма для портфеля \"" + type.getDescription() + "\"");
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fieldBuilder.build());
		}

		formBuilder.addFields(PFPProductEditFormHelper.getIncomeFields());

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

		formBuilder.addFields(PFPProductEditFormHelper.getTargetGroupFields());
		formBuilder.addFields(PFPProductEditFormHelper.getChartParametersFields(TRUE_EXPRESSION));
		formBuilder.addFields(PFPProductEditFormHelper.getTableParametersFields(productTypeParameters.getTableParameters(), TRUE_EXPRESSION));
		formBuilder.addFields(getBaseFormFields());
		formBuilder.addFields(getImageField());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".*", "Пожалуйста, введите описание вклада без использования клавиши Enter."),
				new RegexpFieldValidator(".{0,500}", "Пожалуйста, введите описание вклада не более 500 символов."));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFormValidators(PFPProductEditFormHelper.getIncomeFormValidators());
		formBuilder.addFormValidators(PFPProductEditFormHelper.getTargetGroupValidator(new RhinoExpression("form." + ENABLED_PARAMETER_NAME + " == true")));
		return formBuilder.build();
	}

}
