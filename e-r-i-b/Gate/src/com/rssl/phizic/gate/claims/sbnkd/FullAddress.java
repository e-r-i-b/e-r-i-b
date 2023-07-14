package com.rssl.phizic.gate.claims.sbnkd;

/**
 * ������ �����.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class FullAddress
{
	private String postalCode;
	private String country;
	private String region;
	private String city;
	private String afterSityAdress;
	private boolean registrationAddress;

	/**
	 * @param postalCode �������� ������.
	 */
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	/**
	 * @return �������� ������.
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	/**
	 * @param country ������.
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * @return ������.
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param region ������ (����������, ������� ����).
	 */
	public void setRegion(String region)
	{
		this.region = region;
	}

	/**
	 * @return ������ (����������, ������� ����).
	 */
	public String getRegion()
	{
		return region;
	}

	/**
	 * @param city �����.
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @return �����.
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param afterSityAdress ��������� ����� (�����, ���, ������, ��������).
	 */
	public void setAfterSityAdress(String afterSityAdress)
	{
		this.afterSityAdress = afterSityAdress;
	}

	/**
	 * @return ��������� ����� (�����, ���, ������, ��������).
	 */
	public String getAfterSityAdress()
	{
		return afterSityAdress;
	}

	/**
	 * @return ����� �����������.
	 */
	public boolean isRegistrationAddress()
	{
		return registrationAddress;
	}

	/**
	 * @param registrationAddress ����� �����������.
	 */
	public void setRegistrationAddress(boolean registrationAddress)
	{
		this.registrationAddress = registrationAddress;
	}

	public String toString()
	{
		return getStringIfNotNull(postalCode) + "!!"
				+ getStringIfNotNull(country) + "!!"
				+ getStringIfNotNull(region) + "!!"
				+ getStringIfNotNull(city) + "!!"
				+ getStringIfNotNull(afterSityAdress);
	}

	private String getStringIfNotNull(String str)
	{
		return str != null ? str : "";
	}
}
