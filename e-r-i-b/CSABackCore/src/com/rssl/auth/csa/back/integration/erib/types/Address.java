package com.rssl.auth.csa.back.integration.erib.types;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * �����
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
	 * @return ��� ������
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * ������ ��� ������
	 * @param type ��� ������
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * ������ ������
	 * @param postalCode ������
	 */
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	/**
	 * @return ������
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	/**
	 * ������ �������
	 * @param province �������
	 */
	public void setProvince(String province)
	{
		this.province = province;
	}

	/**
	 * @return �������
	 */
	public String getProvince()
	{
		return province;
	}

	/**
	 * ������ �����
	 * @param district �����
	 */
	public void setDistrict(String district)
	{
		this.district = district;
	}

	/**
	 * @return �����
	 */
	public String getDistrict()
	{
		return district;
	}

	/**
	 * ������ �����
	 * @param city �����
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @return �����
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * ������ �����
	 * @param street �����
	 */
	public void setStreet(String street)
	{
		this.street = street;
	}

	/**
	 * @return �����
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * ������ ���
	 * @param house ���
	 */
	public void setHouse(String house)
	{
		this.house = house;
	}

	/**
	 * @return ���
	 */
	public String getHouse()
	{
		return house;
	}

	/**
	 * ������ ��������
	 * @param building ��������
	 */
	public void setBuilding(String building)
	{
		this.building = building;
	}

	/**
	 * @return ��������
	 */
	public String getBuilding()
	{
		return building;
	}

	/**
	 * ������ ��������
	 * @param flat ��������
	 */
	public void setFlat(String flat)
	{
		this.flat = flat;
	}

	/**
	 * @return ��������
	 */
	public String getFlat()
	{
		return flat;
	}

	/**
	 * ������ ��������������� �����
	 * @param unparseableAddress ��������������� �����
	 */
	public void setUnparseableAddress(String unparseableAddress)
	{
		this.unparseableAddress = unparseableAddress;
	}

	/**
	 * @return ��������������� �����
	 */
	public String getUnparseableAddress()
	{
		return unparseableAddress;
	}
}