package com.rssl.phizic.logging.monitoring;

/**
 * ���� ����������.
 *
 * @author bogdanov
 * @ created 25.02.15
 * @ $Author$
 * @ $Revision$
 */

public enum MonitoringDocumentType
{
	/**
	 * ServiceProviderPayment (������ ����������)
	 */
	SPP,
	/**
	 * ServiceProviderAutopayment (���������� ����������)
	 */
	SPAP,
	/**
	 * ServiceProviderTemplate (������ ����������)
	 */
	SPT,
	/**
	 * ServiceProviderPaymentByTemplate (������ ���������� �� �������)
	 */
	SPPBT,
	/**
	 * ServiceProviderPaymentByReminder (������ ���������� ����� �������)
	 */
	SPPBR,

	/**
	 * �����-����� ���������
	 */
	CCS,
	/**
	 * �����-�����
	 */
	CA,
	/**
	 * �����-�����
	 */
	AC,
	/**
	 * �����-�����
	 */
	AA,
	/**
	 * ����� �� ���� � ������ ��
	 */
	CAOTB,
	/**
	 * ����� �� ���� � ������ �����
	 */
	CAOB,
	/**
	 * �����-����� �� ��������� (���, VMT)
	 */
	CCNS,

	/**
	 * �������� ������
	 */
	AOC,
	/**
	 * �������� ������ ����� ���.
	 */
	AOC_ALF,
	/**
	 * ������ �� ������.
	 */
	CREDIT,
	/**
	 * ������� � ������ �� ������.
	 */
	CRD_DCSN,
	/**
	 * ������� � ����� �� �����.
	 */
	CARD_TRANSFER,
	/**
	 * �������� ����������� ������ ��� ��������������� ������.
	 */
	TDO,
	/**
	 * �������� ������ ������ � ������� ������� � ����� �������
	 */
	TCDO,
	/**
	 * �������� ������ ������ � ������� ������� � ������� ������ �������
	 */
	TDDO,
	/**
	 * �������� ������ ��� � ������� ������� �� ������ �������
	 */
	TDIO,
	/**
	 * �������� ��� � ��������� �� ���� �������� ������� � ��� ���� �� �������
	 */
	TCIO,
	/**
	 * ������� ��� �� ���� ������� �� ����� �������
	 */
	TCI,
	/**
	 * ������� ��� � ����������� ������� �� ����� �������
	 */
	TIC,
	/**
	 * ������� � ����� �� �����
	 */
	TCD,
	/**
	 * ������� �� ������ �� �����
	 */
	TDC,
	/**
	 * ������� iqwave
	 */
	IQWAVE;

	/**
	 * @return �������� - ������ �����.
	 */
	public boolean isPayment()
	{
		switch (this)
		{
			case SPP:
			case SPAP:
			case SPT:
			case SPPBT:
			case SPPBR:
			case CARD_TRANSFER:
				return true;
			default:
				return false;
		}
	}

	/**
	 * @return ��������� ��������.
	 */
	public boolean isInternal()
	{
		switch (this)
		{
			case CCS:
			case CA:
			case AC:
			case AA:
			case CAOTB:
			case CAOB:
			case CCNS:
				return true;
			default:
				return false;
		}
	}

	/**
	 * @return �������� ������.
	 */
	public boolean isAccountOpen()
	{
		return this == AOC || this == AOC_ALF;
	}
}
