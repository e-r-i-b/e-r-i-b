package com.rssl.phizic.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������� �� ���������� ��������� � ������� �������� ������ (������)
 */
@NonThreadSafe
public interface PersonBankrollManager
{
	/**
	 * ������������� �������� ������� ������� �� ������� �������
	 */
	void reloadProducts();

	/**
	 * ���������� ������ ������ �������
	 * @return ������ ������ ������� (never null)
	 */
	Collection<? extends BankrollProductLink> getAccounts();

	/**
	 * ���������� ������ ���� �������
	 * @return ������ ���� ������� (never null)
	 */
	Collection<? extends BankrollProductLink> getCards();

	/**
	 * ���������� ������ �������� �������
	 * @return ������ �������� ������� (never null)
	 */
	Collection<? extends BankrollProductLink> getLoans();

	/**
	 * ���� ��������-������� �� ��� ���-������
	 * @param smsAlias - ���-����� (never null)
	 * @param searchClasses - ������ ���������, �� ������� ����� ������; null - ������ �� ���� ���������
	 * @return ����-��-��������-������� ��� null, ���� �� ������
	 */
	BankrollProductLink findProductBySmsAlias(String smsAlias, Class<? extends BankrollProductLink>... searchClasses);

	/**
	 * ����� �������� ������
	 * @return ������
	 */
	Currency getRURCurrency();

	/**
	 * ����� ������������ ����� ��� ��������, �� ������� ���� ����������� ����� ��������� ������� � ������ ������������ �����, ������������ �������������
	 * @param amount - ����� �������� (never null)
	 * @return ������������ �����, not null
	 */
	BankrollProductLink getPriorityCardForChargeOff(Money amount);

	/**
	 * ����� ������������ ����� ��� ������
	 * @return ������������ �����, not null
	 */
	BankrollProductLink getPriorityCard();

	/**
	 * ������� ���������� ����������, ������� ���������� �������� � ����-������� �� ����� � �����. ������������ � ������� "�������"
	 * @return ���������� ����������
	 */
	Long getNumberOfTransactions();
}
