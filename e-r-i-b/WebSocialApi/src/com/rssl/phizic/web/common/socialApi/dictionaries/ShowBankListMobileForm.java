package com.rssl.phizic.web.common.socialApi.dictionaries;

import com.rssl.phizic.web.common.dictionaries.ShowBankListForm;

/**
 * @author Dorzhinov
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowBankListMobileForm extends ShowBankListForm
{
	private String name;
	private String bic;
	private String city;
	private String guid;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBic()
	{
		return bic;
	}

	public void setBic(String bic)
	{
		this.bic = bic;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}
}
