package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;

import java.math.BigDecimal;
import java.util.Map;

/**
 * ����������� ���������� � ��������
 *
 * @author Evgrafov
 * @ created 29.06.2007
 * @ $Author: egorova $
 * @ $Revision: 12412 $
 */

public interface DepositInfo
{
	/**
	 * ���������� ����
	 *
	 * @return ����
	 */
	Account getAccount();

	/**
	 * ���� ��� ������������ ������������ ���������
	 *
	 * @return ����
	 */
	Account getPercentAccount();

	/**
	 * ����� ��� ������������ ������� �� ��������� ����� ������
	 * � ���� �� ������������ (� ���������)
	 *
	 * @return ���, ����� ����� � ������� �� �����, ������� �� ���� ����� ����������.
	 */
	Map<Account, BigDecimal> getFinalAccounts();

	/**
	 * ����� ������������ ������� ������
	 * (��� ������� �� ����������� �������� ������� ���� ������� ����� ����� ������)
	 *
	 * @return �����
	 */
	Money getMinBalance();

	/**
	 * ����������� ����� ���������� ������
	 * @return �����
	 */
	Money getMinReplenishmentAmount();

	/**
	 * ������� ����������� ������ �� ��������� ����
	 * (��������������� ��� �����������������);
	 *
	 * @return true==����������� ���������
	 */
	boolean isRenewalAllowed();


	/**
	 * ��������� �� ��������� ������("���������" ��� "���������").
	 *
	 * @return true==���������
	 */
	boolean isAnticipatoryRemoval();


	/**
	 * ��������� �� ���������� ������("���������" ��� "���������").
	 *
	 * @return true==���������
	 */
	boolean isAdditionalFee();
	/**
	 * ����� �������� (������������ ������ ��� �����!)
	 * Domain: Text
	 *
	 * @return ����� ��������
	 */
	String getAgreementNumber();

}