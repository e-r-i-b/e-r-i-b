package com.rssl.phizic.web.common.client.cards;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;

/**
 * @author Mescheryakova
 * @ created 25.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListCreditCardOfficeForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private boolean guest;
	private String regionId;

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();


		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Наименование");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("region");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Регион/город");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("street");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Улица");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("regionId");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Id региона");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("regionName");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Название региона");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("regionCodeTB");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Код ТБ");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();

	}

	public boolean isGuest()
	{
		return guest;
	}

	public void setGuest(boolean guest)
	{
		this.guest = guest;
	}

	public String getRegionId()
	{
		return regionId;
	}

	public void setRegionId(String regionId)
	{
		this.regionId = regionId;
	}
}
