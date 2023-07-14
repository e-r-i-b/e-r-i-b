package com.rssl.phizgate.common.credit.bki.dictionary;

/**
 * User: Moshenko
 * Date: 03.10.14
 * Time: 15:32
 * Тип адреса
 */
public class BkiAddressType extends BkiEsbDictionaryEntry
{

	private boolean registration = false;
	private boolean residence = false;

	/**
	 * @return флажок это адрес регистрации
	 */
	public boolean isRegistration()
	{
		return registration;
	}

	public void setRegistration(boolean registration)
	{
		this.registration = registration;
	}
	/**
	 * @return флажок это адрес проижвания
	 */
	public boolean isResidence()
	{
		return residence;
	}

	public void setResidence(boolean residence)
	{
		this.residence = residence;
	}
}
