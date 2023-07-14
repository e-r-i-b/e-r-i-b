package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author Kosyakova
 * @ created 09.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class ContactCountry extends DictionaryRecordBase
{
	private Long id;
	private String name;
	private String nameLat;
	private String code;
	private boolean isRUR;
	private boolean isUSD;
	private boolean isEUR;

	public Comparable getSynchKey()
	{
		return id;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNameLat()
	{
		return nameLat;
	}

	public void setNameLat(String nameLat)
	{
		this.nameLat = nameLat;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public boolean isRUR()
	{
		return isRUR;
	}

	public void setRUR(boolean RUR)
	{
		isRUR = RUR;
	}

	public boolean isUSD()
	{
		return isUSD;
	}

	public void setUSD(boolean USD)
	{
		isUSD = USD;
	}

	public boolean isEUR()
	{
		return isEUR;
	}

	public void setEUR(boolean EUR)
	{
		isEUR = EUR;
	}


}
