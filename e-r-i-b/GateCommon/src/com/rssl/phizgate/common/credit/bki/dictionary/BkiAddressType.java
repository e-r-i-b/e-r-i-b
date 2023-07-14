package com.rssl.phizgate.common.credit.bki.dictionary;

/**
 * User: Moshenko
 * Date: 03.10.14
 * Time: 15:32
 * ��� ������
 */
public class BkiAddressType extends BkiEsbDictionaryEntry
{

	private boolean registration = false;
	private boolean residence = false;

	/**
	 * @return ������ ��� ����� �����������
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
	 * @return ������ ��� ����� ����������
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
