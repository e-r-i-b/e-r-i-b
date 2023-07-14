package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.common.types.annotation.ThreadSafe;

/**
 * @author Erkin
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ���������� �������� ������������ ���� (����, ����� � �.�.)
 */
@ThreadSafe
public interface BankrollConfig 
{
	/**
	 * @return true - �������� ������� ���� ������������ (� ������)
	 */
	boolean isProductUsed();

	/**
	 * @return ���������� ������� (�������),
	 * � ������� �������� ������ ��������� ������� ���� ��������� ����������.
	 * �� ��������� ������� ����������� ���������� ������ ���������.
	 */
	long getProductListLifetime();
}
