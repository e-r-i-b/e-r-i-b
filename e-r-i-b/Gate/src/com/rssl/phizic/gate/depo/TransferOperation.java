package com.rssl.phizic.gate.depo;

/** �������� ��� ������� ��������
 *
 * @author akrenev
 * @ created 15.12.2011
 * @ $Author$
 * @ $Revision$
 */
public enum TransferOperation
{
	/**
	 * �������
	 */
	TRANFER("231"),
	/**
	 * ��������� ��������
	 */
	RECEPTION("240"),
	/**
	 * ������� ����� ��������� ����� ����
	 */
	INTERNAL_TRANFER("220");

	private String value;

	/**
	 * @return ��� ��������
	 */
	public String getValue()
	{
		return value;
	}

	private TransferOperation(String value)
	{
		this.value = value;
	}
}