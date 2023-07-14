package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ���������, ������� ����������� ��������-������ ������ ��� �������� � ����
 */
interface Value
{
	/**
	 * ���������� �������� ����
	 * @return - �������� ����
	 */
	Object getValue();

	/**
	 * ������� �������������� ����
	 * @return true - ���� �����������, false - ���� �������������
	 */
	boolean isMandatory();

	/**
	 * ���������� ������� ��� ������ � ���� ����� ������ �������� ����
	 * @return - ���������� �������
	 */
	int getCommaCount();

	/**
	 * ������� ������� �������� ����
	 * @return - true - ������ ����, false - �� ������
	 */
	boolean isEmpty();

	/**
	 * ���������, ������� ������� � ���, ���� �������� ���� �����������, �� �� ���������
	 * @return - ������ ���������
	 */
	String getMessage();
}
