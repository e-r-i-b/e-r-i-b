package com.rssl.phizic.business.dictionaries.country;


/**
 * �������� ������ � iso 3166 - ������
 * @author koptyaev
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 */
public class CountryCode
{
	private String iso2;
	private String iso3;
	private String name;

	/**
	 * @return iso2 ���
	 */
	public String getIso2()
	{
		return iso2;
	}

	/**
	 * ���������� iso2 ��� ������
	 * @param iso2 ���
	 */
	public void setIso2(String iso2)
	{
		this.iso2 = iso2;
	}

	/**
	 * @return iso3 ���
	 */
	public String getIso3()
	{
		return iso3;
	}

	/**
	 * ���������� iso3 ���
	 * @param iso3 ���
	 */
	public void setIso3(String iso3)
	{
		this.iso3 = iso3;
	}

	/**
	 * �������� ������
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ���������� �������� ������
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
