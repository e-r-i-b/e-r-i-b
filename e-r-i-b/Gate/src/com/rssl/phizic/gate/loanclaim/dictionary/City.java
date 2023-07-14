package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Nady
 * @ created 20.05.2014
 * @ $Author$
 * @ $Revision$
 */

import java.math.BigInteger;

/**
 * ������ ����������� "������"
 */
public class City extends MultiWordDictionaryEntry
{
	/**
	 * ��� ���� ������
	 */
	private String typeOfCity;
	/**
	 * ��� ������
	 */
	private BigInteger area;
	/**
	 * ��� �������
	 */
	private String region;

	public String getTypeOfCity()
	{
		return typeOfCity;
	}

	public void setTypeOfCity(String typeOfCity)
	{
		this.typeOfCity = typeOfCity;
	}

	public BigInteger getArea()
	{
		return area;
	}

	public void setArea(BigInteger area)
	{
		this.area = area;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}
}
