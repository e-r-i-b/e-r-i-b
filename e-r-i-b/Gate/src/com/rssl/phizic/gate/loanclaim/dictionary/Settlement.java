package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Nady
 * @ created 20.05.2014
 * @ $Author$
 * @ $Revision$
 */

import java.math.BigInteger;

/**
 * ������ ����������� "���������� ������"
 */
public class Settlement extends MultiWordDictionaryEntry
{
	/**
	 * ��� ���� ����������� ������
	 */
	private String typeOfLocality;
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

	public String getTypeOfLocality()
	{
		return typeOfLocality;
	}

	public void setTypeOfLocality(String typeOfLocality)
	{
		this.typeOfLocality = typeOfLocality;
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
