package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.MockHelper;

/**
 * User: Balovtsev
 * Date: 06.05.2011
 * Time: 12:27:56
 */
public class ClosedAccountFilter implements AccountFilter
{
	/**
	 * �������� ������� ������/����� ������ �� ��� ���
	 * @param account  ������/����
	 * @return ������� �� ������/����
	 * @throws com.rssl.phizic.business.TemporalBusinessException ���� �����/���� �������� mock
	 */
	public boolean accept(Account account) throws TemporalBusinessException
	{
		if (MockHelper.isMockObject(account))
			throw new TemporalBusinessException("������ ��� ��������� ���������� �� ����� �" + account.getNumber());

		return account.getAccountState() == AccountState.CLOSED;
	}

	public boolean accept(AccountLink accountLink)
	{
		try
		{
			return accept(accountLink.getAccount());
		}
		catch (TemporalBusinessException e)
		{
			return false;
		}
	}
}
