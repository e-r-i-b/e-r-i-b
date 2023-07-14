package com.rssl.phizic.person;

import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author hudyakov
 * @ created 24.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class Address implements Serializable
{
	private static final String COMMA = ", ";
	private static final String PROVINCE = " ���. ";
	private static final String DISTRICT = " �-� ";
	private static final String CITY = "�. ";
	private static final String SETTLEMENT = "�.�. ";
	private static final String STREET = "��. ";
	private static final String HOUSE = "�. ";
	private static final String BUILDING = "����. ";
	private static final String FLAT = "��. ";

	private Long     id;
	private String   postalCode;
	private String   province;
	private String   district;
	private String   city;
	private String   settlement;
	private String   street;
	private String   house;
	private String   building;
	private String   flat;
	private String   unparseableAddress;

	//������� ��� ���� ����� ������� ������� ��� ���.
	private Boolean first;

	/**
	 * �������� ������
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	/**
	 * �������
	 */
	public String getProvince()
	{
		return province;
	}

	/**
	 * �����
	 */
	public String getDistrict()
	{
		return district;
	}

	/**
	 * �����
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * ���������� �����
	 */
	public String getSettlement()
	{
		return settlement;
	}

	/**
	 * �����
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * ����� ����
	 */
	public String getHouse()
	{
		return house;
	}

	/**
	 * ����� �������
	 */
	public String getBuilding()
	{
		return building;
	}

	/**
	 * ����� ��������
	 */
	public String getFlat()
	{
		return flat;
	}

	/**
	 * @return ������ ����� ����� �������. � ����� ����, ��������, ������������ �� ���� ��� ������� CEDBO
	 */
	public String getUnparseableAddress()
	{
		return unparseableAddress;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setSettlement(String settlement)
	{
		this.settlement = settlement;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public void setHouse(String house)
	{
		this.house = house;
	}

	public void setBuilding(String building)
	{
		this.building = building;
	}

	public void setFlat(String flat)
	{
		this.flat = flat;
	}

	public void setUnparseableAddress(String unparseableAddress)
	{
		this.unparseableAddress = unparseableAddress;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	public String toString()
    {
	    StringBuilder sb = new StringBuilder();
	    first = true;

	    append(sb, getPostalCode(), null, false);
	    append(sb, getProvince(), PROVINCE, false);
	    append(sb, getDistrict(), DISTRICT, true);
	    append(sb, getCity(), CITY, true);
	    append(sb, getSettlement(), SETTLEMENT, true);
	    append(sb, getStreet(), STREET, true);
	    append(sb, getHouse(), HOUSE, true);
	    append(sb, getBuilding(), BUILDING, true);
		append(sb, getFlat(), FLAT, true);

        return sb.toString();
    }

	private void append(StringBuilder sb, String info, String prefix, boolean prefixFisrt)
	{
		if (StringHelper.isEmpty(info))
			return;

		if (!first)
			sb.append(COMMA);
		else
			first = false;

		if (!StringHelper.isEmpty(prefix))
		{
			if (prefixFisrt)
				sb.append(prefix).append(info);
			else
				sb.append(info).append(prefix);
			return;
		}
		sb.append(info);

		return;
	}

	private Boolean isEmpty(String parameter)
	{
		return (parameter==null || parameter.length()==0);
	}

	public Boolean isEmpty()
	{
		return (isEmpty(getPostalCode()) && isEmpty(getProvince()) && isEmpty(getDistrict()) && isEmpty(getCity()) && isEmpty(getSettlement()) && isEmpty(getStreet()) && isEmpty(getHouse()) && isEmpty(getBuilding()) && isEmpty(getFlat()));
	}
}
