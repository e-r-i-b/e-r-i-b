package com.rssl.auth.csa.back.integration.erib.types;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � �����
 */

public class CardInformation
{
	private String number;

	/**
	 * �����������
	 */
	public CardInformation()
	{}

	/**
	 * �����������
	 * @param number ����� �����
	 */
	public CardInformation(String number)
	{
		this.number = number;
	}

	/**
	 * @return ����� �����
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * ������ ����� �����
	 * @param number ����� �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}
}
