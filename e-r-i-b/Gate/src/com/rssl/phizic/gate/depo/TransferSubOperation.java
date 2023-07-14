package com.rssl.phizic.gate.depo;

/**
 * @author akrenev
 * @ created 15.12.2011
 * @ $Author$
 * @ $Revision$
 */
/**
 * ����������� ��� ������� ��������
 */
public enum TransferSubOperation
{
	/**
	 * ������� �� ���� ���� ������ �����������
	 */
	INTERNAL_TRANSFER,
	/**
	 * ������� �� ���� � �������
	 */
	LIST_TRANSFER,
	/**
	 * ������� �� ���� � ������ �����������
	 */
	EXTERNAL_TRANSFER,
	/**
	 * ����� �������� �� ����� ���� ������ �����������
	 */
	INTERNAL_RECEPTION,
	/**
	 * ����� �������� �� ����� � �������
	 */
	LIST_RECEPTION,
	/**
	 * ����� �������� �� ����� � ������ �����������
	 */
	EXTERNAL_RECEPTION,
	/**
	 * ������� ����� ��������� ����� ����
	 */
	INTERNAL_ACCOUNT_TRANSFER;
}