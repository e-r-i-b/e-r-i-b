package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditComplexInvestmentProductFormBase extends EditComplexProductForm
{
	private Long[] fundProductIds = new Long[]{};   //идентификаторы ПИФов

	public Long[] getFundProductIds()
	{
		return fundProductIds;
	}

	public void setFundProductIds(Long[] fundProductIds)
	{
		this.fundProductIds = fundProductIds;
	}

	protected FormBuilder createEditFormBuilder(ProductTypeParameters productTypeParameters)
	{
		FormBuilder formBuilder = super.createEditFormBuilder(productTypeParameters);

		FieldBuilder fieldBuilder = new FieldBuilder();

		for (PortfolioType type: PortfolioType.values())
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(type.name().concat("minSum"));
			fieldBuilder.setDescription("Минимальная сумма для портфеля \"" + type.getDescription() + "\"");
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fieldBuilder.build());
		}

		for (Long id: fundProductIds)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("fundProductNameFor" + id);
			fieldBuilder.setDescription("Наименование продукта");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());
		}

		RequiredFieldValidator validator = new RequiredFieldValidator("Необходимо добавить хотя бы один ПИФ.");
		validator.setEnabledExpression(new ConstantExpression(ArrayUtils.isEmpty(fundProductIds)));
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fundProducts");
		fieldBuilder.setDescription("ПИФ");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(validator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder;
	}
}
