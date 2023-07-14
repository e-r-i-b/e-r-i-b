package com.rssl.phizic.business.clients;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.gate.clients.Address;

/**
 * @author Omeliyanchuk
 * @ created 06.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class AddressImpl implements Address
{
   String postalCode;
   String province;
   String district;
   String city;
   String settlement;
   String street;
   String house;
   String building;
   String flat;
   String homePhone;
   String workPhone;
   String mobilePhone;
   String mobileOperator;
   String unparseableAddress;

	public AddressImpl(com.rssl.phizic.person.Address address, Person person)
	{
	   postalCode = address.getPostalCode();
	   province = address.getProvince();
	   district = address.getDistrict();
	   city = address.getCity();
	   settlement = address.getSettlement();
	   street = address.getStreet();
	   house = address.getHouse();
	   building = address.getBuilding();
	   flat = address.getFlat();
	   homePhone = person.getHomePhone();
	   workPhone = person.getJobPhone();
	   mobilePhone = person.getMobilePhone();
	   unparseableAddress = address.getUnparseableAddress();
	   mobileOperator = person.getMobileOperator();
	}

	public String getBuilding()
	{
		return building;
	}

	public void setBuilding(String building)
	{
		this.building = building;
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

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
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

	public String getHouse()
	{
		return house;
	}

	public void setHouse(String house)
	{
		this.house = house;
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

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getWorkPhone()
	{
		return workPhone;
	}

	public void setWorkPhone(String workPhone)
	{
		this.workPhone = workPhone;
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
