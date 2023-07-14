package com.rssl.phizic.gate.loyalty;

import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Calendar;
import java.io.Serializable;

/**
 * ���������� �� �������� �� ��������� ����������
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyProgramOperation extends Serializable
{
	/**
	 * @return ����-����� ��������
	 */
	Calendar getOperationDate();

	/**
	 * @return ��� ��������(��������/����������� ������)
	 */
	LoyaltyOperationKind getOperationKind();

	/**
	 * @return ������ ��������(� �������� ������)
	 */
	BigDecimal getOperationalBalance();

	/**
	 * @return ����� �������� � ������(�� ���� ������������ ������ ��� ���������� ������)
	 */
	Money getMoneyOperationalBalance();

	/**
	 * @return �������� ��������
	 */
	String getOperationInfo();
}
