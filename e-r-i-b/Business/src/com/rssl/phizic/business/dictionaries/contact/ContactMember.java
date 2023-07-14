package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.io.Serializable;

/**
 * @author Kosyakova
 * @ created 09.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class ContactMember extends DictionaryRecordBase implements Serializable
{
	private Long id;
	private String code;
	private String name;
	private String phone;
	private String address;
	private String city;
	private Long countryId;
	private boolean deleted;

	public boolean isDeleted()
	{
		return deleted;
	}

	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	public Comparable getSynchKey()
	{
		return code;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public Long getCountryId()
	{
		return countryId;
	}

	public void setCountryId(Long countryId)
	{
		this.countryId = countryId;
	}

	public String toString()
	{
		return getCode();
	}
}
