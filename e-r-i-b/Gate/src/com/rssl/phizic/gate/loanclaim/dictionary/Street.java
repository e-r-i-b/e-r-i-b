package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Nady
 * @ created 20.05.2014
 * @ $Author$
 * @ $Revision$
 */

import java.math.BigInteger;

/**
 * ������ ����������� "�����"
 */
public class Street extends MultiWordDictionaryEntry
{
	/**
	 * ��� ���� �����
	 */
	private String typeOfStreet;
	/**
	 * ��� ����������� ������
	 */
	private BigInteger settlement;
	/**
	 * ��� ������
	 */
	private BigInteger city;
	/**
	 * ��� ������
	 */
	private BigInteger area;
	/**
	 * ��� �������
	 */
	private String region;

	public String getTypeOfStreet()
	{
		return typeOfStreet;
	}

	public void setTypeOfStreet(String typeOfStreet)
	{
		this.typeOfStreet = typeOfStreet;
	}

	public BigInteger getSettlement()
	{
		return settlement;
	}

	public void setSettlement(BigInteger settlement)
	{
		this.settlement = settlement;
	}

	public BigInteger getCity()
	{
		return city;
	}

	public void setCity(BigInteger city)
	{
		this.city = city;
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
