package com.rssl.phizic.web.loans.products;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 25.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListModifiableLoanProductForm extends ListFormBase
{
	//Карта: <Вид продукта, список кредитных продуктов этого вида>
	private Map<LoanKind, List<ModifiableLoanProduct>> productsByKind = new HashMap<LoanKind, List<ModifiableLoanProduct>>();

	public Map<LoanKind, List<ModifiableLoanProduct>> getProductsByKind()
	{
		return productsByKind;
	}

	public void setProductsByKind(Map<LoanKind, List<ModifiableLoanProduct>> productsByKind)
	{
		this.productsByKind = productsByKind;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("kind");
		fieldBuilder.setDescription("Вид продукта");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("product");
		fieldBuilder.setDescription("Наименование продукта");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("publicity");
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
