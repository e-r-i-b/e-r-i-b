package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.clients.Address;

/**
 @author Pankin
 @ created 12.10.2010
 @ $Author$
 @ $Revision$
 */
public class AddressImpl implements Address
{
	private String postalCode;
	private String province;
	private String district;
	private String city;
	private String settlement;
	private String street;
	private String house;
	private String building;
	private String flat;
	private String homePhone;
	private String workPhone;
	private String mobilePhone;
	private String mobileOperator;
	private String unparseableAddress;

	/**
	 * Стандартный конструктор
	 */
	public AddressImpl()
	{
	}

	/**
	 *
	 * @param unparseableAddress - полный адрес одной строкой
	 */
	public AddressImpl(String unparseableAddress)
	{
		this.unparseableAddress = unparseableAddress;
	}

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

	public String getSettlement()
	{
		return settlement;
	}

	public void setSettlement(String settlement)
	{
		this.settlement = settlement;
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
}
