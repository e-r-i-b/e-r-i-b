package com.rssl.phizic.web.common.mobile.finances.categories;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;

/**
 * @author Dorzhinov
 * @ created 30.09.2013
 * @ $Author$
 * @ $Revision$
 */
public class CategoriesListMobileForm extends FinanceFormBase
{
	public static final Form FORM = createForm();
	private String incomeType;

	public String getIncomeType()
	{
		return incomeType;
	}

	public void setIncomeType(String incomeType)
	{
		this.incomeType = incomeType;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("incomeType");
		fieldBuilder.setDescription("Тип категории");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{INCOME_TYPE, OUTCOME_TYPE}))
		);
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
