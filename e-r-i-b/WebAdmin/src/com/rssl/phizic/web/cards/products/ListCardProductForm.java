package com.rssl.phizic.web.cards.products;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author gulov
 * @ created 03.10.2011
 * @ $Authors$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class ListCardProductForm extends ListFormBase<CardProduct>
{
	static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName("productName");
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус продукта");
		fieldBuilder.setName("online");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
