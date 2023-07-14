/***********************************************************************
 * Module:  JurTransfer.java
 * Author:  Egorova
 * Purpose: Defines the Interface JurTransfer
 ***********************************************************************/

package com.rssl.phizic.gate.payments;

/**
 * ������� ������������ ���� � ������ ���� ����� ��������� ������� ������.
 */
public interface AbstractJurTransfer extends AbstractRUSPayment
{
	/**
	 * @return ������������ ����������
	 */
	String getReceiverName();

	/**
	 * ���������� ������������ ����������
	 * @param receiverName ������������ ����������
	 */
	void setReceiverName(String receiverName);

	/**
	 * @return ��� ���������� ������� - ����� �������������, � ���� ������ ����� null
	 */
	String getReceiverKPP();

	/**
	 * ������������� ����� ������� ��� �������� ���������� ��������
	 * @param registerNumber ����� �������
	 */
	void setRegisterNumber(String registerNumber);

	/**
	 * ������������� ������ ������� ��� �������� ���������� ��������
	 * @param registerString ������ �������
	 */
	void setRegisterString(String registerString);
}