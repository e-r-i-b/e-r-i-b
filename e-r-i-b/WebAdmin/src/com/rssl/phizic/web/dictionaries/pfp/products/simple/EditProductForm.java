package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPProductEditFormHelper;

/**
 * @author akrenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditProductForm extends EditProductFormBase
{
	private static final RhinoExpression NOT_FOR_COMPLEX_EXPRESSION = new RhinoExpression("form.forSomebodyComplex == false");

	protected static FormBuilder getFormBuilder(ProductTypeParameters productTypeParameters)
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(PFPProductEditFormHelper.getIncomeFields());
		formBuilder.addFields(PFPProductEditFormHelper.getTargetGroupFields());
		formBuilder.addFields(getBaseFormFields());

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("forComplex");
		fieldBuilder.setDescription("Доступность клиенту");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("complexKind");
		fieldBuilder.setDescription("тип комплексного продукта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.forSomebodyComplex == true"));
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("forSomebodyComplex");
		fieldBuilder.setDescription("тип комплексного продукта");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(PFPProductEditFormHelper.getTableParametersFields(productTypeParameters.getTableParameters(), NOT_FOR_COMPLEX_EXPRESSION));
		formBuilder.addFields(PFPProductEditFormHelper.getChartParametersFields(NOT_FOR_COMPLEX_EXPRESSION));
		formBuilder.addFields(getImageField());
		
		formBuilder.addFormValidators(PFPProductEditFormHelper.getIncomeFormValidators());
		formBuilder.addFormValidators(PFPProductEditFormHelper.getTargetGroupValidator(new RhinoExpression("form." + ENABLED_PARAMETER_NAME + " == true && form.forSomebodyComplex == false")));
		return formBuilder;
	}
}
