package com.rssl.phizic.gate.monitoring;

/**
 * @author akrenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� �������� ������� �������
 */

public enum MonitoringGateState
{
	NORMAL(0, "�����������"),           //������� �����
	DEGRADATION(1, "���������������"),  //��������������� �����
	INACCESSIBLE(2, "�����������");     //����������� �����

	private int rang; // ���� ���������. ��������� ��� ������� ������� ���������
	private final String description; // �������� ���������

	MonitoringGateState(int rang, String description)
	{
		this.rang = rang;
		this.description = description;
	}

	/**
	 * @return ���� ��������� �������
	 */
	public int getRang()
	{
		return rang;
	}

	/**
	 * @return �������� ���������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ��������� � ������ ���������� �������.
	 * @param otherState - ������ ��������� �������
	 * @return  <0 ������� ��������� ������ �����������. ������� �� �������� ��������� � ���������� ��������.
	 *          0 - ��������� �����
	 *          >0 ������� ��������� ������ �����������. ������� �� �������� ��������� � ���������� �� ��������.
	 */
	public int compare(MonitoringGateState otherState)
	{
		return this.rang - otherState.getRang();
	}
}
