package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ������������� �� ����� ����
 */
public interface DepoDebtItem extends Serializable
{

	/**
	 * ������� ID ������������� �� ����� ����
	 * @return Id
	 */
	String getId();

	/**
	 * ����� ������������� �����
	 * @return recNumber
	 */
	String getRecNumber();

	/**
	 * �����, ��� ����� ���
	 * @return amount
	 */
	Money getAmount();

	/**
	 * ����� ���
	 * @return amountNDS
	 */
	Money getAmountNDS();

	/**
	 * ���� ������������� �����
	 * @return recDate
	 */
	Calendar getRecDate();

	/**
	 * ������ �������, �� ������� ��������� ���� (���������� �������)
	 * @return startDate
	 */
	Calendar getStartDate();

	/**
	 * ����� �������, �� ������� ��������� ���� (���������� �������)
	 * @return endDate
	 */
	Calendar getEndDate();
}
