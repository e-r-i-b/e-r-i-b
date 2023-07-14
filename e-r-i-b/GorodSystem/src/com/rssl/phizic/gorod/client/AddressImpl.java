package com.rssl.phizic.gorod.client;

import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Egorova
 * @ created 25.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class AddressImpl implements Address
{
	private String postalCode;
	private String province;
	private String district;
	private String city;
	private String street;
	private String house;
	private String building;
	private String flat;
	private String homePhone;
	private String workPhone;
	private String mobilePhone;
	private String mobileOperator;
	private String unparseableAddress;

	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	/**
    * Населенный пункт
    */
	public String getSettlement()
	{
		return null;
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getHouse()
	{
		return house;
	}

	public void setHouse(String house)
	{
		this.house = house;
	}

	public String getBuilding()
	{
		return building;
	}

	public void setBuilding(String building)
	{
		this.building = building;
	}

	public String getFlat()
	{
		return flat;
	}

	public void setFlat(String flat)
	{
		this.flat = flat;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getWorkPhone()
	{
		return workPhone;
	}

	public void setWorkPhone(String workPhone)
	{
		this.workPhone = workPhone;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getMobileOperator()
	{
		return mobileOperator;
	}

	public void setMobileOperator(String mobileOperator)
	{
		this.mobileOperator = mobileOperator;
	}

	public String getUnparseableAddress()
	{
		return unparseableAddress;
	}

	public void setUnparseableAddress(String unparseableAddress)
	{
		this.unparseableAddress = unparseableAddress;
	}

	/**
	 * Перевод адреса в строку необходимую Городу
	 * @return Адрес строкой. формат: country + ", " + region + ", " + city + ", " + street + ", " + building + ", " + apartment (названия из "Card/AbonentInfo")
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
        if (!StringHelper.isEmpty(getProvince()))
	        sb.append(getProvince());
		if (!StringHelper.isEmpty(getDistrict()))
	        sb.append(" , ").append(getDistrict());
		if (!StringHelper.isEmpty(getCity()))
	        sb.append(" , ").append(getCity());
		if (!StringHelper.isEmpty(getSettlement()))
	        sb.append(" , ").append(getSettlement());
		if (!StringHelper.isEmpty(getStreet()))
	        sb.append(" , ").append(getStreet());
		if (!StringHelper.isEmpty(getBuilding()))
	        sb.append(" , ").append(getBuilding());
		if (!StringHelper.isEmpty(getFlat()))
	        sb.append(" , ").append(getFlat());
		return sb.toString();
	}
}
