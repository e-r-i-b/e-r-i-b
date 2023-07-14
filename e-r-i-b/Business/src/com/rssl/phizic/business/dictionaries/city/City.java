package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * �������� "�����"
 * @author lepihina
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class City extends DictionaryRecordBase
{
	private String code; // ��� ������ � �������� �������
	private String regionCode; // ��� �������
	private String name; // ������������
	private String enName; // ������������ ������������

	public City()
	{
	}

	public City(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getRegionCode()
	{
		return regionCode;
	}

	public void setRegionCode(String regionCode)
	{
		this.regionCode = regionCode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Comparable getSynchKey()
	{
		return code;
	}

	public String getEnName()
	{
		return enName;
	}

	public void setEnName(String enName)
	{
		this.enName = enName;
	}
}
