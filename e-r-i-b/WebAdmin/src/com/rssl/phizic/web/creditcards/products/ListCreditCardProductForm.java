package com.rssl.phizic.web.creditcards.products;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListCreditCardProductForm extends ListFormBase<CreditCardProduct>
{
	public static final Form FILTER_FORM  = createForm();

	public static final String CHANNEL_GUEST_PREAPPROVED  = "guestPreapproved";
	public static final String CHANNEL_GUEST_LEAD         = "guestLead";
	public static final String CHANNEL_COMMON_PREAPPROVED = "useForPreapprovedOffers";
	public static final String CHANNEL_COMMON_LEAD        = "commonLead";

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
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
