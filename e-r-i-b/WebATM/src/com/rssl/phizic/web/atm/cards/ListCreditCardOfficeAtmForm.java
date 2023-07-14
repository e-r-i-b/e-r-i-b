package com.rssl.phizic.web.atm.cards;

import com.rssl.phizic.web.common.client.cards.ListCreditCardOfficeForm;

/**
 * @author Balovtsev
 * @version 23.08.13 14:45
 */
public class ListCreditCardOfficeAtmForm extends ListCreditCardOfficeForm
{
	private String region;
	private String street;
	private String name;

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
