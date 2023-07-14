package com.rssl.phizic.gate.claims.sbnkd;

/**
 * Полный адрес.
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
	 * @param postalCode Почтовый индекс.
	 */
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	/**
	 * @return Почтовый индекс.
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	/**
	 * @param country Страна.
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * @return Страна.
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param region Регион (республика, область край).
	 */
	public void setRegion(String region)
	{
		this.region = region;
	}

	/**
	 * @return Регион (республика, область край).
	 */
	public String getRegion()
	{
		return region;
	}

	/**
	 * @param city Город.
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @return Город.
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param afterSityAdress Остальной адрес (улица, дом, корпус, квартира).
	 */
	public void setAfterSityAdress(String afterSityAdress)
	{
		this.afterSityAdress = afterSityAdress;
	}

	/**
	 * @return Остальной адрес (улица, дом, корпус, квартира).
	 */
	public String getAfterSityAdress()
	{
		return afterSityAdress;
	}

	/**
	 * @return адрес регистрации.
	 */
	public boolean isRegistrationAddress()
	{
		return registrationAddress;
	}

	/**
	 * @param registrationAddress адрес регистрации.
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
