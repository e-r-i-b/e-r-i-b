package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.phizic.web.common.client.cards.ListCreditCardOfficeForm;

/**
 * @author Dorzhinov
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCreditCardOfficeMobileForm extends ListCreditCardOfficeForm
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
