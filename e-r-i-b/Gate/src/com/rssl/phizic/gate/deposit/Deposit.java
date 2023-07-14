package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * �������� ���������� �� ��������
 */
public interface Deposit extends Serializable
{
	/**
	 * ������� ID ��������
	 * Domain: ExternalID
	 *
	 * @return ������� id
	 */
	String getId();

	/**
	 * �������� ��������
	 * Domain: Text
	 *
	 * @return ��������
	 */
	String getDescription();

	/**
	 * ����� ������
	 *
	 * @return ����� ������
	 */
	Money getAmount();

	/**
	 * ���� � ����
	 *
	 * @return ����
	 */
	Long getDuration();

	/**
	 * ���������� ������ (% �������)  2.00 -> 2% 11.30 -> 11.3%
	 *
	 * @return %�������
	 */
	BigDecimal getInterestRate();

	/**
	 * ���� �������� ��������
	 * Domain: Date
	 *
	 * @return ����
	 */
	Calendar getOpenDate();

	/**
	 * ���� ��������� ����� �������� �������� ��� �������� (��� �������� � ��������).
	 * Domain: Date
	 *
	 * @return ����
	 */
	Calendar getEndDate();

	/**
	 * ����������� ���� �������� ��������. ��� ��������� (������������) �������� == null.
	 * Domain: Date
	 *
	 * @return ����
	 */
	Calendar getCloseDate();
	/**
	 * ������ �������� (������, ������ ...)
	 *
	 * @return ���������
	 */
	DepositState getState();
}
