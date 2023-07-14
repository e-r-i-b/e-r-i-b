package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditComplexIMAInvestmentProductForm extends EditComplexInvestmentProductFormBase
{
	private Long[] imaProductIds = new Long[]{};   //идентификаторы ПИФов

	public Long[] getImaProductIds()
	{
		return imaProductIds;
	}

	public void setImaProductIds(Long[] imaProductIds)
	{
		this.imaProductIds = imaProductIds;
	}

	protected FormBuilder createEditFormBuilder(ProductTypeParameters productTypeParameters)
	{
		FormBuilder formBuilder = super.createEditFormBuilder(productTypeParameters);

		FieldBuilder fieldBuilder;

		for (Long imaId: getImaProductIds())
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("imaProductNameFor" + imaId);
			fieldBuilder.setDescription("Наименование продукта");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());
		}

		RequiredFieldValidator validator = new RequiredFieldValidator("Необходимо добавить хотя бы один ОМС.");
		validator.setEnabledExpression(new ConstantExpression(ArrayUtils.isEmpty(imaProductIds)));
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("imaProducts");
		fieldBuilder.setDescription("ОМС");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(validator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder;
	}
}
