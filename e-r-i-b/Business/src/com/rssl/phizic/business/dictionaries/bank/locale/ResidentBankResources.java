package com.rssl.phizic.business.dictionaries.bank.locale;


import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;

/**
 * @author koptyaev
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("JavaDoc")
public class ResidentBankResources implements LanguageResources<String>, MultiBlockDictionaryRecord
{
	private String id;
	private String localeId;
	private String name;
	private String place;
	private String shortName;
	private String address;


	public String getId()
	{
		return id;
	}

	public String getLocaleId()
	{
		return localeId;
	}

	public String getMultiBlockRecordId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPlace()
	{
		return place;
	}

	public void setPlace(String place)
	{
		this.place = place;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

}
