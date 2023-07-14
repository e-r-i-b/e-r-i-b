package com.rssl.auth.csa.back.integration.erib.types;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Адрес
 */

public class Address
{
	private String type;
	private String postalCode;
	private String province;
	private String district;
	private String city;
	private String street;
	private String house;
	private String building;
	private String flat;
	private String unparseableAddress;

	/**
	 * @return тип адреса
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * задать тип адреса
	 * @param type тип адреса
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * задать индекс
	 * @param postalCode индекс
	 */
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	/**
	 * @return индекс
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	/**
	 * задать область
	 * @param province область
	 */
	public void setProvince(String province)
	{
		this.province = province;
	}

	/**
	 * @return область
	 */
	public String getProvince()
	{
		return province;
	}

	/**
	 * задать район
	 * @param district район
	 */
	public void setDistrict(String district)
	{
		this.district = district;
	}

	/**
	 * @return район
	 */
	public String getDistrict()
	{
		return district;
	}

	/**
	 * задать город
	 * @param city город
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @return город
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * задать улицу
	 * @param street улица
	 */
	public void setStreet(String street)
	{
		this.street = street;
	}

	/**
	 * @return улица
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * задать дом
	 * @param house дом
	 */
	public void setHouse(String house)
	{
		this.house = house;
	}

	/**
	 * @return дом
	 */
	public String getHouse()
	{
		return house;
	}

	/**
	 * задать строение
	 * @param building строение
	 */
	public void setBuilding(String building)
	{
		this.building = building;
	}

	/**
	 * @return строение
	 */
	public String getBuilding()
	{
		return building;
	}

	/**
	 * задать квартиру
	 * @param flat квартира
	 */
	public void setFlat(String flat)
	{
		this.flat = flat;
	}

	/**
	 * @return квартира
	 */
	public String getFlat()
	{
		return flat;
	}

	/**
	 * задать неформатируемый адрес
	 * @param unparseableAddress неформатируемый адрес
	 */
	public void setUnparseableAddress(String unparseableAddress)
	{
		this.unparseableAddress = unparseableAddress;
	}

	/**
	 * @return неформатируемый адрес
	 */
	public String getUnparseableAddress()
	{
		return unparseableAddress;
	}
}