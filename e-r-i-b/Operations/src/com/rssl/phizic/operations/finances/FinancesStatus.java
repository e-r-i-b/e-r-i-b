package com.rssl.phizic.operations.finances;

/**
 * @author rydvanskiy
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

public enum FinancesStatus
{
	/**
	 * ��� ������
	 */
	allOk,
	/**
	 * ������������ �� ��������� (� ������� �� ����� �����)
	 */
	notConnected,
	/**
	 * �� ������� ������������ ��� �� ����������� ������
	 */
	waitingClaims,
	/**
	 * � ������� ����������� �����
	 */
	noProducts
}
