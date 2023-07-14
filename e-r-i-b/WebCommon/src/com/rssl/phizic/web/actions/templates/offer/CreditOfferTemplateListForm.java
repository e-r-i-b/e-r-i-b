package com.rssl.phizic.web.actions.templates.offer;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Balovtsev
 * @since 03.06.2015.
 */
public class CreditOfferTemplateListForm extends ListFormBase<CreditOfferTemplate>
{
	public static final Form FILTER = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("id");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setDescription("Идентификатор шаблона оферты");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("status");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Статус");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
