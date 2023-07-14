package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.gate.loanclaim.dictionary.*;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author Erkin
 * @ created 21.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * �����
 */
@Immutable
public class Address
{
	private final String postalCode;

	private final Region region;

	private final TypeOfArea areaType;

	private final String areaName;

	private final TypeOfCity cityType;

	private final String cityName;

	private final TypeOfLocality localityType;

	private final String localityName;

	private final TypeOfStreet streetType;

	private final String streetName;

	private final String houseNumber;

	private final String buildingNumber;

	private final String unitNumber;

	private final String apartmentNumber;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param postalCode - �������� ������ (never null)
	 * @param region - ������ (never null)
	 * @param areaType - ��� ������/������ (can be null)
	 * @param areaName - �������� ������/������ (can be null)
	 * @param cityType - ��� ������ (can be null)
	 * @param cityName - �������� ������ (can be null)
	 * @param localityType - ��� ���������� ������ (can be null)
	 * @param localityName - �������� ���������� ������ (can be null)
	 * @param streetType - ��� ����� (can be null)
	 * @param streetName - �������� ����� (never null)
	 * @param houseNumber - ����� ���� (never null)
	 * @param buildingNumber - ����� ������� (can be null)
	 * @param unitNumber - ����� �������� (can be null)
	 * @param apartmentNumber - ����� �������� (can be null)
	 */
	public Address(String postalCode, Region region, TypeOfArea areaType, String areaName, TypeOfCity cityType, String cityName, TypeOfLocality localityType, String localityName, TypeOfStreet streetType, String streetName, String houseNumber, String buildingNumber, String unitNumber, String apartmentNumber)
	{
		if (StringHelper.isEmpty(postalCode))
		    throw new IllegalArgumentException("�� ������ �������� ������");
		if (region == null)
		    throw new IllegalArgumentException("�� ������ ������");
		if (StringHelper.isEmpty(streetName))
			throw new IllegalArgumentException("�� ������� �������� �����");
		if (StringHelper.isEmpty(houseNumber))
			throw new IllegalArgumentException("�� ������ ����� ����");

		this.postalCode = postalCode;
		this.areaType = areaType;
		this.region = region;
		this.areaName = areaName;
		this.cityType = cityType;
		this.cityName = cityName;
		this.localityType = localityType;
		this.localityName = localityName;
		this.streetType = streetType;
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.buildingNumber = buildingNumber;
		this.unitNumber = unitNumber;
		this.apartmentNumber = apartmentNumber;
	}

	/**
	 * @return �������� ������ (never null)
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	/**
	 * @return ������ (never null)
	 */
	public Region getRegion()
	{
		return region;
	}

	/**
	 * @return ��� ������/������ (can be null)
	 */
	public TypeOfArea getAreaType()
	{
		return areaType;
	}

	/**
	 * @return �������� ������/������ (can be null)
	 */
	public String getAreaName()
	{
		return areaName;
	}

	/**
	 * @return ��� ������ (can be null)
	 */
	public TypeOfCity getCityType()
	{
		return cityType;
	}

	/**
	 * @return �������� ������ (can be null)
	 */
	public String getCityName()
	{
		return cityName;
	}

	/**
	 * @return ��� ���������� ������ (can be null)
	 */
	public TypeOfLocality getLocalityType()
	{
		return localityType;
	}

	/**
	 * @return �������� ���������� ������ (can be null)
	 */
	public String getLocalityName()
	{
		return localityName;
	}

	/**
	 * @return ��� ����� (can be null)
	 */
	public TypeOfStreet getStreetType()
	{
		return streetType;
	}

	/**
	 * @return �������� ����� (never null)
	 */
	public String getStreetName()
	{
		return streetName;
	}

	/**
	 * @return ����� ���� (never null)
	 */
	public String getHouseNumber()
	{
		return houseNumber;
	}

	/**
	 * @return ����� ������� (can be null)
	 */
	public String getBuildingNumber()
	{
		return buildingNumber;
	}

	/**
	 * @return ����� �������� (can be null)
	 */
	public String getUnitNumber()
	{
		return unitNumber;
	}

	/**
	 * @return ����� �������� (can be null)
	 */
	public String getApartmentNumber()
	{
		return apartmentNumber;
	}

	@Override
	public int hashCode()
	{
		return postalCode.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Address other = (Address) o;

		boolean rc = postalCode.equals(other.postalCode);
		rc = rc && ObjectUtils.equals(region, other.region);
		rc = rc && ObjectUtils.equals(areaType, other.areaType);
		rc = rc && ObjectUtils.equals(areaName, other.areaName);
		rc = rc && ObjectUtils.equals(cityType, other.cityType);
		rc = rc && ObjectUtils.equals(cityName, other.cityName);
		rc = rc && ObjectUtils.equals(localityType, other.localityType);
		rc = rc && ObjectUtils.equals(localityName, other.localityName);
		rc = rc && ObjectUtils.equals(streetType, other.streetType);
		rc = rc && ObjectUtils.equals(streetName, other.streetName);
		rc = rc && ObjectUtils.equals(houseNumber, other.houseNumber);
		rc = rc && ObjectUtils.equals(buildingNumber, other.buildingNumber);
		rc = rc && ObjectUtils.equals(unitNumber, other.unitNumber);
		rc = rc && ObjectUtils.equals(apartmentNumber, other.apartmentNumber);
		return rc;
	}
}
