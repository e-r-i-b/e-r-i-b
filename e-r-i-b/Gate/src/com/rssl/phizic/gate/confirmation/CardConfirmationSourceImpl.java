package com.rssl.phizic.gate.confirmation;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ��������, ��� �� ��������� �������������
 */

public class CardConfirmationSourceImpl implements CardConfirmationSource
{
	private String number;

	/**
	 * �����������
	 */
	public CardConfirmationSourceImpl()
	{}

	/**
	 * �����������
	 * @param number ����� �����
	 */
	public CardConfirmationSourceImpl(String number)
	{
		this.number = number;
	}

	/**
	 * ������ ����� �����
	 * @param number ����� �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getNumber()
	{
		return number;
	}
}
