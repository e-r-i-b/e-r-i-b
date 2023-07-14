package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * �� ������ ����
 *
 * @author Evgrafov
 * @ created 08.09.2006
 * @ $Author: Roshka $
 * @ $Revision: 2418 $
 */
public class AccountNotFoundException extends GateLogicException
{
	private final String accountNumber;

	/**
	 *
	 * @param accountNumber (Domain: AccountNumber)
	 */
	public AccountNotFoundException(String accountNumber)
	{
		super("C��� � " + accountNumber + " �� ������");
		this.accountNumber = accountNumber;
	}

	/**
	 * @return ����� �����
	 */
	public String getAccountNumber()
	{
		return accountNumber;
	}
}