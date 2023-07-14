package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.web.dictionaries.pfp.products.ListProductsBaseForm;

/**
 * @author akrenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * форма списка продуктов
 */
public class ListProductsForm extends ListProductsBaseForm
{
	/**
	 * логическа€ форма фильтрации
	 */
	public static final Form FILTER_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("forComplex");
		fieldBuilder.setDescription("ƒоступность клиенту");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
