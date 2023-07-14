package com.rssl.phizic.web.cards.products;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author gulov
 * @ created 04.10.2011
 * @ $Authors$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class EditCardProductForm extends EditFormBase
{
	static final Form EDIT_FORM = createForm();

	private CardProduct product;
	private Long[] subIds;
	private long[] stopOpenDate;
	private String[] currencies;

	public CardProduct getProduct()
	{
		return product;
	}

	public void setProduct(CardProduct product)
	{
		this.product = product;
	}

	public Long[] getSubIds()
	{
		return subIds;
	}

	public void setSubIds(Long[] subIds)
	{
		this.subIds = subIds;
	}

	public long[] getStopOpenDate()
	{
		return stopOpenDate;
	}

	public void setStopOpenDate(long[] stopOpenDate)
	{
		this.stopOpenDate = stopOpenDate;
	}

	public String[] getCurrencies()
	{
		return currencies;
	}

	public void setCurrencies(String[] currencies)
	{
		this.currencies = currencies;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
			new RequiredFieldValidator(),
			new RegexpFieldValidator(".{0,255}", "Наименование должно быть не более 255 символов"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Опубликована");
		fieldBuilder.setName("online");
		fieldBuilder.setType("boolean");
		fieldBuilder.clearValidators();
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
